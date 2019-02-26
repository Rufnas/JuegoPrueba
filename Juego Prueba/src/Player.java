import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Player extends GameObject {
	private Handler handler;

	public Player(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		this.handler=handler;
		
	}
	
	public Rectangle getBounds(){
		return new Rectangle((int)x, (int)y, 32, 32);
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		x += velX;
		y += velY;
		
		x=Game.clamp(x, 0, Game.WIDTH-37);
		y=Game.clamp(y, 0, Game.HEIGHT-60);
		
		collision();
	}

	private void collision() {
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId()==ID.BasicEnemy || tempObject.getId()==ID.FastEnemy || tempObject.getId() == ID.SmartEnemy || tempObject.getId() == ID.EnemyBoss	){
				if(getBounds().intersects(tempObject.getBounds())){
					//Cuando colisionan con el jugador...
					HUD.HEALTH-=2;
				}
			}
			
		}
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.WHITE);
		g.fillRect((int)x, (int)y, 32, 32);
	}
	
	

}
