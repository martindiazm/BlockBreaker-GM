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
	    private MovementStrategy movementStrategy;
	    
	    public PingBall(int x, int y, int size, int xSpeed, int ySpeed, boolean iniciaQuieto) {
	        super(x,y);
	        this.size = size;
	        this.xSpeed = xSpeed;
	        this.ySpeed = ySpeed;
	        estaQuieto = iniciaQuieto;
	        this.movementStrategy = new NormalMovement();
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

	        // Delegar movimiento a la estrategia actual
	        movementStrategy.move(this);

	     // Rebote contra el borde izquierdo
	        if (getX() - size < 0) {
	            setPosition(size, getY());
	            xSpeed = Math.abs(xSpeed);
	        }

	        // Rebote contra el borde derecho
	        if (getX() + size > Gdx.graphics.getWidth()) {
	            setPosition(Gdx.graphics.getWidth() - size, getY());
	            xSpeed = -Math.abs(xSpeed);
	        }
	        

	        // Rebote contra el techo
	        if (getY() + size >= Gdx.graphics.getHeight()) {

	            // Colocar la pelota dentro del limite superior
	            setPosition(getX(), Gdx.graphics.getHeight() - size);

	            // Asegurar que la pelota se mueva hacia abajo
	            ySpeed = -Math.abs(ySpeed);
	        }
	    }
	    
	    private void rebotarVerticalmente() {
	        ySpeed = -ySpeed;
	    }
	    
	    public void checkCollision(Paddle paddle) {

	        // Solo comprobar el rebote si la pelota esta bajando
	        if (ySpeed < 0 &&
	            paddle.collidesWith(getX(), getY(), size)) {

	            // Colocar la pelota justo encima de la plataforma
	            setPosition(
	                getX(),
	                paddle.getY() + paddle.getHeight() + size
	            );

	            // Cambiar direccion hacia arriba
	            ySpeed = Math.abs(ySpeed);

	            color = Color.GREEN;

	        } else {
	            color = Color.WHITE;
	        }
	    }

	    public void checkCollision(Block block) {

	        if (block.isDestroyed()) {
	            return;
	        }

	        if (block.collidesWith(getX(), getY(), size)) {

	            // Destruir el bloque
	            block.destroy();

	            // Cambiar la direccion vertical
	            rebotarVerticalmente();
	        }
	    }
	    
	    public int getXSpeed() {
	        return xSpeed;
	    }

	    public int getYSpeed() {
	        return ySpeed;
	    }

	    public void moveBy(int dx, int dy) {
	        setPosition(getX() + dx, getY() + dy);
	    }

	    public void setMovementStrategy(MovementStrategy strategy) {
	        if (strategy != null) {
	            this.movementStrategy = strategy;
	        }
	    }
	    
	}
