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

import utility.Constants;

public class Paddle extends Rectangle{

    private String name;
    private Color fillColor;
    private int speed;

    public Paddle(int x, int y, String name) {
        super(x,y,PADDLE_WIDTH, PADDLE_HEIGHT);
        this.name = name;
        this.fillColor = Color.white;
        this.speed = Constants.SPEED_SET;
    }
    
    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public boolean isColliding(Rectangle rect){
        return (this.x < rect.x + rect.width &&
                this.x + this.width > rect.x &&
                this.y < rect.y + rect.height &&
                this.y + this.height > rect.y);
    }
    public static final int PADDLE_WIDTH = 25 , PADDLE_HEIGHT = 120;
}
