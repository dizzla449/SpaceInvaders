
public class Life extends Sprite{
	
	private int lives = 3;
	
	public Life(float x, float y) {
		super(Player.PLAYER_SPRITE_PATH, x, y);
		lives = 3;
	}
	
	public int getLives() {
		return lives;
	}
	public void lifeLost() {
		
	}
}
