import logging
logging.basicConfig(level=logging.INFO)

import cv2
import pyrealsense as pyrs
import numpy as np
from pyrealsense.constants import rs_option

with pyrs.Service() as serv:
    with serv.Device() as dev:

        dev.apply_ivcam_preset(0)
        
        try:  # set custom gain/exposure values to obtain good depth image
            custom_options = [(rs_option.RS_OPTION_R200_LR_EXPOSURE, 30.0),
                              (rs_option.RS_OPTION_R200_LR_GAIN, 100.0)]
            dev.set_device_options(*zip(*custom_options))
        except pyrs.RealsenseError:
            print("R200 try failed")
            
            try: 
                custom_options = [(rs_option.RS_OPTION_COLOR_EXPOSURE, 30.0),
                                  (rs_option.RS_OPTION_COLOR_GAIN , 100.0)]
                dev.set_device_options(*zip(*custom_options))
            except pyrs.RealsenseError:
                print("Both failed")
        
        count = 0

        while True:
            count += 1
            dev.wait_for_frames()
            c = dev.color
            c = cv2.cvtColor(c, cv2.COLOR_RGB2BGR)
            
            if count == 10:
                cv2.imwrite("test_out.png", c)
            
            cv2.imshow('', c)
            if cv2.waitKey(1) & 0xFF == ord('q'):
                break
