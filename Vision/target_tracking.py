import cv2
import numpy as np
import pyrealsense as pyrs

from networktables import NetworkTables

import sys
import os
import signal
import time
import math
import time
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
        first = ordered_cnts[len(ordered_cnts) - 1]
        second = ordered_cnts[len(ordered_cnts) - 2]
        moments1 = cv2.moments(first)
        moments2 = cv2.moments(second)

        center1 = (int(moments1["m10"] / moments1["m00"]), int(moments1["m01"] / moments1["m00"]))
        center2 = (int(moments2["m10"] / moments2["m00"]), int(moments2["m01"] / moments2["m00"]))

        center = ((center1[0] + center2[0]) / 2, (center1[1] + center2[1]) / 2)

        logging.info("Contours: %s", str.format(ordered_cnts))

    return center
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
    logging.warning("%s occurred at %s at %s.", str.format(signum), str.format(frame), exc_info = True)

with pyrs.Service() as serv:
    with serv.Device() as dev:
        while True:
            dev.wait_for_frames()
            frame = dev.color
            frame = cv2.cvtColor(frame, cv2.COLOR_RGB2BGR)

            timer = cv2.getTickCount()

            x_angle, distance = get_target_values(frame)

            fps = cv2.getTickFrequency() / (cv2.getTickCount() - timer)

            vision.putNumber('angle', x_angle)
            vision.putNumber('distance', distance)

            time.sleep(2.9)

