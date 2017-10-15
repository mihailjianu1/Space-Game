package com.game.src.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JFrame;

import com.game.src.main.classes.EntityA;
import com.game.src.main.classes.EntityB;

public class Game extends Canvas implements Runnable{
	
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 320;
	public static final int HEIGHT = WIDTH / 4 * 3;
	public static final int SCALE = 2;
	public final String TITLE = "2D Space Game";
	
	public static final double bullet_speed=5;
	public static final double player_speed=3;
	public static final double enemy_min_speed=1;
	public static final double enemy_max_speed=3;
	
	private boolean upPressed = false;
	private boolean downPressed = false;
	private boolean rightPressed = false;
	private boolean leftPressed = false;
	private boolean spacePressed = false;
	
	private boolean running = false;
	private Thread thread;
	
	private BufferedImage spriteSheet = null;
	private BufferedImage background = null;
	
	private int enemy_count;
	private int enemy_killed;
	private int total_killed;
	
	private Player p;
	private Controller c;

	private Textures tex;
	
	public LinkedList<EntityA> ea;
	public LinkedList<EntityB> eb;

	public static int HCONST = 2;
	public int health, max_health;
	
	private Menu menu;
	private Options options;
	private Help help;
	private Score score;
	
	private int space = 30;
	private int delay_text = 30;
	private int lvl = 1;
	private int score_text_space = 180;
	
	public enum STATE{
		MENU,
		OPTIONS, 
		HELP,
		SCORE,
		GAME
	};
	
	private STATE State = STATE.MENU;
	
	public void initGame(){
		c = new Controller(tex, this);
		p = new Player(200, 200, 32, 32, player_speed, tex, this, c);
		
		enemy_count = 2;
		enemy_killed = 0;
		health = 100;
		max_health = 100;
		total_killed = 0;
		
		c.createEnemy(enemy_count);
		
		ea = c.getEntityA();
		eb = c.getEntityB();
	}
	
	public void init (){
		requestFocus();
		
		menu = new Menu(this);
		options = new Options(this);
		help = new Help(this);
		score = new Score(this);
		
		BufferedImageLoader loader = new BufferedImageLoader();
		try{
			spriteSheet = loader.loadImage("/sprite_sheet.png");
			background = loader.loadImage("/background.png");
		}catch(IOException e){
			e.printStackTrace();
		}	
	
		addKeyListener(new KeyInput(this));
		addMouseListener(new MouseInput(this));
		
		tex = new Textures(this);
		
		initGame();
	}


	private synchronized void start(){
		if(running)
			return;
		
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	private synchronized void stop(){
		if(!running)
			return;
		
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(1);
	}
	
	public void run(){
		init();
		long lastTime = System.nanoTime();
		final double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();
		
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns; //
			lastTime = now;
			if (delta >= 1){
				tick();
				updates++;
				delta--;
			}
			render();
			frames++;
			
			if (System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				System.out.println(updates + " Ticks, Fps " + frames);
				updates = 0;
				frames = 0;
			}
		}
		stop();
	}
	
	private void tick(){
		if(State == STATE.GAME){
			p.tick();
			c.tick();	
		}
		if(enemy_killed >= enemy_count){
			enemy_count += 2;
			enemy_killed = 0;
			lvl++;
			c.createEnemy(enemy_count);
		}
		if(health <= 0 && State == STATE.GAME)
			State = STATE.SCORE;
	}
	
	private void render(){
		BufferStrategy bs = getBufferStrategy(); //
		if(bs == null){
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		//////////////////////////////
		
		g.drawImage(background,  0,  0,  getWidth(), getHeight(), this);
		
		if(State == STATE.GAME){
			p.render(g);
			c.render(g);
			
			g.setColor(Color.gray);
			g.fillRect(5,  5,  max_health * HCONST, 50);
			g.setColor(Color.green);
			g.fillRect(5,  5, health * HCONST, 50);
			
			Font fnt0 = new Font("arial", Font.BOLD, 30);
			g.setFont(fnt0);
			g.setColor(Color.white);
			g.drawString("Score: " + Integer.toString(total_killed), space +  max_health * HCONST, delay_text);
			
			g.drawString("Level: " + Integer.toString(lvl), score_text_space + space +  max_health * HCONST, delay_text);
		}
		else if (State == STATE.MENU)
			menu.render(g);
		else if (State == STATE.OPTIONS)
			options.render(g);
		else if (State == STATE.HELP)
			help.render(g);
		else if (State == STATE.SCORE)
			score.render(g);
		
		//////////////////////////////
		g.dispose();
		bs.show();
	}
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_RIGHT){
			rightPressed = true;
			p.setVelX(p.getSpeed());
		}
		if(key == KeyEvent.VK_LEFT){
			leftPressed = true;
			p.setVelX(-p.getSpeed());
		}
		if(key == KeyEvent.VK_DOWN){
			downPressed = true;
			p.setVelY(p.getSpeed());
		}
		if(key == KeyEvent.VK_UP){
			upPressed = true;
			p.setVelY(-p.getSpeed());
		}
		if(key == KeyEvent.VK_SPACE && !spacePressed){
			c.addEntity(new Bullet(p.getX(), p.getY(), 32, 32, bullet_speed, tex, this));
			spacePressed = true;
		}
	}
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_RIGHT){
			rightPressed = false;
			if(!leftPressed)
				p.setVelX(0);
			else
				p.setVelX(-p.getSpeed());
		}
		if(key == KeyEvent.VK_LEFT){
			leftPressed = false;
			if(!rightPressed)
				p.setVelX(0);
			else
				p.setVelX(p.getSpeed());
		}
		if(key == KeyEvent.VK_DOWN){
			downPressed = false;
			if(!upPressed)
				p.setVelY(0);
			else
				p.setVelY(-p.getSpeed());
		}
		if(key == KeyEvent.VK_UP){
			upPressed = false;
			if(!downPressed)
				p.setVelY(0);
			else
				p.setVelY(p.getSpeed());
		}
		if(key == KeyEvent.VK_SPACE){
			spacePressed = false;
		}
	}
	
	
	public static void main(String args[]){
		Game game = new Game();
		
		game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		
		JFrame frame = new JFrame (game.TITLE);
		frame.add(game);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		game.start();
	}
	
	public BufferedImage getSpriteSheet(){
		return spriteSheet;
	}
	
	
	public int getEnemy_count() {
		return enemy_count;
	}

	public void setEnemy_count(int enemy_count) {
		this.enemy_count = enemy_count;
	}

	public int getEnemy_killed() {
		return enemy_killed;
	}

	public void setEnemy_killed(int enemy_killed) {
		this.enemy_killed = enemy_killed;
	}

	public void setState(STATE State) {
		this.State = State;
	}
	
	public STATE getState() {
		return State;
	}
	
	public Menu getMenu(){
		return menu;
	}
	
	public Help getHelp(){
		return help;
	}
	
	public Options getOptions() {
		return options;
	}

	public void setOptions(Options options) {
		this.options = options;
	}
	
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getMax_health() {
		return max_health;
	}

	public void setMax_health(int max_health) {
		this.max_health = max_health;
	}

	public int getTotal_killed() {
		return total_killed;
	}

	public void setTotal_killed(int total_killed) {
		this.total_killed = total_killed;
	}

	public Score getScore() {
		return score;
	}

	public void setScore(Score score) {
		this.score = score;
	}
}