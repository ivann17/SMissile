package progetto.SMissile.game.oggetti;

import progetto.SMissile.game.Oggetti;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public class Stella extends AbstractGameObject{

	private TextureRegion regStella;
	public boolean presa;
	
	public Stella(){
		init();
	}
	
	private void init(){
		dimensione.set(9f, 9f);
		regStella = Oggetti.getInstance().stella.regStella;
		//settiamo i bordi per riconoscere la collisione tra gli oggetti
		bounds.set(0,0,dimensione.x,dimensione.y);		
		presa=false;	//la stella non è stata ancora presa
	}
	
	@Override
	public void render(SpriteBatch batch) {
		if(presa){
			posizione.set(posizione.x+MathUtils.random(1800,2700),MathUtils.random(0, 50));
			init();
		}
		TextureRegion reg=null;
		reg=regStella;
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
