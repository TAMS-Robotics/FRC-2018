#!/bin/bash

gst-launch-1.0 -v videotestsrc ! video/x-raw, width=1280, height=720 ! xvimagesink
