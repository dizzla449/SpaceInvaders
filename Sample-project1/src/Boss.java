
public class Boss extends Enemy{
	private static final float TARGET_Y = 72f;
	private static final String BOSS_IMAGE_PATH = "res/boss.png";
	private static final float SPEED1 = 0.2f;
	private static final float SPEED2 = 0.1f;
	private static final int score = 5000;
	private static int strikes = 0;
	private int[] offset = new int[] {-97, -74, 74, 97};
	int timeElasped = 0;
	int timeElasped2nd = 0;
	int localTime = 0;
	private boolean setRandomX = true;
	private boolean setRandomX2 = true;
	private float firstXTarget = 0f;
	private float secondXTarget = 0f;
	private float dx=0;
	private float dx2 = 0;
	private boolean completedThirdMove = false;
	private boolean completedSecondMove = false;
	
	public Boss(float x, float y_offset, int delay) {
		super(BOSS_IMAGE_PATH, x, y_offset, delay);
	}
	
	@Override
	public void enemyBehaviour(int delta) {
			
			if (getTime() >getDelay()) {
				if (getY() != TARGET_Y) {
					move(0, 0.05f);
				} else { timeElasped += delta;}
				if (timeElasped >5000) {
					if (setRandomX) {
						firstXTarget = (float) (Math.random()*768+128);
						dx = getX() < firstXTarget ? SPEED1 : -SPEED1;
						setRandomX = false;
					}
					if (!completedSecondMove) {
						move(dx, 0);
						if (Math.abs(getX()-firstXTarget)<0.3f) { completedSecondMove = true;}
					} else { timeElasped2nd+= delta;}
					if (timeElasped2nd > 2000) {
						if (setRandomX2) {
							secondXTarget = (float)(Math.random()*768+128);
							dx2 = getX() < secondXTarget ? SPEED2 : -SPEED2;
							setRandomX2 = false;
						}
						localTime += delta;
						if (!completedThirdMove) {
							move(dx2, 0);
							if (localTime>200) { shoot(); localTime-=200;}
						if (Math.abs(getX()-secondXTarget)<0.2f) { completedThirdMove = true;}
						} else {
							if (timeElasped2nd < 3000 && localTime>200) {
								shoot();
							} else {variableReset();}
						}
					}
				}
			}
	}
	private void shoot() {
		for(int i=0; i<4; i++) {
			World.getInstance().addSprite(new EnemyLaserShot(getX()+offset[i], getY()));	
		}
	}
	private void variableReset() {
		timeElasped = 0;
		timeElasped2nd = 0;
		localTime = 0;
		completedSecondMove = false;
		completedThirdMove = false;
		setRandomX = true;
		setRandomX2 = true;
	}
	@Override
	public void contactSprite(Sprite other) {
		// Check if the enemy made contact with the player
		// and if so, end the game
		if (other instanceof Player) {
			other.deactivate();
		}
		
		if (other instanceof LaserShot) {
			deactivate();
			other.deactivate();
		}
	}
	@Override
	public void deactivate() {
		strikes++;
		if (strikes==60) {
			super.deactivate();
		}
	}
	@Override
	public int getScore() {
		return score;
	}
}