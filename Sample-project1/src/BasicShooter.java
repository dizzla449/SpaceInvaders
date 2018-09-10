
public class BasicShooter extends Enemy{
	
	private static final String BASI_SHOOTER_PATH = "res/basic-shooter.png";
	private final float Y_COORDINATE_STOP = (float) (Math.random()*416f+48);
	private final static float SPEED = 0.2f;
	private static final int SHOOT_DELAY = 3500;
	private int timeElasped = 0;
	private static final int score = 200;
	
	public BasicShooter(float x, float y_offset, int delay) {
		super(BASI_SHOOTER_PATH, x, y_offset, delay);
	}
	
	@Override
	public void enemyBehaviour(int delta) {
		if (getY()<Y_COORDINATE_STOP) {
			move(0, SPEED*delta);
		} else {
			timeElasped += delta;
			if (timeElasped>SHOOT_DELAY) {
				timeElasped -= SHOOT_DELAY;
				World.getInstance().addSprite(new EnemyLaserShot(getX(), getY()));				
			}
		}
	}
	@Override
	public int getScore() {
		return score;
	}
}
