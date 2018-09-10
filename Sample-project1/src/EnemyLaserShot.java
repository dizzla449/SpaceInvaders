import org.newdawn.slick.Input;

public class EnemyLaserShot extends Sprite{
	private static final String LASER_SHOT_PATH = "res/shot.png";
	private static final float SPEED = 07f;
	
	public EnemyLaserShot(float x, float y) {
		super(LASER_SHOT_PATH, x , y);
	}
	
	public void update(Input input, int delta) {
		move(0, SPEED*delta);
		if (!onScreen()) {
			deactivate();
		}
	}
	@Override
	public void contactSprite(Sprite other) {
		if (other instanceof Player) {
			other.deactivate();
			deactivate();
		}
		if (other instanceof LaserShot) {
			deactivate();
			other.deactivate();
		}
	}
}
