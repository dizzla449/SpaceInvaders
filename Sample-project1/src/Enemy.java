public class Enemy extends Sprite {
	public final static String ENEMY_SPRITE_PATH = "res/basic-enemy.png";
	
	public Enemy(float x, float y) {
		super(ENEMY_SPRITE_PATH, x, y);
	}

	@Override
	public void contactSprite(Sprite other) {
		// Check if the enemy made contact with the player
		// and if so, end the game
		if (other instanceof Player) {
			System.exit(0);
		}
		
		if (other instanceof LaserShot) {
			deactivate();
			other.deactivate();
		}
	}
}
