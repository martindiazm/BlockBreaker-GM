package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Paddle extends GameObject implements Collidable{
    private int width = 100;
    private int height = 10;
    
    public Paddle(int x, int y, int ancho, int alto) {
    	super(x,y);
    	width = ancho;
    	height = alto;
    }
     
	public int getWidth() {return width;}
	public int getHeight() {return height;}
	
	public void centrar() {
	    int centroX = (Gdx.graphics.getWidth() - width) / 2;
	    setPosition(centroX, getY());
	}

	public void update() {
	    int nuevaX = getX();

	    if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
	        nuevaX -= 15;
	    }

	    if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
	        nuevaX += 15;
	    }

	    // Impedir que la plataforma salga por la izquierda
	    if (nuevaX < 0) {
	        nuevaX = 0;
	    }

	    // Impedir que salga por la derecha
	    if (nuevaX + width > Gdx.graphics.getWidth()) {
	        nuevaX = Gdx.graphics.getWidth() - width;
	    }
	    
	    setPosition(nuevaX, getY());
	}
	
	@Override
	public void draw(ShapeRenderer shape) {
	    shape.setColor(Color.BLUE);
	    shape.rect(getX(), getY(), width, height);
	}
    
	@Override
	public boolean collidesWith(int ballX, int ballY, int radius) {

	    boolean intersectaX =
	        getX() + width >= ballX - radius &&
	        getX() <= ballX + radius;

	    boolean intersectaY =
	        getY() + height >= ballY - radius &&
	        getY() <= ballY + radius;

	    return intersectaX && intersectaY;
	}
}
