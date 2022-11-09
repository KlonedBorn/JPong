// Copyright 2022 Kyle King
// 
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// 
//     http://www.apache.org/licenses/LICENSE-2.0
// 
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package utility;

import java.awt.Dimension;

public class Constants {
    public static float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }
    public static final int PREF_SCREEN_WIDTH = 1080, 
                            PREF_SCREEN_HEIGHT = 720,
                            MIN_SCREEN_WIDTH = PREF_SCREEN_WIDTH,
                            MIN_SCREEN_HEIGHT = PREF_SCREEN_WIDTH,
                            MAX_SCREEN_WIDTH = PREF_SCREEN_WIDTH,
                            MAX_SCREEN_HEIGHT = PREF_SCREEN_WIDTH,
                            FPS_SET = 60,
                            UPS_SET = 120,
                            PADDLE_WIDTH = 25,
                            PADDLE_HEIGHT = 125,
                            PADDLE_SPEED = 8,
                            BALL_WIDTH = 25,
                            BALL_HEIGHT = 25,
                            BALL_X_SPEED = 2,
                            BALL_Y_SPEED = 2,
                            WIN_CONDITION = 5;
    public static final Dimension PREF_SCREEN_SIZE = new Dimension(PREF_SCREEN_WIDTH,PREF_SCREEN_HEIGHT);
}
