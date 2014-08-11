package progetto.SMissile.game.oggetti;

import progetto.SMissile.game.Oggetti;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public class Bomba extends AbstractGameObject {
	
	private TextureRegion regBomba;
	public boolean presa;
	
	public Bomba(){
		init();
	}
	
	public void init(){
		dimensione.set(15f, 15f);
		regBomba = Oggetti.getInstance().bomba.regBomba;
		//settiamo i bordi per riconoscere le collisioni
		bounds.set(0, 0, dimensione.x, dimensione.y);
		presa = false;		
	}

	@Override
	public void render(SpriteBatch batch) {
		if(presa){
			posizione.set(posizione.x+MathUtils.random(1200,2000),MathUtils.random(0, 50));
			init();
		}
		TextureRegion reg = null;
		reg = regBomba;
		
		// impostiamo lo shake della bomba
		float velShake = 20.0f;
		rotazione = (rotazione + Gdx.graphics.getDeltaTime() * velShake) % 360;
		final float ampiezzaShake = 10.0f;
		float shake = MathUtils.sin(rotazione)*ampiezzaShake;
		batch.draw(reg.getTexture(),posizione.x, posizione.y,origine.x,
				origine.y,dimensione.x, dimensione.y,scala.x, scala.y,
				shake, reg.getRegionX(), reg.getRegionY(), 
				reg.getRegionWidth(),reg.getRegionHeight(), false, false);
	}
	
	@Override
	public void update(float deltaTime){
		super.update(deltaTime);
	}

}
