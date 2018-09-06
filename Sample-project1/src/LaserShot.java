import org.newdawn.slick.Input;

public class LaserShot extends Sprite {
	public final static String SHOT_SPRITE_PATH = "res/shot.png";
	public final float SHOT_SPEED = -.3f;
	
	public LaserShot(float x, float y) {
		super(SHOT_SPRITE_PATH, x, y);
	}
	
	@Override
	public void update(Input input, int delta) {
		move(0, SHOT_SPEED);
		if (!onScreen()) {
			deactivate();
		}
	}
}
