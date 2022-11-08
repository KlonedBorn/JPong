import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JPanel;

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

public class Game extends JPanel implements Runnable{

    private AtomicBoolean game_running;
	private Thread game_thread;
	private Paddle pads[];
	private static final int LEFT = 0 , RIGHT = 1;
    public Game(){
		this.game_running = new AtomicBoolean(true);
		this.game_thread = new Thread(this);
		this.pads = new Paddle[]{
			new Paddle("Left", 10, 10),
			new Paddle("Right", 1066 - Constants.PADDLE_WIDTH - 10,683 - Constants.PADDLE_HEIGHT- 10)
		};
		System.out.println();
        createAndShowGui();
		this.game_thread.start();
    }

    public void update(){
		
    }

    @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g);
		Graphics2D gd = (Graphics2D)g;
		for(Paddle pad : pads){
			gd.setColor(pad.getFillColor());
			gd.fillRect(pad.x, pad.y, pad.width, pad.height);
		}
    }

    public void createAndShowGui(){
        this.setBackground(new Color(51, 51, 51));
    }

	@Override public void run() {
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

	@Override
	public Dimension getPreferredSize() {
		return Constants.PREF_SCREEN_SIZE;
	}
}