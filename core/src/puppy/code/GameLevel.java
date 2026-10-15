package puppy.code;

import java.util.ArrayList;

public abstract class GameLevel 
{

    // Template Method
    public final void prepararNivel(ArrayList<Block> blocks, int nivel, int anchoPantalla, int altoPantalla) 
    {
        // Limpia los bloques anteriores
        blocks.clear();

        // Genera los bloques segun el tipo de nivel
        generarBloques(blocks, nivel, anchoPantalla, altoPantalla);
    }

    protected abstract void generarBloques(ArrayList<Block> blocks, int nivel, int anchoPantalla, int altoPantalla);
    
    public final boolean estaCompletado(ArrayList<Block> blocks) 
    {
        return blocks.isEmpty();
    }
}