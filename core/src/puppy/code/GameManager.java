package puppy.code;

public class GameManager {

    // Unica instancia de GameManager
    private static final GameManager instancia = new GameManager();

    private int vidas;
    private int puntaje;
    private int nivel;
    private boolean gameOver;

    // Constructor privado: impide crear instancias externas
    private GameManager() {
        reiniciarPartida();
    }

    // Acceso a la unica instancia
    public static GameManager getInstance() {
        return instancia;
    }

    public int getVidas() {
        return vidas;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public int getNivel() {
        return nivel;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void sumarPuntos(int puntos) {
        if (!gameOver && puntos > 0) {
            puntaje += puntos;
        }
    }

    public void perderVida() {
        if (!gameOver && vidas > 0) {
            vidas--;

            if (vidas == 0) {
                gameOver = true;
            }
        }
    }

    public void siguienteNivel() {
        if (!gameOver) {
            nivel++;
        }
    }

    public void reiniciarPartida() {
        vidas = 3;
        puntaje = 0;
        nivel = 1;
        gameOver = false;
    }
}
