import numpy as np
import cv2
import pyrealsense as pyrs
from pyrealsense.constants import rs_option

import time
import pyximport; pyximport.install()

import z16tobgr

depth_fps = 90
depth_stream =  pyrs.stream.DepthStream(fps = depth_fps)

with pyrs.Service() as serv:
    with serv.Device(streams = (depth_stream,)) as dev:
        dev.apply_ivcam_preset(0)

        try:
            custom_options = [(rs_option.RS_OPTION_R200_LR_EXPOSURE, 30.0),
                              (rs_option.RS_OPTION_R200_LR_GAIN, 100.0)]
            dev.set_device_options(*zip(*custom_options))
        except pyrs.RealsenseError:
            pass

        cnt = 0
        last = time.time()
        smoothing = 0.9
        fps_smooth = depth_fps

        while True:
            cnt  += 1
            if (cnt % 30) == 0:
                now = time.time()
                dt = now - last
                fps = 30 / dt
                fps_smooth = (fps_smooth * smoothing) + (fps * (1.0 - smoothing))
                last = now

            dev.wait_for_frames()
            d = dev.depth
            d = z16tobgr(d)

            cv2.putText(d, str(fps_smooth)[:4], (0, 50), cv2.FONT_HERSHEY_SIMPLEX, 2, (255, 255, 255))

            cv2.imshow("", d)
            if cv2.waitKey(1) & 0xFF == ord('q'):
                break

