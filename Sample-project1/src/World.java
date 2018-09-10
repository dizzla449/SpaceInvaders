import java.util.ArrayList;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class World {
	public static final String BACKGROUND_PATH = "res/space.png";
	
	private static final float ENEMY_Y_OFFSET = -64;
	private static final float BACKGROUND_SCROLL_SPEED = 0.2f;
	private static final int SPEED_UP_RATIO = 5;
	
	private float backgroundOffset = 0;
	private Image background;
	
	private static World world;
	private ArrayList<Sprite> sprites = new ArrayList<>();
	private ArrayList<Sprite> lives = new ArrayList<>();
	private int effectiveDelta;
	
	public static World getInstance() {
		if (world == null) {
			world = new World();
		}
		return world;
	}
	
	public void addSprite(Sprite sprite) {
		sprites.add(sprite);
	}
	public void takeLife() {
		lives.remove(lives.size()-1);
	}
	
	public World() {
		try {
			background = new Image(BACKGROUND_PATH);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		// Create sprites
		sprites.add(new Player());
		spwanEnemies();
		for (int i=0; i<3; i++) {
			addSprite(new Life(20+i*40, 696f));
		}
		
		world = this;
	}
	
	public void update(Input input, int delta) {
		// Update all sprites
		effectiveDelta = delta*(input.isKeyPressed(Input.KEY_S) ? SPEED_UP_RATIO: 1);
		for (int i = 0; i < sprites.size(); ++i) {
			sprites.get(i).update(input, effectiveDelta);
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
		for (Sprite i: lives) {
			i.render();
		}
	}
	
	private void spwanEnemies() {
		try (BufferedReader br = new BufferedReader(new FileReader("res/waves.txt"))) {
			String line = null;
			while ((line = br.readLine()) != null) {
				if ((line.charAt(0) != '#')) {
					String[] data = line.split(",");
					String className = data[0];
					int x = Integer.parseInt(data[1]);
					int delay = Integer.parseInt(data[2]);
					Class<?> c = Class.forName(className);
					addSprite((Sprite)c.getConstructor(new Class[] {float.class, float.class, int.class})
							.newInstance(new Object[] {x, ENEMY_Y_OFFSET, delay}));
					
				}					
			}
	} catch (Exception e) {
			e.printStackTrace();
	}
	}
}
