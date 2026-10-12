package puppy.code;

import java.util.ArrayList;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;


public class BlockBreakerGame extends ApplicationAdapter {
    private OrthographicCamera camera;
	private SpriteBatch batch;	   
	private BitmapFont font;
	private ShapeRenderer shape;
	private PingBall ball;
	private Paddle pad;
	private ArrayList<Block> blocks = new ArrayList<>();
	private GameManager gameManager;
	private Sound sonidoRomperBloque;
	private boolean movimientoZigZag = false;
    
		@Override
		public void create () {	
			camera = new OrthographicCamera();
		    camera.setToOrtho(false, 800, 480);
		    batch = new SpriteBatch();
		    font = new BitmapFont();
		    font.getData().setScale(3, 2);
		    gameManager = GameManager.getInstance();
		    gameManager.reiniciarPartida();

		    crearBloques(2 + gameManager.getNivel());
			
		    shape = new ShapeRenderer();
		    ball = new PingBall(Gdx.graphics.getWidth()/2-10, 41, 10, 5, 7, true);
		    aplicarEstrategiaActual();
		    pad = new Paddle(Gdx.graphics.getWidth()/2-50,40,100,10);

		    
		    sonidoRomperBloque = Gdx.audio.newSound(Gdx.files.internal("sounds/block_break.wav"));
		}
		public void crearBloques(int filas) {
			blocks.clear();
			int blockWidth = 70;
		    int blockHeight = 26;
		    int y = Gdx.graphics.getHeight();
		    for (int cont = 0; cont<filas; cont++ ) {
		    	y -= blockHeight+10;
		    	for (int x = 5; x < Gdx.graphics.getWidth(); x += blockWidth + 10) {
		            blocks.add(new Block(x, y, blockWidth, blockHeight));
		        }
		    }
		}
		public void dibujaTextos() {
			//actualizar matrices de la cámara
			camera.update();
			//actualizar 
			batch.setProjectionMatrix(camera.combined);
			batch.begin();
			//dibujar textos
			font.draw(batch, "Puntos: " + gameManager.getPuntaje(), 10, 25);
			font.draw(batch, "Vidas : " + gameManager.getVidas(), Gdx.graphics.getWidth()- 20, 25);
			font.draw(batch, "Nivel: " + gameManager.getNivel(), 350, 25);
			batch.end();
		}	
		
		@Override
		public void render () {
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); 		
			
			// Mostrar Game Over cuando no quedan vidas
		    if (gameManager.isGameOver()) {

		        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
		            reiniciarJuego();
		        } else {
		            dibujarGameOver();
		        }

		        return;
		    }
		    
			shape.begin(ShapeRenderer.ShapeType.Filled);
			pad.update();
			pad.draw(shape);
	        // monitorear inicio del juego
	        if (ball.estaQuieto()) {
	        	ball.setXY(pad.getX()+pad.getWidth()/2-5, pad.getY()+pad.getHeight()+11);
	        	if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) ball.setEstaQuieto(false);
	        }else ball.update();
	        //verificar si se fue la bola x abajo
	        if (ball.getY()<0) {
	        	gameManager.perderVida();
	        	//nivel = 1;
	        	ball = new PingBall(pad.getX()+pad.getWidth()/2-5, pad.getY()+pad.getHeight()+11, 10, 5, 7, true);
	        	aplicarEstrategiaActual();
	        }
	        
	        // Cambiar estrategia presionando M
	        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {

	            movimientoZigZag = !movimientoZigZag;

	            if (movimientoZigZag) {
	                ball.setMovementStrategy(new ZigZagMovement());
	            } else {
	                ball.setMovementStrategy(new NormalMovement());
	            }
	        }
	        
	        // Dibujar bloques y comprobar colisiones
	        if (!gameManager.isGameOver()) {
	            for (Block b : blocks) {
	                b.draw(shape);
	                ball.checkCollision(b);
	            }

	            // Eliminar bloques destruidos
	            for (int i = 0; i < blocks.size(); i++) {

	                Block b = blocks.get(i);

	                if (b.isDestroyed()) {

	                    gameManager.sumarPuntos(1);

	                    sonidoRomperBloque.play(0.5f);

	                    blocks.remove(i);
	                    i--;
	                }
	            }

	            // Comprobar colision con plataforma
	            ball.checkCollision(pad);

	            // Comprobar si se completo el nivel
	            if (blocks.isEmpty()) {

	                gameManager.siguienteNivel();

	                crearBloques(2 + gameManager.getNivel());

	                ball = new PingBall(
	                    pad.getX() + pad.getWidth() / 2 - 5,
	                    pad.getY() + pad.getHeight() + 11,
	                    10, 5, 7, true
	                );
	                aplicarEstrategiaActual();
	            }

	            ball.draw(shape);
	        }

	        shape.end();

	        // Dibujar informacion de la partida
	        if (gameManager.isGameOver()) {
	            dibujarGameOver();
	        } else {
	            dibujaTextos();
	        }
		}
	    
		private void dibujarGameOver() {

		    camera.update();
		    batch.setProjectionMatrix(camera.combined);

		    GlyphLayout layout = new GlyphLayout();

		    float anchoPantalla = camera.viewportWidth;
		    float altoPantalla = camera.viewportHeight;

		    batch.begin();

		    // Titulo GAME OVER
		    String titulo = "GAME OVER";
		    layout.setText(font, titulo);

		    font.draw(batch, titulo,
		        (anchoPantalla - layout.width) / 2,
		        altoPantalla / 2 + 80);

		    // Puntaje final
		    String textoPuntaje = "Puntaje: " + gameManager.getPuntaje();
		    layout.setText(font, textoPuntaje);

		    font.draw(batch, textoPuntaje,
		        (anchoPantalla - layout.width) / 2,
		        altoPantalla / 2 + 20);

		    // Instruccion para reiniciar
		    String instruccion = "Presiona R para reiniciar";
		    layout.setText(font, instruccion);

		    font.draw(batch, instruccion,
		        (anchoPantalla - layout.width) / 2,
		        altoPantalla / 2 - 40);

		    batch.end();
		}
		
		private void reiniciarJuego() {

		    // Reiniciar datos mediante Singleton
		    gameManager.reiniciarPartida();
		    
		    // Volver al movimiento normal
		    movimientoZigZag = false;

		    // Centrar la plataforma
		    pad.centrar();
		    
		    // Volver a crear los bloques del primer nivel
		    crearBloques(2 + gameManager.getNivel());
		    
		    // Reiniciar la pelota
		    ball = new PingBall(
		        pad.getX() + pad.getWidth() / 2 - 5,
		        pad.getY() + pad.getHeight() + 11,
		        10, 5, 7, true
		    );
		    
		    aplicarEstrategiaActual();
		}
		
		private void aplicarEstrategiaActual() {

		    if (movimientoZigZag) {
		        ball.setMovementStrategy(new ZigZagMovement());
		    } else {
		        ball.setMovementStrategy(new NormalMovement());
		    }
		}
	        
		@Override
		public void dispose () {
			// Liberar recursos graficos
		    batch.dispose();
		    font.dispose();
		    shape.dispose();

		    // Liberar recurso de audio
		    if (sonidoRomperBloque != null) {
		        sonidoRomperBloque.dispose();
		    }

		}
	}
