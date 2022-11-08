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

import java.awt.Rectangle;
import utility.Constants;

public class Paddle extends Rectangle{
    private int init_x , init_y , speed;
    public int score;
    private String player_name;
    private java.awt.Color fill_color;

    public Paddle(String name , int x, int y) {
        super(x, y, Constants.PADDLE_WIDTH, Constants.PADDLE_HEIGHT);
        this.init_x = x;
        this.init_y = y;
        this.player_name = name;
        this.score = 0;
        this.fill_color = java.awt.Color.white;
    }

    public void reset(){
        setLocation(init_x, init_y);
        this.score = 0;
    }

    public void move(int direction){
        direction = (int)Constants.clamp(direction, -1, 1);
    }

    public String getPlayer_name() {
        return player_name;
    }

    public java.awt.Color getFillColor() {
        return fill_color;
    }
    public static int getRightTopCorner(Paddle p){
        return p.x + p.width;
    }
}
