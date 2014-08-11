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

public class ShopScreen extends AbstractGameScreen{
	
	private Stage stage;
	private Skin skinSuperMissile;
	private GamePreferences prefs;
	private Button btnPotenziaCalamita;
	private Button btnPotenziaStella;
	private Button btnPotenziaScudo;
	private Button btnBack, btnOk;
	
	private Stack stack;
	
	public ShopScreen(DirectedGame game) {
		super(game);
	}

	private void rebuildStage() {
		prefs = GamePreferences.instance;
		prefs.load();
		skinSuperMissile = new Skin(Gdx.files.internal(Constants.SKIN_SUPERMISSILE_UI), new TextureAtlas(Constants.TEXTURE_ATLAS_UI));
	
		stage.clear();
		resize((int)Constants.VIEWPORT_GUI_WIDTH, (int) Constants.VIEWPORT_GUI_HEIGHT);
		//Stack stack = new Stack();
		stack = new Stack();
		stage.addActor(stack);
		stack.setSize(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
		stack.add(buildLayerBackground());
		stack.add(buildLayerPotenziaCalamita());
		stack.add(buildLayerPotenziaStella());
		stack.add(buildLayerPotenziaScudo());
		stack.add(buildLayerBack());
		stack.add(buildLayerLivelliPotenziamenti());
		stack.add(buildLayerPrezziPotenziamenti());
		stack.add(buildMoneteRaccolteLayer());
	}
	
	private Table buildOkMessaggio() {
		Table layer = new Table();
		btnOk = new Button(skinSuperMissile, "messaggio_monete");
		btnOk.setTouchable(Touchable.enabled);
		btnOk.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				btnOk.setVisible(false);
				abilitaBottoni();
			}
		});
		layer.add(btnOk);
	return layer;
	}

	private Table buildMoneteRaccolteLayer(){
		Table layer = new Table();
		layer.pad(-510, -680, 0, 0);
		Label lblMoneteRaccolte = new Label(": "+(int)prefs.moneteRaccolte, skinSuperMissile, "fontmonete");
		layer.add(lblMoneteRaccolte);
	return layer;
	}
	
	private Actor buildLayerLivelliPotenziamenti() {
		Table layer = new Table();
		layer.top().pad(35);
		Label lblLivelli = new Label("        " + (int)prefs.livelloCalamita + 
									 "          " + (int)prefs.livelloStella +
									 "           " + (int)prefs.livelloScudo, skinSuperMissile);
		layer.add(lblLivelli);
	return layer;
	}

	private Actor buildLayerPrezziPotenziamenti() {
		Table layer = new Table();
		layer.pad(255, 30, 0, 0);
		int prezzoCalamita = prezzoCalamita((int)prefs.livelloCalamita);
		int prezzoStella = prezzoStella((int)prefs.livelloStella);
		int prezzoScudo = prezzoScudo((int)prefs.livelloScudo);
		/*
		Label lblLivelli = new Label(" " + (int)prezzoCalamita + 
									 " $                    " + (int)prezzoStella +
									 " $                     " + (int)prezzoScudo + " $", skinSuperMissile, "fontmonete");
		 */
		String s = "";
		
		if(prefs.livelloCalamita < 5)
			s += " " + (int)prezzoCalamita + " $";
		else
			s += "max";
		if(prefs.livelloStella < 5)
			s += "                     " + (int)prezzoStella + " $";
		else
			s += "max                         ";
		if(prefs.livelloScudo < 5)
			s += "                      " + (int)prezzoScudo + " $";
		else
			s += "max                         ";
		
		Label lblLivelli = new Label(s,skinSuperMissile, "fontmonete" );
		
		layer.add(lblLivelli);
	return layer;
	}
	
	private int prezzoCalamita(int livelloCalamita){
		int prezzo = 0;
		switch(livelloCalamita){
			case 1: prezzo = 2000; break;
			case 2: prezzo = 3500; break;
			case 3: prezzo = 7000; break;
			default: prezzo = 10000;
		}
	return prezzo;
	}
	
	private int prezzoStella(int livelloStella){
		int prezzo = 0;
		switch(livelloStella){
			case 1: prezzo = 2000; break;
			case 2: prezzo = 3500; break;
			case 3: prezzo = 7000; break;
			default: prezzo = 10000;
		}
	return prezzo;
	}
	
	private int prezzoScudo(int livelloScudo){
		int prezzo = 0;
		switch(livelloScudo){
			case 1: prezzo = 2000; break;
			case 2: prezzo = 3500; break;
			case 3: prezzo = 7000; break;
			default: prezzo = 10000;
		}
	return prezzo;
	}
	
	private void abilitaBottoni() {
		btnPotenziaCalamita.setTouchable(Touchable.enabled);
		btnPotenziaStella.setTouchable(Touchable.enabled);
		btnPotenziaScudo.setTouchable(Touchable.enabled);
		btnBack.setTouchable(Touchable.enabled);
	}
	
	private void disabilitaBottoni() {
		btnPotenziaCalamita.setTouchable(Touchable.disabled);
		btnPotenziaStella.setTouchable(Touchable.disabled);
		btnPotenziaScudo.setTouchable(Touchable.disabled);
		btnBack.setTouchable(Touchable.disabled);
	}
	
	private Table buildLayerPotenziaCalamita() {
		Table layer = new Table();
		layer.bottom().left();
		layer.row();
		btnPotenziaCalamita = new Button(skinSuperMissile, "potenzia");
		btnPotenziaCalamita.setTouchable(Touchable.enabled);
		btnPotenziaCalamita.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(prefs.livelloCalamita < 5){
					if(prefs.moneteRaccolte >= prezzoCalamita((int)prefs.livelloCalamita)){
						prefs.moneteRaccolte -= prezzoCalamita((int)prefs.livelloCalamita);
						prefs.livelloCalamita += 1;
						prefs.save();
						Transizioni transizione = ScreenTransitionFade.init(0.5f);
						game.setSchermo(new ShopScreen(game), transizione);
					}
					else{
						//non hai abbastanza monete
						disabilitaBottoni();
						stack.add(buildOkMessaggio());
					}
				}
			}
		});
		layer.add(btnPotenziaCalamita);
		
	return layer;
	}
	
	private Table buildLayerPotenziaStella() {
		Table layer = new Table();
		layer.bottom();
		layer.row();
		btnPotenziaStella = new Button(skinSuperMissile, "potenzia");
		btnPotenziaStella.setTouchable(Touchable.enabled);
		btnPotenziaStella.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				
				if(prefs.livelloStella < 5){
					if(prefs.moneteRaccolte >= prezzoStella((int)prefs.livelloStella)){
						prefs.moneteRaccolte -= prezzoStella((int)prefs.livelloStella);
						prefs.livelloStella += 1;
						prefs.save();
						Transizioni transizione = ScreenTransitionFade.init(0.5f);
						game.setSchermo(new ShopScreen(game), transizione);
					}
					else{
						//NON HAI ABBASTANZA MONETE
						disabilitaBottoni();
						stack.add(buildOkMessaggio());
					}
				}
				
				/*
				//per azzerare i potenziamenti
				//e prendere 20000 monete
				prefs.livelloStella=1;
				prefs.livelloCalamita=1;
				prefs.livelloScudo=1;
				prefs.moneteRaccolte=20000;
				prefs.save();
				*/
			}
		});
		layer.add(btnPotenziaStella);
		
	return layer;
	}
	
	private Table buildLayerPotenziaScudo() {
		Table layer = new Table();
		layer.bottom().right();
		layer.row();
		btnPotenziaScudo = new Button(skinSuperMissile, "potenzia");
		btnPotenziaScudo.setTouchable(Touchable.enabled);
		btnPotenziaScudo.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(prefs.livelloScudo < 5){
					if(prefs.moneteRaccolte >= prezzoScudo((int)prefs.livelloScudo)){
						prefs.moneteRaccolte -= prezzoScudo((int)prefs.livelloScudo);
						prefs.livelloScudo += 1;
						prefs.save();
						Transizioni transizione = ScreenTransitionFade.init(0.5f);
						game.setSchermo(new ShopScreen(game), transizione);
					}
					else{
						//NON HAI ABBASTANZA MONETE
						disabilitaBottoni();
						stack.add(buildOkMessaggio());
						}
				}
			}
		});
		layer.add(btnPotenziaScudo);
		
	return layer;
	}

	private Table buildLayerBack() {
		Table layer = new Table();
		layer.top();
		layer.right();
		layer.row();
		btnBack = new Button(skinSuperMissile, "backpiccolo");
		btnBack.setTouchable(Touchable.enabled);
		btnBack.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Transizioni transizione = ScreenTransitionFade.init(1.0f);
				game.setSchermo(new MenuScreen(game), transizione);
			}
		});
		layer.add(btnBack);

	return layer;
	}
	
	private Actor buildLayerBackground() {
		Table layer = new Table();
		Image imgBackground = new Image(skinSuperMissile, "img_shop");
		layer.addActor(imgBackground);
		imgBackground.setPosition(0, 0);
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
