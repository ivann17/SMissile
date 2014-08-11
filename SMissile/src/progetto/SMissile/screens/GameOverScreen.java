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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class GameOverScreen extends AbstractGameScreen{
	
	private Stage stage;
	private Skin skinSuperMissile;
	private GamePreferences prefs;
	private int punteggio;	//monete + metri
	private int metriPercorsi;
	private float moneteRaccolte;
	private float record;
	private Button btnRetry, btnMenu;
	
	public GameOverScreen(DirectedGame game, float moneteRaccolte, int metriPercorsi){
		super(game);
		this.moneteRaccolte = (int)moneteRaccolte;
		this.metriPercorsi = metriPercorsi;
	}
	
	@Override
	public void show() {
		stage = new Stage();
		prefs = GamePreferences.instance;
		prefs.load();
		rebuildStage();
	}
	
	private void rebuildStage(){
		record = prefs.record;
		punteggio = (int)metriPercorsi + (int)moneteRaccolte;
		if(record < punteggio)
			prefs.record = punteggio;
		prefs.moneteRaccolte += moneteRaccolte;
		prefs.save();
		skinSuperMissile = new Skin(Gdx.files.internal(Constants.SKIN_SUPERMISSILE_UI), new TextureAtlas(Constants.TEXTURE_ATLAS_UI));
		stage.clear();
		resize((int) Constants.VIEWPORT_GUI_WIDTH, (int) Constants.VIEWPORT_GUI_HEIGHT);
		Stack stack = new Stack();
		stack.setSize(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
		stack.add(buildLayerBackground());
		stack.add(buildLayerObjects());
		stack.add(buildLayerScore());
		stage.addActor(stack);
	}
	
	private Table buildLayerBackground() {
		Table layer = new Table();
		Image imgBackground = new Image(skinSuperMissile, "sfondo_game_over");
		layer.addActor(imgBackground);
		imgBackground.setPosition(0, 0);
	return layer;
	}
	
	private Table buildLayerScore(){
		Table layer = new Table();
		layer.top();
		layer.top().pad(-5);
		layer.row();
		//punteggio = (int)metriPercorsi + (int)moneteRaccolte;
		Label lblScore = new Label("SCORE: " + punteggio + " pt", skinSuperMissile, "fontscore");
		Label lblMoneteRaccolte = new Label("Monete raccolte: "+(int)moneteRaccolte, skinSuperMissile, "fontscore");
		Label lblmetriPercorsi = new Label("Metri percorsi: "+(int)metriPercorsi, skinSuperMissile, "fontscore");
		layer.add(lblMoneteRaccolte);
		layer.row();
		layer.add(lblmetriPercorsi);
		layer.row();
		layer.row();
		layer.add(lblScore);
	return layer;
	}
	
	private Table buildLayerObjects() {
		Table layer = new Table();
		layer.center().bottom();
		layer.bottom().pad(35);
		btnRetry = new Button(skinSuperMissile, "retry");
		btnRetry.setTouchable(Touchable.enabled);
		btnRetry.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Transizioni transizione = ScreenTransitionFade.init(0.3f);
				game.setSchermo(new GameScreen(game), transizione);
			}
		});
		layer.add(btnRetry);
		layer.row();
		
		btnMenu = new Button(skinSuperMissile, "menu");
		btnMenu.setTouchable(Touchable.enabled);
		btnMenu.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Transizioni transizione = ScreenTransitionFade.init(0.3f);
				game.setSchermo(new MenuScreen(game), transizione);
			}
		});
		layer.add(btnMenu);

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
		stage.setViewport(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT, false);		
	}

	@Override
	public void hide() {
		stage.dispose();
		skinSuperMissile.dispose();
	}

	@Override
	public void pause() { }

}
