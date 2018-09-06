import org.newdawn.slick.Input;

public class Player extends Sprite {
	public final static String PLAYER_SPRITE_PATH = "res/spaceship.png";
	public final static int PLAYER_INITIAL_X = 512;
	public final static int PLAYER_INITIAL_Y = 688;
	
	public final float SPEED = 0.5f;
	
	public Player() {
		super(PLAYER_SPRITE_PATH, PLAYER_INITIAL_X, PLAYER_INITIAL_Y);
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

}
