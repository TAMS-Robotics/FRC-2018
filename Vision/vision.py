import numpy as np
import cv2

import sys

redLower = np.array([-10, 100, 100])
redUpper = np.array([10, 255, 255])

def find_contoured_centroid(mask):
    cnts = cv2.findContours(mask, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)[-2]
    center = None

    if len(cnts) > 0:
        ordered_cnts = sorted(cnts, key = cv2.contourArea)
        first = ordered_cnts[-1]
        second = ordered_cnts[-2]
        moments1 = cv2.moments(first)
        moments2 = cv2.moments(second)

        center1 = (int(moments1["m10"] / moments1["m00"]), int(moments1["m01"] / moments1["m00"]))
        center2 = (int(moments2["m10"] / moments2["m00"]), int(moments2["m01"] / moments2["m00"]))

        center = ((center1[0] + center2[0]) / 2, (center1[1] + center2[1]) / 2)

    return center

def erode_dilate_mask(frame):
    blurred = cv2.GaussianBlur(frame, (11, 11), 0)
    hsv = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)

    mask = cv2.inRange(hsv, redLower, redUpper)
    eroded = cv2.erode(mask, None, iterations = 2)
    eroded_dilated = cv2.dilate(mask, None, iterations = 2)

    return eroded_dilated

def pixel2degrees((x, y), (res_x, res_y), diagonal_fov, camera_angle):
    ptd = diagonal_fov / math.sqrt(math.pow(res_x, 2), math.pow(res_y, 2))

    angle_x = ((res_x / 2) - x) * ptd
    angle_y = ((res_y / 2) - cy) * ptd + camera_angle

    return (angle_x, angle_y)

if __name__ == '__main__':
    name = sys.argv[1]
    print(name)
    frame = cv2.imread(name, cv2.IMREAD_COLOR)

    mask = erode_dilate_mask(frame)
    center = find_contoured_centroid(mask)

    print("Center: {}".format(center))
