from ultralytics import YOLO
import cv2

class YOLODetector:
    def __init__(self, model_path="yolov8n.pt", conf_threshold=0.4):
        """
        model_path: YOLOv8 model file
        conf_threshold: minimum confidence for detections
        """
        self.model = YOLO(model_path)
        self.conf_threshold = conf_threshold

    def detect(self, frame):
        """
        Runs YOLOv8 inference on a frame.
        Returns a list of detections.
        """
        results = self.model(frame, verbose=False)[0]

        detections = []

        for box in results.boxes:
            conf = float(box.conf[0])
            if conf < self.conf_threshold:
                continue

            cls_id = int(box.cls[0])
            x1, y1, x2, y2 = map(int, box.xyxy[0])
            # w = x2 - x1
            # h = y2 - y1

            # # # Ignore tiny boxes (likely shoulders / noise)
            # # if w < 60 or h < 100:
            # #     continue
            # aspect_ratio = h / float(w + 1e-6)
            # if aspect_ratio < 1.2:
            #     continue
            detections.append({
                "bbox": (x1, y1, x2, y2),
                "confidence": conf,
                "class_id": cls_id,
                "class_name": self.model.names[cls_id]
            })

        return detections

    def draw_detections(self, frame, detections):
        """
        Draws bounding boxes on the frame.
        """
        for det in detections:
            x1, y1, x2, y2 = det["bbox"]
            label = f'{det["class_name"]} {det["confidence"]:.2f}'

            cv2.rectangle(frame, (x1, y1), (x2, y2), (0, 255, 0), 2)
            cv2.putText(
                frame,
                label,
                (x1, y1 - 10),
                cv2.FONT_HERSHEY_SIMPLEX,
                0.5,
                (0, 255, 0),
                2
            )

        return frame
