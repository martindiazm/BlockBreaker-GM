package puppy.code;

/** Contrato para objetos capaces de detectar y resolver impactos de la pelota. */
public interface Collidable {
	boolean collidesWith(
	        int ballX,
	        int ballY,
	        int radius
	    );
}