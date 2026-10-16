package puppy.code;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;

public class Block implements Collidable{
	private int x;
	private int y;
	private int width;
	private int height;
	private Color cc;
	private boolean destroyed;
	private int resistencia;

    
	public Block(int x, int y, int width, int height) {
	    this(x, y, width, height, 1);
	}
	
    public Block(int x, int y, int width, int height, int resistencia) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        
        Random r = new Random(x+y);        
        cc = new Color(0.1f+r.nextFloat(), r.nextFloat(), r.nextFloat(), 1f);
        this.resistencia = Math.max(1, resistencia);
  
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public int getResistencia() {
        return resistencia;
    }

    public boolean isDestroyed() {
        return destroyed;
    }
    
    public void destroy() {
        destroyed = true;
    }
    
    public int getPuntos() {
        return 1;
    }
    
    public void draw(ShapeRenderer shape){
    	shape.setColor(cc);
        shape.rect(x, y, width, height);
    }
    
    @Override
    public boolean collidesWith(int ballX, int ballY, int radius) {

        if (destroyed) {
            return false;
        }

        boolean intersectaX =
            x + width >= ballX - radius &&
            x <= ballX + radius;

        boolean intersectaY =
            y + height >= ballY - radius &&
            y <= ballY + radius;

        return intersectaX && intersectaY;
    }
    
    public boolean recibirGolpe() {

        if (destroyed) {
            return false;
        }

        resistencia--;

        if (resistencia <= 0) {
            destroy();
        }

        return true;
    }
}
