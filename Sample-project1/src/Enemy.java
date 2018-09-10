import org.newdawn.slick.Input;

public class Enemy extends Sprite {
	public final static String ENEMY_SPRITE_PATH = "res/basic-enemy.png";
	private int delay;
	private int t=0;
	
	public Enemy(String path, float x, float y, int delay) {
		super(ENEMY_SPRITE_PATH, x, y);
		this.delay = delay;
	}
	
	public void update(Input input, int delta) {
		t += delta;
		if (delay>t) {
			enemyBehaviour(delta);
		}
	}
	
	public void enemyBehaviour(int delta) {
	}
	
	@Override
	public void contactSprite(Sprite other) {
		// Check if the enemy made contact with the player
		// and if so, end the game
		if (other instanceof Player) {
			other.deactivate();
		}
		
		if (other instanceof LaserShot) {
			Player.getInstance().increaseScore(getScore());
			deactivate();
			other.deactivate();
		}
	}
	public int getDelay() { return delay;}
	public int getTime() { return t;}
	@Override
	public void deactivate() {
		super.deactivate();
	}
	public int getScore() {
		return 0;
	}
}
