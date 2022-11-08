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

import java.awt.Color;
import java.awt.Rectangle;
import java.util.Random;

import utility.Constants;

public class Ball extends Rectangle{
    private Color fillColor;
    private int speed, xinit , yinit;
    public int xvel , yvel;
    public Ball(int x, int y) {
        super(x,y,WIDTH, HEIGHT);
        this.xinit = x;
        this.yinit = y;
        Random rand = new Random();
        xvel = (rand.nextBoolean()?1:-1);
        yvel = (rand.nextBoolean()?1:-1);
        speed = Constants.SPEED_SET -8;
    }
    
    public int getSpeed() {
        return speed;
    }

    public void center(){
        Random rand = new Random();
        xvel = (rand.nextBoolean()?1:-1);
        yvel = (rand.nextBoolean()?1:-1);
        this.setLocation(this.xinit, this.yinit);
    }

    public static final int WIDTH = 25 , HEIGHT = 25;
    public Color getFillColor() {
        return fillColor;
    }
}