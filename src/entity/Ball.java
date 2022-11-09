package entity;
import java.awt.Point;
import java.util.Random;

import utility.Constants;

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

public class Ball extends PongEntity {
    
    private Point center;
    private int x_vel , y_vel , x_speed  , y_speed ;
    private boolean is_colliding_goal = false;

    public Ball(int init_x, int init_y) {
        super(init_x,init_y,Constants.BALL_WIDTH,Constants.BALL_HEIGHT);
        this.center = new Point();
        this.center.setLocation(init_x+Constants.BALL_WIDTH/2, init_y+Constants.BALL_HEIGHT/2);
        this.shuffleVelocity();
        this.x_speed = Constants.BALL_X_SPEED;
        this.y_speed = Constants.BALL_Y_SPEED;
    }

    public Point getCenter(){
        this.center.setLocation(this.x+Constants.BALL_WIDTH/2, this.y+Constants.BALL_HEIGHT/2);
        return this.center;
    }
    public void shuffleVelocity(){
        Random rand = new Random();
        this.x_vel = (rand.nextBoolean()?1:-1);
        this.y_vel = (rand.nextBoolean()?1:-1); 
    }
    
    @Override
    public void reset() {
        super.reset();
        this.shuffleVelocity();
        is_colliding_goal = false;
    }

    

    public boolean getIsGoalColliding() {
        return is_colliding_goal;
    }

    @Override
    public void update() {
        if(!this.is_colliding_goal){
            int new_x = this.x_vel * this.x_speed + this.x;
            int new_y = this.y_vel * this.y_speed + this.y;
            this.is_colliding_goal = ( new_x > this.container.width - this.width) || ( new_x < 0 );
            if( new_y < 0 ) {
                this.y_vel = 1;
                new_y = 0;
            }
            if( new_y > this.container.height - this.height){
                this.y_vel = -1;
                new_y = this.container.height - this.height;
            }
            this.x = new_x;
            this.y = new_y;
        }
    }
    public void resolveXVel(int x , int vel){
        this.x = x;
        this.x_vel = vel;
    }
}