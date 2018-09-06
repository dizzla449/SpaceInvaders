import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class World {
	public static final String BACKGROUND_PATH = "res/space.png";
	
	private static final float ENEMY_X_OFFSET = 64;
	private static final float ENEMY_Y_OFFSET = 32;
	private static final float ENEMY_X_SEP = 128;
	private static final float BACKGROUND_SCROLL_SPEED = 0.2f;
	
	private float backgroundOffset = 0;
	private Image background;
	
	private static World world;
	public static World getInstance() {
		if (world == null) {
			world = new World();
		}
		return world;
	}
	
	private ArrayList<Sprite> sprites = new ArrayList<>();
	
	public void addSprite(Sprite sprite) {
		sprites.add(sprite);
	}
	
	public World() {
		try {
			background = new Image(BACKGROUND_PATH);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		// Create sprites
		for (int i = 0; i < App.SCREEN_WIDTH; i += ENEMY_X_SEP) {
			sprites.add(new Enemy(ENEMY_X_OFFSET + i, ENEMY_Y_OFFSET));
		}
		sprites.add(new Player());
		
		world = this;
	}
	
	public void update(Input input, int delta) {
		// Update all sprites
		for (int i = 0; i < sprites.size(); ++i) {
			sprites.get(i).update(input, delta);
		}
		// Handle collisions
		for (Sprite sprite : sprites) {
			for (Sprite other : sprites) {
				if (sprite != other && sprite.getBoundingBox().intersects(other.getBoundingBox())) {
					sprite.contactSprite(other);
				}
			}
		}
		// Clean up inactive sprites
		for (int i = 0; i < sprites.size(); ++i) {
			if (sprites.get(i).getActive() == false) {
				sprites.remove(i);
				// decrement counter to make sure we don't miss any
				--i;
			}
		}
		
		backgroundOffset += BACKGROUND_SCROLL_SPEED * delta;
		backgroundOffset = backgroundOffset % background.getHeight();
	}
	
	public void render() {
		// Tile the background image
		for (int i = 0; i < App.SCREEN_WIDTH; i += background.getWidth()) {
			for (int j = -background.getHeight() + (int)backgroundOffset; j < App.SCREEN_HEIGHT; j += background.getHeight()) {
				background.draw(i, j);
			}
		}
		// Draw all sprites
		for (Sprite sprite : sprites) {
			sprite.render();
		}		
	}
}
