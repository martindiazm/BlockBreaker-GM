package puppy.code;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/** Clase base de los objetos que tienen posicion, movimiento y representacion. */
public abstract class GameObject {

	private int x;
	private int y;
	
	protected GameObject(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	protected void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

	public abstract void draw(ShapeRenderer shape);
	
}
