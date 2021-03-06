import numpy as np
import cv2
import pyrealsense

import sys
import os
import signal
import time
import math

from networktables import NetworkTables

NetworkTables.initialize(server = '10.52.12.2')
vision_table = NetworkTables.getTable('vision') 

greenLower = np.array([40, 100, 100])
greenUpper = np.array([90, 255, 255])

resolution = (1920, 1080)
diagonal_fov = 75.2
target_height = 9.375
camera_height = 2.7916667
camera_angle = 0

def get_target_values(frame):
    mask = erode_dilate_mask(frame)
    center = find_contoured_centroid(mask)

    angle = pixel2degrees(center, resolution, diagonal_fov, camera_angle)
    distance = dist2target(angle[1], target_height, camera_height)

    return (angle[1], distance)


def dist2target(y_angle, target_height, camera_height):
    return abs((target_height - camera_height) / math.tan(math.radians(y_angle)))

def pixel2degrees(point, resolution, diagonal_fov, camera_angle):
    x = point[0]
    y = point[1]
    res_x = resolution[0]
    res_y = resolution[1]

    ptd = diagonal_fov / math.sqrt(math.pow(res_x, 2) + math.pow(res_y, 2))

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
        x, y, w, h = cv2.boundingRect(first)
        img = cv2.imread("tests/tape2.png", cv2.IMREAD_COLOR)
        thing = cv2.rectangle(img,(x,y),(x+w,y+h),(0,255,0),2)
        cv2.imshow("test", thing)
        cv2.waitKey(0)
        moments1 = cv2.moments(first)
        moments2 = cv2.moments(second)

        center1 = (int(moments1["m10"] / moments1["m00"]), int(moments1["m01"] / moments1["m00"]))
        center2 = (int(moments2["m10"] / moments2["m00"]), int(moments2["m01"] / moments2["m00"]))

        center = ((center1[0] + center2[0]) / 2, (center1[1] + center2[1]) / 2)

    return center

def erode_dilate_mask(frame):
    blurred = cv2.GaussianBlur(frame, (11, 11), 0)
    hsv = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)

    mask = cv2.inRange(hsv, greenLower, greenUpper)
    eroded = cv2.erode(mask, None, iterations = 2)
    eroded_dilated = cv2.dilate(mask, None, iterations = 2)

    return eroded_dilated

def sig_handler(signum, frame):
    segfaults = vision_table.getSubTable('segfaults')
    segfaults.putBoolean("".format(time.time()), True)


if __name__ == '__main__':
    signal.signal(signal.SIGSEGV, sig_handler)
    name = sys.argv[1]
    frame = cv2.imread(name, cv2.IMREAD_COLOR)
    mask = erode_dilate_mask(frame)
    cv2.imshow("mask", mask)
    cv2.waitKey(0)
    center = find_contoured_centroid(mask)
    angle = pixel2degrees(center, (1920, 1080), 75.2, 0)
    distance = dist2target(angle[1], 9.375, 2.791666667) / 12.0

    print("Center: {}".format(center))
    print("Angle: {}".format(angle))
    print("Ditance: {}".format(distance))

