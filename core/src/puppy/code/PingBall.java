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
	    
	    private int anteriorX;
	    private int anteriorY;
	    
	    public PingBall(int x, int y, int size, int xSpeed, int ySpeed, boolean iniciaQuieto) {
	        super(x,y);
	        anteriorX = x;
	        anteriorY = y;
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

	        anteriorX = getX();
	        anteriorY = getY();
	        
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

	    public boolean checkCollision(Block block) {

	        if (block.isDestroyed()) {
	            return false;
	        }

	        // Limites del bloque
	        int izquierda = block.getX();
	        int derecha = izquierda + block.getWidth();
	        int abajo = block.getY();
	        int arriba = abajo + block.getHeight();

	        // Punto del bloque mas cercano al centro de la pelota
	        int cercanoX = Math.max(
	            izquierda, Math.min(getX(), derecha)
	        );

	        int cercanoY = Math.max(
	            abajo, Math.min(getY(), arriba)
	        );

	        int diferenciaX = getX() - cercanoX;
	        int diferenciaY = getY() - cercanoY;

	        // Detectar contacto entre circulo y rectangulo
	        boolean colision =
	            diferenciaX * diferenciaX +
	            diferenciaY * diferenciaY <= size * size;

	        if (!colision) {
	            return false;
	        }

	        // Registrar el impacto
	        if (!block.recibirGolpe()) {
	            return false;
	        }

	        // Calcular desde que direccion venia la pelota
	        if (anteriorY - size >= arriba) {

	            // Impacto desde arriba
	            setPosition(getX(), arriba + size);
	            ySpeed = Math.abs(ySpeed);

	        } else if (anteriorY + size <= abajo) {

	            // Impacto desde abajo
	            setPosition(getX(), abajo - size);
	            ySpeed = -Math.abs(ySpeed);

	        } else if (anteriorX + size <= izquierda) {

	            // Impacto desde la izquierda
	            setPosition(izquierda - size, getY());
	            xSpeed = -Math.abs(xSpeed);

	        } else if (anteriorX - size >= derecha) {

	            // Impacto desde la derecha
	            setPosition(derecha + size, getY());
	            xSpeed = Math.abs(xSpeed);

	        } else {

	            // Contacto en esquina o superposicion
	            // Resolver segun la menor penetracion
	            int penetracionX = size - Math.abs(diferenciaX);
	            int penetracionY = size - Math.abs(diferenciaY);

	            if (penetracionX < penetracionY) {
	                xSpeed = -xSpeed;
	            } else {
	                rebotarVerticalmente();
	            }
	        }

	        return true;
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
