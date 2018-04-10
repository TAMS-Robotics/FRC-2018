import cv2
import numpy as np
import pyrealsense as pyrs

import math

from cube_contours import CubeContours

NetworkTables.initialize(server = '10.52.12.2')
vision = NetworkTables.getTable('vision')

logging.basicConfig(filename = 'vision.log', level = logging.DEBUG, format = '%(asctime)s %(message)s')

# 3D model points.
# 33.582 cm, 33.582 cm, 27.94 cm
# Only 3.81 cm of the cube will be visible while in the arm
model_points = np.array([[0.0, 0.0, 0.0],
                         [0.0, 0.0, -27.94],
                         [0.0, 33.582, -27.94],
                         [-33.582, 33.582, -27.94]
                         [-33.582, 33.582, 0.0],
                         [-33.582, 0.0, 0.0]])

camera_matrix = np.array([[7.8041108095538573e+02, 0., 320.],
                          [0., 7.8041108095538573e+02, 240.],
                          [0., 0., 1.]])
distortion_matrix = np.array([[1.2967497853950866e-01, 4.5607189281390443e+00, 0., 0., -2.4946392110455260e+01]])

new_camera_matrix, region_of_interest = cv2.getOptimalNewCameraMatrix(camera_matrix, camera_distortion_matrix, (res_x, res_y), 1, (res_x, res_y))

def findBoxRotation(target):
    tmp = []
    for pt in target:
        tmp.append(pt[0][0], pt[0][1])
    image_points = np.array(tmp, dtype = np.float64)

    logging.info("Image points: %s", str.format(image_points))

    _, rvecs, tvecs = cv2.solvePnP(model_points, image_points, camera_matrix, distortion_matrix, flags = cv2.CV_ITERATIVE)

    R, _ = cv2.Rodrigues(rvecs)
    side_y = math.sqrt(R[0, 0] * R[0, 0] + R[1, 0] * R[1, 0])
    y = math.atan2(-R[2, 0], side_y)

    cam_angle = -0 * math.pi / 180

    rotation = (cam_angle - y) / math.pi * 180

    logging.info("Rodrigues output: %s", str.format(R))
    logging.info("side_y output: %s", str.format(side_y))
    logging.info("y: %s", str.format(y))
    logging.info("Rotation: %s degrees", str.format(rotation))

    return rotation

def make_true_frame(frame):
    clear = cv2.undistort(frame, camera_matrix, distortion_matrix, None, new_camera_matrix)

    x, y, w, h = region_of_interest
    clear = clear[y:y + h, x:x + w]

    return clear

with pyrs.Service() as serv:
    with serv.Device() as dev:
        while True:
            # try:
            #     custom_options = [(rs_option.RS_OPTION_COLOR_EXPOSURE, 30.0),
            #                       (rs_option.RS_OPTION_COLOR_GAIN, 100.0)]
            #     dev.set_device_options(*zip(*custom_options))
            # except pyrs.RealsenseError:
            #     logging.warning("Exposure change failed.")

            dev.wait_for_frames()
            frame = dev.color
            frame = cv2.cvtColor(frame, cv2.COLOR_RGB2BGR)
            frame = make_true_frame(frame)

            boxfinder = CubeContours()
            boxfinder.process(frame)

            if len(boxfinder.find_contours_output) > 0:
                logging.info("Contours (supposedly) found.")
                logging.info("Convex hulls: %s", str.format(boxfinder.convex_hulls_output))

                target = boxfinder.convex_hulls_output
                rotation = findBoxRotation(target)

                vision.putNumber('rotation', rotation)
