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
    
    public Block(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        destroyed = false;
        Random r = new Random(x+y);
        
        cc = new Color(0.1f+r.nextFloat(), r.nextFloat(), r.nextFloat(), 1f);
  
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

    public boolean isDestroyed() {
        return destroyed;
    }
    
    public void destroy() {
        destroyed = true;
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
}
