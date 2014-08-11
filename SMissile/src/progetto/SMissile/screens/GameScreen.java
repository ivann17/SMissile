package progetto.SMissile.screens;

import progetto.SMissile.game.WorldController;
import progetto.SMissile.game.WorldRenderer;
import progetto.SMissile.screens.transizioni.ScreenTransitionFade;
import progetto.SMissile.screens.transizioni.Transizioni;
import progetto.SMissile.util.GamePreferences;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL10;

public class GameScreen extends AbstractGameScreen{
	@SuppressWarnings("unused")
	private static final String TAG = GameScreen.class.getName();
	
	private WorldController worldController;
	private WorldRenderer worldRenderer;
	
	private boolean paused, init;
	
	public GameScreen(DirectedGame game){
		super(game);
		paused = false;
	}
	
	@Override
	public void render(float deltaTime){
		//non fare niente se il gioco è in pausa
		if(!paused && init){
			// aggiorna il mondo di gioco dal tempo passato
			// dall'ultimo frame che è stato disegnato.
			worldController.update(deltaTime);
		}
		// settiamo il colore dello schermo ad azzurro
		// lo utilizziamo come cielo
		Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f, 0xed / 255.0f, 0xff / 255.0f);
		// puliamo lo schermo dopo
		// ogni disegno reso
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		// visualizziamo il mondo di gioco sullo schermo
		worldRenderer.render();
	}
	
	@Override
	public void resize (int width, int height) {
		worldRenderer.resize(width, height);
	}
	
	@Override
	public void dispose(){
		worldRenderer.dispose();
	}
	
	@Override
	public void show () {
		if(!init){
			GamePreferences.instance.load();
			worldController = new WorldController(game);
			worldRenderer = new WorldRenderer(worldController);
			init = true;
			Gdx.input.setCatchBackKey(true);
		}
		else
			paused=false;
	}
	
	@Override
	public void hide () {
		if(!paused)
			Gdx.input.setCatchBackKey(false);
	}
	
	@Override
	public void pause () {
		paused = true;
		Transizioni transizione = ScreenTransitionFade.init(0.15f);
		game.setSchermo(new PauseScreen(game, this), transizione);
	}
	
	@Override
	public void resume () {
		paused = false;
	}

	@Override
	public InputProcessor getInputProcessor() {
		return worldController;
	}
	
}
