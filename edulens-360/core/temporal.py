import time
import math
from collections import deque

class TemporalAnalyzer:
    def __init__(self, max_history=30, idle_speed_thresh=15):
        """
        max_history: number of frames to keep per track
        idle_speed_thresh: pixel movement threshold for idle vs moving
        """
        self.track_history = {}
        self.max_history = max_history
        self.idle_speed_thresh = idle_speed_thresh

        # NEW: motion stability tracking
        self.motion_counter = {}
        self.motion_confirm_frames = 1

    def update(self, tracks):
        """
        tracks: list of {track_id, bbox}
        returns: dict of temporal features per track
        """
        results = {}

        current_time = time.time()

        for obj in tracks:
            tid = obj["track_id"]
            x1, y1, x2, y2 = obj["bbox"]

            # Compute centroid
            cx = (x1 + x2) / 2.0
            cy = (y1 + y2) / 2.0

            if tid not in self.track_history:
                self.track_history[tid] = {
                    "centroids": deque(maxlen=self.max_history),
                    "first_seen": current_time,
                    "last_seen": current_time
                }

            history = self.track_history[tid]
            history["centroids"].append((cx, cy))
            history["last_seen"] = current_time

            # ---- Velocity computation ----
            speed = 0.0
            if len(history["centroids"]) >= 2:
                (x_prev, y_prev) = history["centroids"][-2]
                speed = math.sqrt((cx - x_prev) ** 2 + (cy - y_prev) ** 2)

            # ---- Dwell time ----
            dwell_time = current_time - history["first_seen"]

            # ---- Motion state ----
            if tid not in self.motion_counter:
                self.motion_counter[tid] = 0

            if speed > self.idle_speed_thresh:
                self.motion_counter[tid] += 1
            else:
                self.motion_counter[tid] = max(0, self.motion_counter[tid] - 1)

            motion_state = (
                "moving"
                if self.motion_counter[tid] >= self.motion_confirm_frames
                else "idle"
            )

            results[tid] = {
                "speed": speed,
                "dwell_time": dwell_time,
                "motion_state": motion_state,
                "centroid": (int(cx), int(cy))
            }

        return results
