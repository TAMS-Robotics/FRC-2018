import logging
logging.basicConfig(level=logging.INFO)

import get_cube_outline as gco
import pyrealsense as pyrs
import numpy as np
import cv2
import sys

with pyrs.Service() as serv:
    with serv.Device() as dev:
        blobber = gco.CubeToBlob()
        dev.apply_ivcam_preset(0)

        while True:
            dev.wait_for_frames()
            c = dev.color
            c = cv2.cvtColor(c, cv2.COLOR_RGB2BGR)
            blobber.process(c)
            c = blobber.find_blobs_output
            c = np.asarray(c)
            if len(c.shape) > 1:
                cv2.imshow('', np.asarray(c))
                print("found")
            if cv2.waitKey(1) & 0xFF == ord('q'):
                break

