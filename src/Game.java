import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.JPanel;
import entity.Ball;
import entity.Paddle;
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
public class Game extends JPanel implements Runnable , KeyListener{

    private Thread game_thread;
    private AtomicBoolean game_running;
    private Paddle paddles[];
    private Ball ball;
    private boolean keyPressed[];

    public Game() {
        this.game_thread = new Thread(this);
        this.game_running = new AtomicBoolean(true);
        this.setBackground(new Color(51, 51, 51));
        this.paddles = new Paddle[]{
            new Paddle(10,10,"left"),
            new Paddle(Constants.SCREEN_WIDTH - (Paddle.PADDLE_WIDTH +  21),Constants.SCREEN_HEIGHT - (Paddle.PADDLE_HEIGHT+45), "right")
        };
        this.setSize(Constants.SCREEN_DIMENSION);
        this.ball = new Ball((int)(this.getWidth() - Ball.WIDTH)/2,(int)(this.getHeight()-Ball.HEIGHT)/2);
        this.keyPressed = new boolean[256];
        Arrays.fill(this.keyPressed, false);
    }

    /**
     * 2 Paddles each representing a player
     * 1 Ball
     * 2 Text Objects to display the score
     * 1 Text object to inform the players who won
     * 1 Button to allow players to replay the game
     */
    public void initComponents(){

    }

    public void update(){
        if( keyPressed['W'] && paddles[0].y - 10 >= 0 )paddles[0].y -= 10;
        if( keyPressed['S'] && paddles[0].y + 10 <= this.getHeight() - paddles[0].height - 1 ) paddles[0].y += 10;
        if( keyPressed[KeyEvent.VK_UP] && paddles[1].y - 10 >= 0)  paddles[1].y -= 10;
        if( keyPressed[KeyEvent.VK_DOWN] && paddles[1].y + 10 <= this.getHeight() - paddles[0].height - 1) paddles[1].y += 10;

        int offsetX = ball.x + ball.xvel * ball.getSpeed() , offsetY = ball.y + ball.yvel*ball.getSpeed();

        if( paddles[0].isColliding(ball) ) {
            offsetX = paddles[0].x+paddles[0].width+(ball.width/2);
            ball.xvel = 1;
        }
        if( paddles[1].isColliding(ball)) {
            offsetX = paddles[1].x-paddles[1].width-(ball.width/2);
            ball.xvel = -1;
        }
        if( offsetY <= 0 && ball.yvel < 0 ) {
            offsetY =  1;
            ball.yvel = 1;
        }
        if( offsetY > (this.getHeight() - ball.getHeight() -1) && ball.yvel > 0 ) {
            offsetY = this.getHeight() - (int)ball.getHeight() -1;
            ball.yvel = -1;
        }
        ball.x = offsetX;
        ball.y = offsetY;
        if(ball.x <= 0 || ball.x > this.getWidth()) ball.center();
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for(Paddle pad : paddles){
            g.setColor(pad.getFillColor());
            g.fillRect(pad.x, pad.y, pad.width, pad.height);
        }
        g.setColor(ball.getFillColor());
        g.fillOval(ball.x, ball.y, ball.width, ball.height);
        drawDashedLine(g, this.getWidth()/2, 0, this.getWidth()/2, this.getHeight());
    }

    public void drawDashedLine(Graphics g, int x1, int y1, int x2, int y2){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.white);
        float dash[] = {10.0f};
        Stroke dashed = new BasicStroke(
            10,
            BasicStroke.CAP_ROUND, 
            BasicStroke.JOIN_ROUND, 
            0,
            new float[]{20.0f},
            0
        );
        g2d.setStroke(dashed);
        g2d.drawLine(x1, y1, x2, y2);
    }
    @Override
    public void keyPressed(KeyEvent e) {
        keyPressed[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyPressed[e.getKeyCode()] = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void run() {
        long elapsedTime = System.nanoTime();
        double prevFrame = 0 , prevUpdate = 0;
        while(game_running.get()){
		double timePerFrame = 1000000000.0 /  utility.Constants.FPS_SET;
		double timePerUpdate = 1000000000.0 / utility.Constants.UPS_SET;

		long previousTime = System.nanoTime();

		int frames = 0;
		int updates = 0;
		long lastCheck = System.currentTimeMillis();

		double deltaU = 0;
		double deltaF = 0;

		while (true) {
			long currentTime = System.nanoTime();
			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;

			if (deltaU >= 1) {
				update();
				updates++;
				deltaU--;
			}

			if (deltaF >= 1) {
                this.repaint();
				frames++;
				deltaF--;
			}

			if (System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
				System.out.println("FPS: " + frames + " | UPS: " + updates);
				frames = updates = 0;
			}
		}
        }
    }

    public void start(){
        game_thread.start();
    }
}