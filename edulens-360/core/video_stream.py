import cv2

class VideoStream:
    def __init__(self, source=0, width=640, height=480):
        """
        source: 0 for webcam, or path to video file
        """
        self.cap = cv2.VideoCapture(source)
        self.width = width
        self.height = height

        if not self.cap.isOpened():
            raise RuntimeError("Failed to open video source")

    def read(self):
        ret, frame = self.cap.read()
        if not ret:
            return None

        frame = cv2.resize(frame, (self.width, self.height))
        return frame

    def release(self):
        self.cap.release()
