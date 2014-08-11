package progetto.SMissile.screens;

import progetto.SMissile.screens.transizioni.Transizioni;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

public class DirectedGame implements ApplicationListener {
	
	private boolean init;
	private AbstractGameScreen curSchermo;
	private AbstractGameScreen nextSchermo;
	private FrameBuffer curFbo;
	private FrameBuffer nextFbo;
	private SpriteBatch batch;
	private float t;
	private Transizioni screenTransition;
	
	public void setSchermo (AbstractGameScreen schermo){
		setSchermo(schermo, null);
	}
	
	public void setSchermo(AbstractGameScreen schermo, Transizioni screenTransition){
		int w = Gdx.graphics.getWidth();
		int h = Gdx.graphics.getHeight();
		if(!init){
			curFbo = new FrameBuffer(Format.RGB888, w, h, false);
			nextFbo = new FrameBuffer(Format.RGB888, w, h, false);
			batch = new SpriteBatch();
			init = true;
		}
		//inizia transizione
		nextSchermo = schermo;
		nextSchermo.show(); //attiva nextSchermo
		nextSchermo.resize(w, h);
		nextSchermo.render(0); //aggiorna lo schermo una volta
		Gdx.input.setInputProcessor(null); //disabilita input
		this.screenTransition = screenTransition;
		t = 0;		
	}

	@Override
	public void create() {	}

	@Override
	public void resize(int width, int height) {
		if(curSchermo != null) curSchermo.resize(width, height);
		if(nextSchermo != null) nextSchermo.resize(width, height);
	}

	@Override
	public void render() {
		// get delta time e garantisce un limite superiore di un 60-esimo di secondo
		float deltaTime = Math.min(Gdx.graphics.getDeltaTime(), 1.0f / 60.0f);
		
		if(nextSchermo == null){
			// nessuna transizione in corso
			if(curSchermo != null) curSchermo.render(deltaTime);
		}
		else{
			//transizione in corso
			float durata = 0;
			if(screenTransition != null)
				durata = screenTransition.getDurata();
			//aggiorna progressi della transizione in corso
			t = Math.min(t + deltaTime, durata);
			if(screenTransition == null || t >= durata){
				//nessun effetto di transizione || transizione appena finita
				if(curSchermo != null) curSchermo.hide();
				nextSchermo.resume();
				//abilita input per la prossima schermata
				Gdx.input.setInputProcessor(nextSchermo.getInputProcessor());
				// cambia schermate
				curSchermo = nextSchermo;
				nextSchermo = null;
				screenTransition = null;
			}
			else{
				// render schermi to FBOs
				curFbo.begin();
				if(curSchermo != null) curSchermo.render(deltaTime);
				curFbo.end();
				nextFbo.begin();
				nextSchermo.render(deltaTime);
				nextFbo.end();
				// render l'effetto di transizione sullo schermo
				float alpha = t/durata;
				screenTransition.render(batch, curFbo.getColorBufferTexture(), nextFbo.getColorBufferTexture(), alpha);
			}
		}
	}//render

	@Override
	public void pause() {
		if(curSchermo != null) curSchermo.pause();
	}

	@Override
	public void resume() {
		if(curSchermo != null) curSchermo.resume();
	}

	@Override
	public void dispose() {
		if(curSchermo != null) curSchermo.hide();
		if(nextSchermo != null) nextSchermo.hide();
		if(init){
			curFbo.dispose();
			curSchermo = null;
			nextFbo.dispose();
			nextSchermo = null;
			batch.dispose();
			init = false;
		}
	}

}