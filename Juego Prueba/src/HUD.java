import java.awt.Color;
import java.awt.Graphics;

public class HUD {
	public static int HEALTH=100;
	private int greenValue=255;
	
	private int score=0, level=1;
	
	public void tick(){
		HEALTH=(int) Game.clamp(HEALTH, 0, 100);
		greenValue=(int) Game.clamp(greenValue, 0, 255);
		greenValue=HEALTH*2;
		
		score++;
	}
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void render(Graphics g){
		g.setColor(Color.GRAY);
		g.fillRect(15, 15, 200, 32);
		g.setColor(new Color(75, greenValue, 0));
		g.fillRect(15, 15, HEALTH*2, 32);
		g.setColor(Color.WHITE);
		g.drawRect(15, 15, 200, 32);
		
		g.drawString("Puntos: "+score, 15, 64);
		g.drawString("Nivel: "+level, 15, 80);
	}
}
