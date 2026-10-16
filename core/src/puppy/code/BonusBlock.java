package puppy.code;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class BonusBlock extends Block {

    public BonusBlock(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void draw(ShapeRenderer shape) {

        int x = getX();
        int y = getY();
        int width = getWidth();
        int height = getHeight();

        // Borde blanco
        shape.setColor(Color.WHITE);
        shape.rect(x, y, width, height);

        // Interior dorado
        shape.setColor(Color.GOLD);
        shape.rect(x + 3, y + 3, width - 6, height - 6);

        // Estrella blanca 
        float cx = x + width / 2f;
        float cy = y + height / 2f;

        shape.setColor(Color.WHITE);

        shape.triangle(
            cx, cy + 10,
            cx - 5, cy,
            cx + 5, cy
        );

        shape.triangle(
            cx, cy - 10,
            cx - 5, cy,
            cx + 5, cy
        );

        shape.triangle(
            cx - 10, cy,
            cx, cy + 5,
            cx, cy - 5
        );

        shape.triangle(
            cx + 10, cy,
            cx, cy + 5,
            cx, cy - 5
        );
    }

    @Override
    public int getPuntos() {
        return 3;
    }
}