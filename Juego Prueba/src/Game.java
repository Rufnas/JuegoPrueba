import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game extends Canvas implements Runnable {
	public static final int WIDTH = 640;
	public static final int HEIGHT = WIDTH / 12*9;
	private Thread thread;
	private boolean running = false;
	private Handler handler;
	private Random r;
	private HUD hud;
	private Spawn spawner;

	public Game(){
		handler = new Handler();
		
		this.addKeyListener(new KeyInput(handler));
		
		new Window(WIDTH, HEIGHT, "JUEGO DE PRUEBA", this);
		hud=new HUD();
		spawner=new Spawn(handler,hud);
		r = new Random();
		handler.addObject(new Player(WIDTH/2-32, HEIGHT/2-32, ID.Player, handler));
		handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH-50), r.nextInt(Game.HEIGHT-50), ID.BasicEnemy));
	}

	public synchronized void start(){
		thread = new Thread(this);
		thread.start();
		running = true;
	}

	public synchronized void stop(){
		try {
			thread.join();
			running = false;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run(){
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1){
				tick();
				delta--;
			}
			if(running)
				render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames=0;
			}
		}
		stop();
	}

	private void tick() {
		// TODO Auto-generated method stub
		handler.tick();
		hud.tick();
		spawner.tick();
	}
	
	private void render() {
		// TODO Auto-generated method stub
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		handler.render(g);
		
		hud.render(g);
		
		g.dispose();
		bs.show();
	}
	
	public static float clamp(float var, float min, float max){
		if(var>=max) return var=max;
		else if(var<=min) return var=min;
		else return var;
	}

	public static void main(String arg[]){
		new Game();
	}
}
