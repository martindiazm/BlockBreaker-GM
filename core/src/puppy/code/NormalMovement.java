package puppy.code;

public class NormalMovement implements MovementStrategy {

    @Override
    public void move(PingBall ball) {

        ball.moveBy(ball.getXSpeed(), ball.getYSpeed());
    }
}