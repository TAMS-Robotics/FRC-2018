#!/usr/bin/env python

import cv2
import numpy as np

# Read Image
im = cv2.imread("headPose.jpg");
size = im.shape
    
#2D image points. If you change the image, you need to change vector
image_points = np.array([], dtype="double")

# 3D model points.
model_points = np.array([])

focal_length = 0
center = 0
camera_matrix = np.array()

print "Camera Matrix :\n {0}".format(camera_matrix);

dist_coeffs = np.zeros((4,1))
(success, rvecs, tvecs) = cv2.solvePnP(model_points, image_points, camera_matrix, dist_coeffs, flags=cv2.CV_ITERATIVE)

print "Rotation Vector:\n {0}".format(rotation_vector)
print "Translation Vector:\n {0}".format(translation_vector)

R, _ = cv2.Rodrigues(rvecs)
side_y = math.sqrt(R[0, 0] * R[0, 0] + R[1, 0] * R[1, 0])
y = math.atan2(-R[2, 0], side_y)

cam_angle = -0 * math.pi / 180

alpha = math.atan(tvecs[0] / tvecs[2])

tlen = math.sqrt(tvecs[0] * tvecs[0] + tvecs[2] * tvecs[2])

shift = tlen * (math.sin(cam_angle + alpha) + math.cos(cam_angle + alpha) * math.tan(- y - cam_angle))

rotation = (cam_angle - y) / math.pi * 180



