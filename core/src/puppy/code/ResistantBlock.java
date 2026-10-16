package puppy.code;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class ResistantBlock extends Block {

    public ResistantBlock(int x, int y, int width, int height) {
        super(x, y, width, height, 2);
    }

    @Override
    public void draw(ShapeRenderer shape) {

        int x = getX();
        int y = getY();
        int w = getWidth();
        int h = getHeight();

        // Borde exterior oscuro
        shape.setColor(Color.DARK_GRAY);
        shape.rect(x, y, w, h);

        if (getResistencia() == 2) {
            // Bloque intacto
        	shape.setColor(new Color(0.05f, 0.35f, 0.75f, 1f));
        } else {
            // Bloque dañado
        	shape.setColor(new Color(0.85f, 0.30f, 0.05f, 1f));
        }

        shape.rect(x + 3, y + 3, w - 6, h - 6);

        // Lineas que representan la resistencia restante
        shape.setColor(Color.WHITE);

        if (getResistencia() == 2) {
            shape.rect(x + 12, y + 8, w - 24, 3);
            shape.rect(x + 12, y + 15, w - 24, 3);
        } else {
            shape.rect(x + 12, y + 11, w - 24, 3);
        }
    }

    @Override
    public int getPuntos() {
        return 2;
    }
}
