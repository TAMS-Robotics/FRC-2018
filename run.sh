bash ./Camera\ Streaming/gstreamer_server.sh 2 10.52.12.4 5805 &
python ./Camera\ Streaming/show_cv2.py &&
kill $!
