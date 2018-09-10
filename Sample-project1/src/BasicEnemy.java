
public class BasicEnemy extends Enemy {
	private final static float SPEED = 0.2f;
	private static final String BASIC_ENEMY_PATH = "res/basic-enemy.png";
	private static final int score = 50;
	
	public BasicEnemy(float x, float y_offset, int delay) {
		super(BASIC_ENEMY_PATH, x, y_offset, delay);
	}
	
	@Override
	public void enemyBehaviour(int delta) {
		move(0f, SPEED*delta);
	}
	public int getScore() {
		return score;
	}
}
