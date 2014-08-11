package progetto.SMissile.screens.transizioni;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;

public class ScreenTransitionFade implements Transizioni {
	
	private static final ScreenTransitionFade instance = new ScreenTransitionFade();
	
	private float durata;
	
	public static ScreenTransitionFade init(float durata){
		instance.durata = durata;
		return instance;
	}

	@Override
	public float getDurata() {
		return durata;
	}

	@Override
	public void render(SpriteBatch batch, Texture corSchermo, Texture nextSchermo, float alpha) {
		float w = corSchermo.getWidth();
		float h = nextSchermo.getHeight();
		alpha = Interpolation.fade.apply(alpha);
		
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.setColor(1, 1, 1, alpha);
		batch.draw(nextSchermo, 0, 0, 0, 0, w, h, 1, 1, 0, 0, 0, nextSchermo.getWidth(), nextSchermo.getHeight(), false, true);
		batch.end();
	}
	
}