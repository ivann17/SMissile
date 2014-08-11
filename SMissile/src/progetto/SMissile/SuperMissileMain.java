package progetto.SMissile;

import progetto.SMissile.game.Oggetti;
import progetto.SMissile.screens.DirectedGame;
import progetto.SMissile.screens.MenuScreen;
import progetto.SMissile.util.GamePreferences;
import progetto.SMissile.util.AudioManager;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;

public class SuperMissileMain extends DirectedGame{
	@SuppressWarnings("unused")
	private static final String TAG = SuperMissileMain.class.getName();
	
	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_NONE);		//da cambiare a LOG_NONE prima di pubblicare il gioco
		//carico gli oggetti							//lasciare LOG_DEBUG per il debug in fase di prova
		Oggetti.getInstance().init(new AssetManager());
		//carico i suoni e la musica
		GamePreferences.instance.load();
		//faccio partire la musica
		AudioManager.instance.play(Oggetti.getInstance().music.musica_sottofondo);
		//start del gioco nel menu principale
		setSchermo(new MenuScreen(this));
	}

}