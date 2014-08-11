package progetto.SMissile.screens;

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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class PauseScreen extends AbstractGameScreen{
	
	private Stage stage;
	private Skin skinSuperMissile;
	private GameScreen gameScreen;
	private GamePreferences prefs;
	private Button btnResume, btnMenu;
	private CheckBox btnMusica, btnSound;
	
	public PauseScreen(DirectedGame game, GameScreen gameScreen) {
		super(game);
		this.gameScreen = gameScreen;
	}

	@Override
	public void show() {
		stage = new Stage();
		rebuildStage();
	}
	
	private void rebuildStage(){
		prefs = GamePreferences.instance;
		prefs.load();
		skinSuperMissile = new Skin(Gdx.files.internal(Constants.SKIN_SUPERMISSILE_UI), new TextureAtlas(Constants.TEXTURE_ATLAS_UI));
		stage.clear();
		resize((int) Constants.VIEWPORT_GUI_WIDTH, (int) Constants.VIEWPORT_GUI_HEIGHT);
		Stack stack = new Stack();
		stack.setSize(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
		stack.add(buildLayerBackground());
		stack.add(buildLayerObjects());
		stack.add(buildLayerAudioSetting());
		stage.addActor(stack);
	}

	private Table buildLayerBackground() {
		Table layer = new Table();
		Image imgBackground = new Image(skinSuperMissile, "sfondo_pausa");
		imgBackground.scale(Constants.VIEWPORT_GUI_HEIGHT / imgBackground.getHeight());
		layer.addActor(imgBackground);
		imgBackground.setPosition(0, 0);
		return layer;
	}

	private Table buildLayerAudioSetting() {
		Table layer = new Table();
		layer.center().top();
		layer.pad(10);
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

	private Table buildLayerObjects() {
		Table layer = new Table();
		layer.bottom().center();
		btnResume = new Button(skinSuperMissile, "resume");
		btnResume.setTouchable(Touchable.enabled);
		btnResume.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.setSchermo(gameScreen);
			}
		});
		layer.add(btnResume);
		layer.row();
		
		btnMenu = new Button(skinSuperMissile, "menu");
		btnMenu.setTouchable(Touchable.enabled);
		btnMenu.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				gameScreen.dispose();
				Transizioni transizione = ScreenTransitionFade.init(0.3f);
				game.setSchermo(new MenuScreen(game), transizione);
			}
		});
		layer.add(btnMenu);
		return layer;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(Constants.VIEWPORT_GUI_WIDTH,	Constants.VIEWPORT_GUI_HEIGHT, false);
	}

	@Override
	public void hide() {
		stage.dispose();
		skinSuperMissile.dispose();
	}

	@Override
	public void pause() { }

	@Override
	public InputProcessor getInputProcessor() {
		return stage;
	}

}
