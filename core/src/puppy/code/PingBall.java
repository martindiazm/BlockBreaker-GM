package puppy.code;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/** Pelota: colisiones de limites y delegacion de movimiento a Strategy. */
public class PingBall extends GameObject {

	    private int size;
	    private int xSpeed;
	    private int ySpeed;
	    private Color color = Color.WHITE;
	    private boolean estaQuieto;
	    
	    public PingBall(int x, int y, int size, int xSpeed, int ySpeed, boolean iniciaQuieto) {
	        super(x,y);
	        this.size = size;
	        this.xSpeed = xSpeed;
	        this.ySpeed = ySpeed;
	        estaQuieto = iniciaQuieto;
	    }
	    
	    public boolean estaQuieto() {
	    	return estaQuieto;
	    }
	    public void setEstaQuieto(boolean bb) {
	    	estaQuieto=bb;
	    }
	    public void setXY(int x, int y) {
	        setPosition(x, y);
	    }

	    @Override
	    public void draw(ShapeRenderer shape) {
	        shape.setColor(color);
	        shape.circle(getX(), getY(), size);
	    }

	    public void update() {
	        if (estaQuieto) {
	            return;
	        }

	        setPosition(
	            getX() + xSpeed,
	            getY() + ySpeed
	        );

	        if (getX() - size < 0 ||
	            getX() + size > Gdx.graphics.getWidth()) {
	            xSpeed = -xSpeed;
	        }

	        if (getY() + size > Gdx.graphics.getHeight()) {
	            ySpeed = -ySpeed;
	        }
	    }	
	    
	    public void checkCollision(Paddle paddle) {

	        if (paddle.collidesWith(getX(), getY(), size)) {
	            color = Color.GREEN;
	            ySpeed = -ySpeed;
	        } else {
	            color = Color.WHITE;
	        }
	    }

	    public void checkCollision(Block block) {

	        if (block.collidesWith(getX(), getY(), size)) {
	            ySpeed = -ySpeed;
	            block.destroyed = true;
	        }
	    }
	    
	}
