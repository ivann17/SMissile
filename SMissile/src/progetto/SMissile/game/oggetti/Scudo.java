package progetto.SMissile.game.oggetti;

import progetto.SMissile.game.Oggetti;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public class Scudo extends AbstractGameObject {
	
	private TextureRegion regScudo;
	public boolean preso;
	
	public Scudo(){
		init();
	}

	private void init(){
		dimensione.set(8f, 8f);
		regScudo = Oggetti.getInstance().scudo.regScudo;
		//settiamo i bordi per riconoscere la collisione tra gli oggetti
		bounds.set(0,0,dimensione.x,dimensione.y);
		preso = false;	//lo scudo non è stato ancora preso
	}
	
	@Override
	public void render(SpriteBatch batch) {
		if(preso){
			posizione.set(posizione.x+MathUtils.random(950,1900),MathUtils.random(0, 50));
			init();
		}
		TextureRegion reg = null;
		reg = regScudo;
		batch.draw(reg.getTexture(),posizione.x, posizione.y,origine.x,
				origine.y,dimensione.x, dimensione.y,scala.x, scala.y,
				rotazione, reg.getRegionX(), reg.getRegionY(), 
				reg.getRegionWidth(),reg.getRegionHeight(), false, false);
	}
	
	@Override
	public void update(float deltaTime){
		super.update(deltaTime);
	}

	
}
