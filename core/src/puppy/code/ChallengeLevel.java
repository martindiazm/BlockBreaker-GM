package puppy.code;

import java.util.ArrayList;

public class ChallengeLevel extends GameLevel {

    @Override
    protected void generarBloques(ArrayList<Block> blocks, int nivel, int anchoPantalla, int altoPantalla) 
    {

        int filas = 2 + nivel;

        int blockWidth = 70;
        int blockHeight = 26;
        int separacion = 10;

        int y = altoPantalla;

        for (int fila = 0; fila < filas; fila++) {

            y -= blockHeight + separacion;

            int columna = 0;

            for (int x = 5;
                 x + blockWidth <= anchoPantalla;
                 x += blockWidth + separacion) {

                // Distribucion alternada con espacios
                if ((fila + columna) % 3 != 0) {

                    blocks.add(new Block(x, y, blockWidth, blockHeight));
                }

                columna++;
            }
        }
    }
}