import numpy as np
import cv2

import time
import sys
sys.path.append("../")

import vision as v

if __name__ == "__main__":
    start_time = time.time()
    for _ in range(0, 1000):
        main()
    end_time = time.time()
    print("--- {} seconds ---".format(end_time - start_time))

def main():
    name = sys.argv[1]
    print(name)
    frame = cv2.imread(name, cv2.IMREAD_COLOR)

    mask = v.erode_dilate_mask(frame)
    center = v.find_contoured_centroid(mask)

    print("Center: {}".format(center))

