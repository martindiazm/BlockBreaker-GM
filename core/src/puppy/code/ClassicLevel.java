package puppy.code;

import java.util.ArrayList;

public class ClassicLevel extends GameLevel {

    @Override
    protected void generarBloques(
            ArrayList<Block> blocks,
            int nivel,
            int anchoPantalla,
            int altoPantalla) {

        int filas = 2 + nivel;

        int blockWidth = 70;
        int blockHeight = 26;
        int separacion = 10;

        int y = altoPantalla;

        for (int fila = 0; fila < filas; fila++) {

            y -= blockHeight + separacion;

            for (int x = 5;
                 x + blockWidth <= anchoPantalla;
                 x += blockWidth + separacion) {

                blocks.add(new Block(x, y, blockWidth, blockHeight));
            }
        }
    }
}