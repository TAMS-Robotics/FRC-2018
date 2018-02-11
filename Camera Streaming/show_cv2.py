import logging
logging.basicConfig(level=logging.INFO)

import cv2
import pyrealsense as pyrs
import numpy as np

with pyrs.Service() as serv:
    with serv.Device() as dev:

        dev.apply_ivcam_preset(0)

        while True:

            dev.wait_for_frames()
            c = dev.color
            c = cv2.cvtColor(c, cv2.COLOR_RGB2BGR)

            cv2.imshow('', c)
            if cv2.waitKey(1) & 0xFF == ord('q'):
                break
