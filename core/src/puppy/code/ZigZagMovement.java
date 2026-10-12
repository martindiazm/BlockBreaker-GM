package puppy.code;

public class ZigZagMovement implements MovementStrategy {

    private int contador = 0;
    private int direccion = 1;

    @Override
    public void move(PingBall ball) {

        contador++;

        if (contador % 12 == 0) {
            direccion = -direccion;
        }

        //Este movimiento incorpora una pequeña oscilación horizontal
        ball.moveBy(ball.getXSpeed() + (2 * direccion), ball.getYSpeed());
    }
}