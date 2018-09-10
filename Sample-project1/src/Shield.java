import org.newdawn.slick.Input;

public class Shield extends Sprite{
	
	
	private int t=0;
	private static final String SHIELD_IMAGE_PATH = "res/shield.png";
	
	public Shield(float x, float y) {
		super(SHIELD_IMAGE_PATH, x, y);
	}
	
	public void update(Input input, int delta) {
		t += delta;
		if (t<3000) {
			move(Player.getInstance().getX(), Player.getInstance().getY());
		} else {
			deactivate();
			Player.getInstance().loseShield();
		}
	}
}
