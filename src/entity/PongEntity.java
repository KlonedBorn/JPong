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

public abstract class PongEntity extends Rectangle{
    protected int init_x , init_y;
    protected java.awt.Color fill_color;
    protected Rectangle container;

    public PongEntity(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.init_x = x;
        this.init_y = y;
        this.fill_color = Color.white;
        this.container = new Rectangle();
    }

    abstract public void update();
    public Color getFillColor(){
        return this.fill_color;
    }
    public void setContainer(Rectangle rect){
        this.container = rect;
    }
    public void reset(){
        this.setLocation(init_x, init_y);
    }
}
