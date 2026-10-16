package puppy.code;

import java.util.ArrayList;

public class ChallengeLevel extends GameLevel {

	@Override
	protected void generarBloques(
	        ArrayList<Block> blocks,
	        int nivel,
	        int anchoPantalla,
	        int altoPantalla) {

	    LevelBuilder builder = new LevelBuilder(anchoPantalla, altoPantalla);

	    ArrayList<Block> nuevosBloques = builder
	        .filas(2 + nivel)
	        .tamanoBloques(70, 26)
	        .separacion(10)
	        .alternados(false)
	        .resistentes(true)
	        .especiales(true)
	        .build();

	    blocks.addAll(nuevosBloques);
	}
}