import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JLabel;
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

    private AtomicBoolean game_running;
	private Thread game_thread;
	private Paddle pads[];
	private static final int LEFT = 0 , RIGHT = 1;
	private HashMap<Integer,Boolean> keyPressed;
	private Ball ball;
	private enum GameState{
		START,
		RUNNING,
		PAUSED
	}
	private Stroke strokeAttribute;
	GameState gs = GameState.START;
	private Font raster;
    public Game(){
		this.setPreferredSize(Constants.PREF_SCREEN_SIZE);
		this.game_running = new AtomicBoolean(true);
		this.game_thread = new Thread(this);
		this.keyPressed = new HashMap<>();
		this.pads = new Paddle[]{
			new Paddle("Left",
						10,
						10,
						new int[]{KeyEvent.VK_W,KeyEvent.VK_S}
					),
			new Paddle("Right",
				1066 - Constants.PADDLE_WIDTH - 10,
				683 - Constants.PADDLE_HEIGHT- 10,
				new int[]{KeyEvent.VK_UP,KeyEvent.VK_DOWN}
				)
		};
		Dimension size = new Dimension(1066,683);
		this.pads[0].setContainer(new Rectangle(size));
		this.pads[1].setContainer(new Rectangle(size));
		this.ball = new Ball((1066 - Constants.BALL_WIDTH)/2, 
							(683 - Constants.BALL_HEIGHT)/2
							);
		this.ball.setContainer(new Rectangle(size));
		try {
			raster = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("assets/font/raster.ttf"));
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
			// raster = Font.getFont("")
		}
        createAndShowGui();
		this.game_thread.start();
    }

    public void update(){
		if(Paddle.keyStates.getOrDefault(KeyEvent.VK_ESCAPE, false)) this.game_running.set(false);
		switch(gs){
			case START -> {
				leftScore.setVisible(false);
				rightScore.setVisible(false);
				messageLabel.setFont(raster.deriveFont(30.0f));
				messageLabel.setText("<html>Press Spacebar to Start Game</html>");
				if(Paddle.keyStates.getOrDefault(KeyEvent.VK_SPACE, false)){
					gs = GameState.RUNNING;
					leftScore.setVisible(true);
					rightScore.setVisible(true);
					messageLabel.setVisible(false);
				}
			}
			case RUNNING -> {
				ball.update();
				pads[0].update();
				if(pads[0].intersects(ball)) ball.resolveXVel(pads[0].x + pads[0].width, 1);
				pads[1].update();
				if(pads[1].intersects(ball)) ball.resolveXVel(pads[1].x - ball.width, -1);
				if(ball.getIsGoalColliding()) {
					// win Proc.
					if( ball.x <= 0 ) pads[1].score += 1;
					else pads[0].score += 1;
					if(pads[0].score >= Constants.WIN_CONDITION) {
						messageLabel.setText("<html><p>Left Player Won!</p>Press SpaceBar to Replay</html>");
						gs = GameState.PAUSED;
					}
					else if(pads[1].score >= Constants.WIN_CONDITION){
						messageLabel.setText("<html><p>Right Player Won!</p>Press SpaceBar to Replay</html>");
						gs = GameState.PAUSED;
					}
					ball.reset();
				}
				leftScore.setText("" + pads[0].score);
				rightScore.setText("" + pads[1].score);
			}
			case PAUSED -> {
				leftScore.setVisible(false);
				rightScore.setVisible(false);
				messageLabel.setVisible(true);
				if(Paddle.keyStates.getOrDefault(KeyEvent.VK_SPACE, false)){
					pads[0].reset();
					pads[1].reset();
					ball.reset();
					gs = GameState.RUNNING;
					leftScore.setVisible(true);
					rightScore.setVisible(true);
					messageLabel.setVisible(false);
				}
			}
		}
    }

    @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g);
		Graphics2D gd = (Graphics2D)g;
		for(Paddle pad : pads){
			gd.setColor(pad.getFillColor());
			gd.fillRect(pad.x, pad.y, pad.width, pad.height);
		}
		gd.setColor(ball.getFillColor());
		gd.fillOval(ball.x, ball.y, ball.width, ball.height);
		if(gs == GameState.RUNNING) {
			Stroke s = gd.getStroke();
			gd.setStroke(strokeAttribute);
			gd.drawLine(1066/2, 0, 1066/2, 683);
			gd.setStroke(s);
		}
    }

    public void createAndShowGui(){
        this.setBackground(new Color(51, 51, 51));
		this.strokeAttribute = new BasicStroke(
			10,
			BasicStroke.CAP_SQUARE , 
			BasicStroke.JOIN_BEVEL ,
			0,
			new float[]{35.0f},
			0f
		);
        java.awt.GridBagConstraints gridBagConstraints;

        leftScore = new javax.swing.JLabel();
        messageLabel = new javax.swing.JLabel();
        rightScore = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());

        leftScore.setText("#");
		leftScore.setFont(raster.deriveFont(72.0f));
		leftScore.setForeground(Color.white);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weighty = 0.1;
		gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 100);
        add(leftScore, gridBagConstraints);

        messageLabel.setText("");
		messageLabel.setFont(raster.deriveFont(28.0f));
		messageLabel.setForeground(Color.white);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 300, 0, 300);
        add(messageLabel, gridBagConstraints);

        rightScore.setText("#");
		rightScore.setFont(raster.deriveFont(72.0f));
		rightScore.setForeground(Color.white);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weighty = 0.1;
		gridBagConstraints.insets = new java.awt.Insets(0, 100, 0, 0);
        add(rightScore, gridBagConstraints);
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

	@Override
	public void keyPressed(KeyEvent e) {
		Paddle.keyStates.put(e.getKeyCode(), true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		Paddle.keyStates.put(e.getKeyCode(), false);
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	private JLabel leftScore, rightScore, messageLabel;
}