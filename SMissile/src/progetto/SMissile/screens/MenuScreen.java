package progetto.SMissile.screens;

import progetto.SMissile.game.Oggetti;
import progetto.SMissile.screens.transizioni.ScreenTransitionFade;
import progetto.SMissile.screens.transizioni.Transizioni;
import progetto.SMissile.util.AudioManager;
import progetto.SMissile.util.Constants;
import progetto.SMissile.util.GamePreferences;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MenuScreen extends AbstractGameScreen {
	@SuppressWarnings("unused")
	private static final String TAG = MenuScreen.class.getName();
	
	private Stage stage;
	private Skin skinSuperMissile;
	private Skin skinLibgdx;
	
	//menu
	private Image imgBackground;
	private Button btnPlay;
	private Button btnInfo;
	private Button btnShop;
	
	//impostazioni
	private GamePreferences prefs;
	private CheckBox btnMusica, btnSound;
	
	private boolean debugEnabled = false;	
	
	public MenuScreen(DirectedGame game) {
		super(game);
	}
	
	@Override
	public void render(float deltaTime){
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		stage.act(deltaTime);
		stage.draw();
	}

	@Override
	public void resize (int width, int height) {
		stage.setViewport(Constants.VIEWPORT_GUI_WIDTH,	Constants.VIEWPORT_GUI_HEIGHT, false);
	}
	
	@Override
	public void show () {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		AudioManager.instance.play(Oggetti.getInstance().music.musica_sottofondo);
		rebuildStage();
	}
	
	@Override 
	public void hide () {
		stage.dispose();
		skinSuperMissile.dispose();
		skinLibgdx.dispose();
	}
	
	@Override 
	public void pause () { }
	
	private void rebuildStage(){
		
		prefs = GamePreferences.instance;
		prefs.load();
		
		skinSuperMissile = new Skin(Gdx.files.internal(Constants.SKIN_SUPERMISSILE_UI), new TextureAtlas(Constants.TEXTURE_ATLAS_UI));
		skinLibgdx = new Skin(Gdx.files.internal(Constants.SKIN_LIBGDX_UI),	new TextureAtlas(Constants.TEXTURE_ATLAS_LIBGDX_UI));

		stage.clear();
		Stack stack = new Stack();
		stage.addActor(stack);
		stack.setSize(Constants.VIEWPORT_GUI_WIDTH,	Constants.VIEWPORT_GUI_HEIGHT);
		stack.add(buildBackgroundLayer());
		stack.add(buildControlsLayer());
		stack.add(buildShopLayer());
		stack.add(buildAudioLayer());
		stack.add(buildRecordLayer());
		stack.add(buildMoneteRaccolteLayer());
	}
	
	private Table buildBackgroundLayer () {
		Table layer = new Table();
		// sfondo
		imgBackground = new Image(skinSuperMissile, "background");
		layer.add(imgBackground);
		return layer;
	}
	
	private Table buildShopLayer() {
		Table layer = new Table();

		//bottone shop
		btnShop = new Button(skinSuperMissile, "shop");
		layer.add(btnShop);
		btnShop.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Transizioni transizione = ScreenTransitionFade.init(0.25f);
				game.setSchermo(new ShopScreen(game),transizione);
			}
		});
		layer.right().bottom();
	return layer;
	}
	
	private Table buildControlsLayer () {
		Table layer = new Table();
		// bottone play
		btnPlay = new Button(skinSuperMissile, "play");
		layer.add(btnPlay);
		btnPlay.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				Transizioni transizione = ScreenTransitionFade.init(0.25f);
				game.setSchermo(new GameScreen(game),transizione);
			}
		});
		layer.row();
		
		// bottone info
		btnInfo = new Button(skinSuperMissile, "info");
		layer.add(btnInfo);
		btnInfo.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Transizioni transizione = ScreenTransitionFade.init(0.15f);
				game.setSchermo(new InfoScreen(game),transizione);
			}
		});
		if(debugEnabled)
			layer.debug();
		layer.right().padRight(200);
		layer.bottom().padBottom(70);
		return layer;
	}

	private Table buildRecordLayer(){
		Table layer = new Table();
		layer.pad(500,-500, 0, 0);
		Label lblRecord = new Label("Record: "+(int)prefs.record+" pt", skinSuperMissile, "fontscore");
		layer.add(lblRecord);
	return layer;
	}
	
	private Table buildMoneteRaccolteLayer(){
		Table layer = new Table();
		layer.pad(470, 760, 60, 10);
		Label lblMoneteRaccolte = new Label(": "+(int)prefs.moneteRaccolte, skinSuperMissile, "fontmonete");
		layer.add(lblMoneteRaccolte);
	return layer;
	}
	
	private Table buildAudioLayer() {
		Table layer = new Table();
		layer.pad(180, 0, 300, 600);
		layer.columnDefaults(0).padRight(10);
		layer.columnDefaults(1).padRight(10);
		btnMusica = new CheckBox("", skinSuperMissile, "musica");
		btnMusica.setChecked(!prefs.music);
		btnMusica.setTouchable(Touchable.enabled);
		btnMusica.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				prefs.music = !btnMusica.isChecked();
				prefs.save();
				AudioManager.instance.onSettingsUpdated();
			}
		});
		layer.add(btnMusica);
		
		btnSound = new CheckBox("", skinSuperMissile, "sound");
		btnSound.setChecked(!prefs.sound);
		btnSound.setTouchable(Touchable.enabled);
		btnSound.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				prefs.sound = !btnSound.isChecked();
				prefs.save();
				AudioManager.instance.onSettingsUpdated();
			}
		});
		layer.add(btnSound);
		return layer;
	}

	@Override
	public InputProcessor getInputProcessor() {
		return stage;
	}
	
}
