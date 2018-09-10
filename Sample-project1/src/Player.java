import org.newdawn.slick.Input;

public class Player extends Sprite {
	public final static String PLAYER_SPRITE_PATH = "res/spaceship.png";
	public final static int PLAYER_INITIAL_X = 512;
	public final static int PLAYER_INITIAL_Y = 688;
	private int nLives = 3;
	private static int strikes = 0;
	public final float SPEED = 0.5f;
	private int score = 0;
	private static Player player;
	private boolean shieldExists = false;
	
	public Player() {
		super(PLAYER_SPRITE_PATH, PLAYER_INITIAL_X, PLAYER_INITIAL_Y);
		player = this;
	}
	public static Player getInstance() {
		if (player == null) {
			player = new Player();
		}
		return player;
	}
	
	@Override
	public void update(Input input, int delta) {
		doMovement(input, delta);
		// do shooting
		if (input.isKeyPressed(Input.KEY_SPACE)) {
			World.getInstance().addSprite(new LaserShot(getX(), getY()));
		}
	}
		
	private void doMovement(Input input, int delta) {
		// handle horizontal movement
		float dx = 0;
		if (input.isKeyDown(Input.KEY_LEFT)) {
			dx -= SPEED;
		}
		if (input.isKeyDown(Input.KEY_RIGHT)) {
			dx += SPEED;
		}

		// handle vertical movement
		float dy = 0;
		if (input.isKeyDown(Input.KEY_UP)) {
			dy -= SPEED;
		}
		if (input.isKeyDown(Input.KEY_DOWN)) {
			dy += SPEED;
		}
		if (input.isKeyDown(Input.KEY_ESCAPE)) {
			System.exit(0);
		}
		
		move(dx * delta, dy * delta);
		clampToScreen();
	}
	@Override
	public void contactSprite(Sprite other) {
		// Check if the enemy made contact with the player
		// and if so, end the game
		if (other instanceof Enemy) {
			deactivate();
			other.deactivate();
		}
		
		if (other instanceof EnemyLaserShot) {
			deactivate();
			other.deactivate();
		}
	}
	@Override
	public void deactivate() {
		if (!shieldExists) {
			strikes++;
			World.getInstance().addSprite(new Shield(getX(), getY()));				
			World.getInstance().takeLife();
			shieldExists = true;
		}
		if (strikes==3) {
			super.deactivate();
		}
	}
	public int getScore() { return score;}
	public static int getLives() { return 3-strikes;}
	public void increaseScore(int points) {
		score += points;
	}
	public void loseShield() {
		shieldExists = false;
	}
}
