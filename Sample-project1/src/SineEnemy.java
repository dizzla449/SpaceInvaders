
public class SineEnemy extends Enemy{
	
	private float offset;
	private static final String SINE_ENEMY_PATH = "res/sine-enemy.png";
	private static final float SPEED = 0.15f;
	private final static float FACTOR = (float) Math.PI;
	private static final float AMPLITUDE = 96f;
	private final float initialX;
	private static final int score = 100;

	
	public SineEnemy(float x, float y_offset, int delay) {
		super(SINE_ENEMY_PATH, x, y_offset, delay);
		initialX = x;
	}
	
	@Override
	public void enemyBehaviour(int delta) {
		offset = (float) (AMPLITUDE*Math.sin(FACTOR*(getTime()-getDelay())));
		setX(initialX+offset);
		//move(offset, SPEED);
		move(0f, SPEED);
	}
	
	@Override
	public int getScore() {
		return score;
	}
}
