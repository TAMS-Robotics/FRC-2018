import cv2
import numpy as np
import pyrealsense as pyrs

from networktables import NetworkTables

import sys
import os
import signal
import time
import math
import logging

NetworkTables.initialize(server = '10.52.12.2')
vision_table = NetworkTables.getTable('vision')

logging.basicConfig(filename = 'vision.log', level = logging.DEBUG, format = '%(asctime)s %(message)s')
signal.signal(signal.SIGSEGV, sig_handler)

res_x, res_y = 1920, 1080
diagonal_fov = 75.2
target_height = 9.375
camera_height = 2.7916667
camera_angle = 0

ptd = diagonal_fov / math.sqrt(math.pow(res_x, 2) + math.pow(res_y, 2))

camera_matrix = np.array([[7.8041108095538573e+02, 0., 320.],
                          [0., 7.8041108095538573e+02, 240.],
                          [0., 0., 1.]])
camera_distortion_matrix = np.array([[1.2967497853950866e-01, 4.5607189281390443e+00, 0., 0., -2.4946392110455260e+01]])
new_camera_matrix, region_of_interest = cv2.getOptimalNewCameraMatrix(camera_matrix, camera_distortion_matrix, (res_x, res_y), 1, (res_x, res_y))

lowest_cost = 300

def get_target_values(frame):
    clear = make_true_frame(frame)
    mask = erode_dilate_mask(clear)
    center = find_contoured_centroid(mask)

    angle = pixel2degrees(center, resolution, diagonal_fov, camera_angle)
    distance = dist2target(angle[1], target_height, camera_height)

    return (angle[0], distance)


def dist2target(y_angle, target_height, camera_height):
    return abs((target_height - camera_height) / math.tan(math.radians(y_angle)))

def pixel2degrees(point, resolution, diagonal_fov, camera_angle):
    x = point[0]
    y = point[1]
    res_x = resolution[0]
    res_y = resolution[1]

    angle_x = ((res_x / 2) - x) * ptd
    angle_y = ((res_y / 2) - y) * ptd + camera_angle

    return (angle_x, angle_y)

def find_contoured_centroid(mask):
    cnts = cv2.findContours(mask, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)[-2]
    center = None

    if len(cnts) > 0:
        ordered_cnts = sorted(cnts, key = cv2.contourArea)
        top_contours = ordered_cnts[-8:]

        best_pair = find_optimal_contours(top_contours)
        target = cv2.convexHull(np.concatenate(best_pair[0], best_pair[1]))

        moments = cv2.moments(target)
        cx = int(moments['m10'] / moments['m00'])
        cy = int(moments['m01'] / moments['m00'])

        center = (cx, cy)

        logging.info("Contours: %s", str.format(ordered_cnts))
        
        return center

# TODO: implement target cost algorith 
def find_optimal_contours(contours):
    best_contours = None
    for p1, p2 in combinations(contours, 2):
        c1, a1 = p1[0], p1[1]
        c2, a2 = p2[0], p2[1]

        cost = target_cost(c1, c2)
        if cost < lowest_cost:
            lowest_cost = cost
            best_contours = (c1, c2)
    return (c1, c2)

def target_cost(c1, c2):
    M1 = cv2.moments(c1)
    cx1 = int(M1['m10'] / M1['m00'])
    cy1 = int(M1['m01'] / M1['m00'])
    M2 = cv2.moments(c2)
    cx2 = int(M2['m10'] / M2['m00'])
    cy2 = int(M2['m01'] / M2['m00'])

    offset_x = abs(cx1 - cx2)
    offset_y = abs(cy1 - cy2)

    return offset_x * 6 + offset_y

# TODO: use getStructuringElement in later iterations
def erode_dilate_mask(frame):
    blurred = cv2.GaussianBlur(frame, (11, 11), 0)
    hsv = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)

    mask = cv2.inRange(hsv, greenLower, greenUpper)
    eroded = cv2.erode(mask, None, iterations = 2)
    eroded_dilated = cv2.dilate(mask, None, iterations = 2)

    return eroded_dilated

def make_true_frame(frame):
    clear = cv2.undistort(frame, camera_matrix, distortion_matrix, None, new_camera_matrix)

    x, y, w, h = region_of_interest
    clear = clear[y:y + h, x:x + w]

    return clear

def sig_handler(signum, frame):
    segfaults = vision_table.getSubTable('segfaults')
    segfaults.putBoolean("".format(time.time()), True)
    logging.warning("%s occurred at %s at %s.", str.format(signum), str.format(frame), exc_info = True)

with pyrs.Service() as serv:
    with serv.Device() as dev:
        while True:
            try:
                custom_options = [(rs_option.RS_OPTION_COLOR_EXPOSURE, 30.0),
                                  (rs_option.RS_OPTION_COLOR_GAIN, 100.0)]
                dev.set_device_options(*zip(*custom_options))
            except pyrs.RealsenseError:
                logging.warning("Exposure change failed.")

            dev.wait_for_frames()
            frame = dev.color
            frame = cv2.cvtColor(frame, cv2.COLOR_RGB2BGR)

            timer = cv2.getTickCount()

            x_angle, distance = get_target_values(frame)

            fps = cv2.getTickFrequency() / (cv2.getTickCount() - timer)

            vision.putNumber('angle', x_angle)
            vision.putNumber('distance', distance)

            time.sleep(2.9)

