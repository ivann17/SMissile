package progetto.SMissile.game;

import progetto.SMissile.util.Constants;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Disposable;

public class WorldRenderer implements Disposable{
	@SuppressWarnings("unused")
	private static final String TAG = WorldRenderer.class.getName();
	
	
	private OrthographicCamera camera;
	private OrthographicCamera cameraGUI;
	private SpriteBatch batch;
	private WorldController worldController;
	
	public WorldRenderer(WorldController worldController){
		this.worldController=worldController;
		init();
	}
	
	private void renderWorld (SpriteBatch batch) {
		worldController.cameraHelper.applyTo(camera);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		worldController.livello.render(batch);
		batch.end();
	}
	
	private void init () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
		camera.position.set(0, 0, 0);
		camera.update();
		
		cameraGUI = new OrthographicCamera(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
		cameraGUI.position.set(0, 0, 0);
		cameraGUI.setToOrtho(true);
		cameraGUI.update();
	}
	
	public void render () {
		renderWorld(batch);
		renderGui(batch);
	}
	
	public void resize (int width, int height) {
		camera.viewportWidth = (Constants.VIEWPORT_GUI_HEIGHT / height) *  width;
		camera.update();
		
		cameraGUI.viewportHeight = Constants.VIEWPORT_GUI_HEIGHT;
		cameraGUI.viewportWidth = (Constants.VIEWPORT_GUI_HEIGHT / (float)height) * (float)width;
		cameraGUI.position.set(cameraGUI.viewportWidth / 2,	cameraGUI.viewportHeight / 2, 0);
		cameraGUI.update();
	}
	
	@Override 
	public void dispose () {
		batch.dispose();
	}
	
	private void renderGui (SpriteBatch batch) {
		batch.setProjectionMatrix(cameraGUI.combined);
		batch.begin();
		
		// visulizza il numero di monete raccolte
		// e gli disegna vicino una moneta che
		// vibra ogni volta che ne raccogli una
		renderGuiScore(batch);
		// disegna il messaggio di game-over
		renderGuiGameOverMessage(batch);
		// disegna la stella quando viene presa
		renderGuiStella(batch);
		// disegna la calamita quando viene presa
		renderGuiCalamita(batch);
		// disegna lo scudo quando viene preso
		renderGuiScudo(batch);
		// visualizza il numero di metri percorsi
		renderGuiMetri(batch);
		
		batch.end();
	}
	
	//moneta con numero in alto a sx nello schermo
	private void renderGuiScore (SpriteBatch batch) {
		float x = -15;
		float y = -15;
		float offsetX = 50;
		float offsetY = 50;
		if(worldController.scoreVisual < worldController.score){
			long shakeAlpha = System.currentTimeMillis() % 360;
			float shakeDist = 1.5f;
			offsetX += MathUtils.sin(shakeAlpha * 2.2f) * shakeDist;
			offsetY += MathUtils.sin(shakeAlpha * 2.9f) * shakeDist;
		}
		batch.draw(Oggetti.getInstance().moneta.regMoneta, x, y, offsetX, offsetY, 110, 110, 0.35f, -0.35f, 0);
		Oggetti.getInstance().fonts.defaultBig.draw(batch, "" + worldController.score, x + 80, y + 35);
	}
	
	//visibile quando prendi calamita, in alto a dx
	private void renderGuiCalamita (SpriteBatch batch){
		float x = cameraGUI.viewportWidth - 155;
		float y = 35;
		float timeLeftCalamita = worldController.livello.missile.timeLeftCalamita;
		if(timeLeftCalamita > 0) {
			if(timeLeftCalamita < 4){
				if(((int)(timeLeftCalamita * 5) %2) !=0){
					batch.setColor(1, 1, 1, 0.5f);
				}
			}
			batch.draw(Oggetti.getInstance().calamita.regCalamita, x , y, 50, 50, 130, 130, 0.35f, -0.35f, 0);
			batch.setColor(1, 1, 1, 1);
			Oggetti.getInstance().fonts.defaultNormal.draw(batch, "" + (int)timeLeftCalamita, x + 60, y + 57);
		}
	}

	//visibile quando prendi uno scudo, in alto a dx
	private void renderGuiScudo (SpriteBatch batch){
		float x = cameraGUI.viewportWidth - 155;
		float y = 35;
		float timeLeftScudo = worldController.livello.missile.timeLeftScudo;
		if(timeLeftScudo > 0) {
			if(timeLeftScudo < 4){
				if(((int)(timeLeftScudo * 5) %2) !=0){
					batch.setColor(1, 1, 1, 0.5f);
				}
			}
			batch.draw(Oggetti.getInstance().scudo.regScudo, x , y, -50, 50, 170, 170, 0.35f, -0.35f, 0);
			batch.setColor(1, 1, 1, 1);
			Oggetti.getInstance().fonts.defaultNormal.draw(batch, "" + (int)timeLeftScudo, x , y + 57);
		}
	}

	//visibile quando prendi una stella, in alto a dx
	private void renderGuiStella (SpriteBatch batch) {
		float x = cameraGUI.viewportWidth - 100;
		float y = 35;
		float timeLeftPowerUp = worldController.livello.missile.timeLeftPowerUp;
		if(timeLeftPowerUp > 0) {
			if(timeLeftPowerUp < 4){
				if(((int)(timeLeftPowerUp * 5) %2) !=0){
					batch.setColor(1, 1, 1, 0.5f);
				}
			}
			batch.draw(Oggetti.getInstance().stella.regStella, x , y, 50, 50, 150, 150, 0.35f, -0.35f, 0);
			batch.setColor(1, 1, 1, 1);
			Oggetti.getInstance().fonts.defaultNormal.draw(batch, "" + (int)timeLeftPowerUp, x + 60, y + 57);
		}
	}
		
	private void renderGuiGameOverMessage(SpriteBatch batch){
		float x = cameraGUI.viewportWidth / 2;
		float y = cameraGUI.viewportHeight / 2;
		if (worldController.isGameOver()) {
			BitmapFont fontGameOver = Oggetti.getInstance().fonts.gameOverFont;
			fontGameOver.drawMultiLine(batch, "Game over!", x, y, 50 , BitmapFont.HAlignment.CENTER);
			fontGameOver.setColor(1, 1, 1, 1);
		}
	}

	private void renderGuiMetri(SpriteBatch batch){
		float x = -35;
		float y = 40;
		int metri = - 10;	//il missile inizia da 10 metri
		metri += worldController.metriPercorsi;
		Oggetti.getInstance().fonts.defaultBig.draw(batch, metri+" m", x + 65, y + 25);
		//per controllare la velocità
		//Oggetti.getInstance().fonts.defaultBig.draw(batch,worldController.livello.missile.velocity.x +" m/s", x + 65, y + 65);
	}
	
}
