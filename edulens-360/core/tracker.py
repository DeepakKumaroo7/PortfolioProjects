import numpy as np

class Track:
    def __init__(self, bbox, track_id):
        self.bbox = bbox
        self.id = track_id
        self.age = 0

class SimpleByteTracker:
    def __init__(self, max_age=30, iou_thresh=0.3):
        self.tracks = []
        self.next_id = 1
        self.max_age = max_age
        self.iou_thresh = iou_thresh

    def update(self, detections):
        new_tracks = []

        for det in detections:
            if det["class_id"] != 0:  # track only people
                continue

            bbox = np.array(det["bbox"], dtype=float)
            matched = False

            for track in self.tracks:
                iou = self.compute_iou(track.bbox, bbox)
                if iou > self.iou_thresh:
                    track.bbox = bbox
                    track.age = 0
                    new_tracks.append(track)
                    matched = True
                    break

            if not matched:
                new_tracks.append(Track(bbox, self.next_id))
                self.next_id += 1

        # Age unmatched tracks
        for track in self.tracks:
            track.age += 1
            if track.age < self.max_age and track not in new_tracks:
                new_tracks.append(track)

        self.tracks = new_tracks

        # Output format
        results = []
        for track in self.tracks:
            x1, y1, x2, y2 = map(int, track.bbox)
            results.append({
                "track_id": track.id,
                "bbox": (x1, y1, x2, y2)
            })

        return results

    @staticmethod
    def compute_iou(a, b):
        x1 = max(a[0], b[0])
        y1 = max(a[1], b[1])
        x2 = min(a[2], b[2])
        y2 = min(a[3], b[3])

        inter = max(0, x2 - x1) * max(0, y2 - y1)
        area_a = (a[2] - a[0]) * (a[3] - a[1])
        area_b = (b[2] - b[0]) * (b[3] - b[1])

        union = area_a + area_b - inter
        return inter / union if union > 0 else 0
