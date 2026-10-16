package puppy.code;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class LevelBuilder {

    private int anchoPantalla;
    private int altoPantalla;

    private int filas = 3;
    private int anchoBloque = 70;
    private int altoBloque = 26;
    private int separacion = 10;

    private boolean alternados = false;
    private boolean especiales = false;
    private boolean resistentes = false;

    public LevelBuilder(int anchoPantalla, int altoPantalla) {
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;
    }

    public LevelBuilder filas(int cantidad) {
        this.filas = Math.max(1, Math.min(cantidad, 7));
        return this;
    }

    public LevelBuilder tamanoBloques(int ancho, int alto) {
        if (ancho > 0 && alto > 0) {
            this.anchoBloque = ancho;
            this.altoBloque = alto;
        }
        return this;
    }

    public LevelBuilder separacion(int valor) {
        this.separacion = Math.max(0, valor);
        return this;
    }

    public LevelBuilder alternados(boolean valor) {
        this.alternados = valor;
        return this;
    }

    public LevelBuilder especiales(boolean valor) {
        this.especiales = valor;
        return this;
    }
    
    public LevelBuilder resistentes(boolean valor) {
        this.resistentes = valor;
        return this;
    }

    // Construye el conjunto de bloques
    public ArrayList<Block> build() {

        ArrayList<Block> bloques = new ArrayList<Block>();
        Random random = new Random();

        int y = altoPantalla;

        for (int fila = 0; fila < filas; fila++) {

            y -= altoBloque + separacion;
            int columna = 0;

            for (int x = 5;
                 x + anchoBloque <= anchoPantalla;
                 x += anchoBloque + separacion) {

                boolean agregar = true;

                if (alternados && (fila + columna) % 3 == 0) {
                    agregar = false;
                }

                if (agregar) {

                    Block nuevoBloque;

                    // Algunos bloques requieren dos golpes
                    if (resistentes && (fila + columna) % 3 == 0) {

                        nuevoBloque = new ResistantBlock(
                            x, y, anchoBloque, altoBloque
                        );

                    } else {

                        nuevoBloque = new Block(x, y, anchoBloque, altoBloque);
                    }

                    bloques.add(nuevoBloque);
                }

                columna++;
            }
        }

        // Distribuye los BonusBlock en posiciones aleatorias
        if (especiales && !bloques.isEmpty()) {

            ArrayList<Integer> posiciones =
                new ArrayList<Integer>();

            for (int i = 0; i < bloques.size(); i++) {
                posiciones.add(i);
            }

            Collections.shuffle(posiciones, random);

            // Aproximadamente un bonus cada diez bloques
            int cantidadBonus = Math.max(1, bloques.size() / 10);

            for (int i = 0; i < cantidadBonus; i++) {

                int indice = posiciones.get(i);
                Block original = bloques.get(indice);

                bloques.set(indice, new BonusBlock(
                    original.getX(),
                    original.getY(),
                    original.getWidth(),
                    original.getHeight()
                ));
            }
        }

        return bloques;
    }
}