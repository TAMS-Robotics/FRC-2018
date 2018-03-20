import cv2
import numpy as np

# Read Image
im = cv2.imread("headPose.jpg");

# 3D model points.
# 33.582 cm, 33.582 cm, 27.94 cm
model_points = np.array([(0.0, 0.0, 0.0),
                         (0.0, -33.582, 0.0),
                         (0.0, -33.582, 27.94),
                         (0.0, 0.0, 27.94),
                         (-33.582, 0.0, 0.0),
                         (-33.582, 0.0, 27.94)])

camera_matrix = np.array([[7.8041108095538573e+02, 0., 320.],
                          [0., 7.8041108095538573e+02, 240.],
                          [0., 0., 1.]])
distortion_matrix = np.array([[1.2967497853950866e-01, 4.5607189281390443e+00, 0., 0., -2.4946392110455260e+01]])

new_camera_matrix, region_of_interest = cv2.getOptimalNewCameraMatrix(camera_matrix, distortion_matrix, (res_x, res_y), 1, (res_x, res_y))

def findBoxRotation(frame):
    clear = make_true_frame(frame)
    mask = erode_dilate_mask(clear)
    cnts = cv2.findContours(mask, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)[-2]
    
    (success, rvecs, tvecs) = cv2.solvePnP(model_points, image_points, new_camera_matrix, distortion_matrix, flags=cv2.CV_ITERATIVE)

    R, _ = cv2.Rodrigues(rvecs)
    side_y = math.sqrt(R[0, 0] * R[0, 0] + R[1, 0] * R[1, 0])
    y = math.atan2(-R[2, 0], side_y)

    cam_angle = -0 * math.pi / 180

    alpha = math.atan(tvecs[0] / tvecs[2])

    tlen = math.sqrt(tvecs[0] * tvecs[0] + tvecs[2] * tvecs[2])

    shift = tlen * (math.sin(cam_angle + alpha) + math.cos(cam_angle + alpha) * math.tan(- y - cam_angle))

    rotation = (cam_angle - y) / math.pi * 180

    return rotation

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
