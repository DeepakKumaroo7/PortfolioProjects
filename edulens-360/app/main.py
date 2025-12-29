import cv2
from core.video_stream import VideoStream
from core.detector import YOLODetector
from core.tracker import SimpleByteTracker
from core.temporal import TemporalAnalyzer



def main():
    stream = VideoStream(source=0)
    detector = YOLODetector()
    tracker = SimpleByteTracker()
    temporal = TemporalAnalyzer()

    while True:
        frame = stream.read()
        if frame is None:
            break

        detections = detector.detect(frame)
        tracks = tracker.update(detections)
        temporal_info = temporal.update(tracks)

        # Draw tracking IDs
        for obj in tracks:
            x1, y1, x2, y2 = obj["bbox"]
            tid = obj["track_id"]
            info = temporal_info.get(tid, {})
            state = info.get("motion_state", "unknown")
            speed = info.get("speed", 0.0)

            label = f"ID {tid} | {state}"

            cv2.rectangle(frame, (x1, y1), (x2, y2), (255, 0, 0), 2)
            cv2.putText(
                frame,
                label,
                (x1, y1 - 10),
                cv2.FONT_HERSHEY_SIMPLEX,
                0.6,
                (255, 0, 0),
                2
            )

        cv2.imshow("EduLens - Tracking", frame)
        if cv2.waitKey(1) & 0xFF == ord("q"):
            break

    stream.release()
    cv2.destroyAllWindows()

if __name__ == "__main__":
    main()
