package progetto.SMissile.screens;

import progetto.SMissile.screens.transizioni.ScreenTransitionFade;
import progetto.SMissile.screens.transizioni.Transizioni;
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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class InfoScreenBonus extends AbstractGameScreen{

	private Stage stage;
	private Skin skinSuperMissile;
	private GamePreferences prefs;
	private Button btnAvanti;
	private Button btnIndietro;
	
	public InfoScreenBonus(DirectedGame game) {
		super(game);
	}
	
	private void rebuildStage(){	
		prefs = GamePreferences.instance;
		prefs.load();
		skinSuperMissile = new Skin(Gdx.files.internal(Constants.SKIN_SUPERMISSILE_UI), new TextureAtlas(Constants.TEXTURE_ATLAS_UI));

		stage.clear();
		resize((int) Constants.VIEWPORT_GUI_WIDTH, (int) Constants.VIEWPORT_GUI_HEIGHT);
		Stack stack = new Stack();
		stage.addActor(stack);
		stack.setSize(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
		stack.add(buildLayerBackground());
		stack.add(buildLayerAvanti());
		stack.add(buildLayerIndietro());
	}
	
	private Table buildLayerBackground() {
		Table layer = new Table();
		Image imgBackground = new Image(skinSuperMissile, "img_info_bonus");
		layer.addActor(imgBackground);
		imgBackground.setPosition(-30, 0);
		return layer;
	}
	
	private Table buildLayerAvanti() {
		Table layer = new Table();
		layer.right().pad(-60);
		layer.row();
		btnAvanti = new Button(skinSuperMissile, "frecciadx");
		btnAvanti.setTouchable(Touchable.enabled);
		btnAvanti.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Transizioni transizione = ScreenTransitionFade.init(0.15f);
				game.setSchermo(new InfoScreenNemici(game), transizione);
			}
		});
		layer.add(btnAvanti);

	return layer;
	}
	
	private Table buildLayerIndietro() {
		Table layer = new Table();
		//layer.left().pad(60);
		layer.pad(20, -770, 0, 0);
		layer.row();
		btnIndietro = new Button(skinSuperMissile, "frecciasx");
		btnIndietro.setTouchable(Touchable.enabled);
		btnIndietro.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Transizioni transizione = ScreenTransitionFade.init(0.15f);
				game.setSchermo(new InfoScreen(game), transizione);
			}
		});
		layer.add(btnIndietro);

	return layer;
	}

	@Override
	public InputProcessor getInputProcessor() {
		return stage;
	}

	@Override
	public void render(float deltaTime) {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		stage.act(deltaTime);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(Constants.VIEWPORT_GUI_WIDTH,	Constants.VIEWPORT_GUI_HEIGHT, false);
	}

	@Override
	public void show() {
		stage = new Stage();
		rebuildStage();
	}

	@Override
	public void hide() {
		stage.dispose();
		skinSuperMissile.dispose();
	}

	@Override
	public void pause() { }
	
}
