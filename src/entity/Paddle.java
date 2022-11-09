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

package entity;

import java.util.HashMap;
import utility.Constants;
public class Paddle extends PongEntity{
    private String player_name;
    private int keys[];

    public int score;

    public Paddle(String name , int x, int y, int keys[]) {
        super(x, y, Constants.PADDLE_WIDTH, Constants.PADDLE_HEIGHT);
        this.player_name = name;
        this.score = 0;
        this.keys = keys;
    }

    @Override public void reset(){
        super.reset();
        this.score = 0;
    }

    public String getPlayerName() {
        return player_name;
    }

    @Override public void update() {
        int offset_y = this.y;
        if(keyStates.getOrDefault(keys[0], false)) {
            offset_y -= Constants.PADDLE_SPEED;
            this.y = Math.max(offset_y, 0);
        }
        if(keyStates.getOrDefault(keys[1], false)) {
            offset_y += Constants.PADDLE_SPEED;
            this.y = Math.min(offset_y, this.container.height - Constants.PADDLE_HEIGHT);
        }
    }
    public static HashMap<Integer,Boolean> keyStates = new HashMap<>();
}