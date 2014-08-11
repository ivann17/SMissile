package progetto.SMissile.game.oggetti;

import progetto.SMissile.game.Oggetti;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public class MonetaDOro extends AbstractGameObject{
	
	private TextureRegion regMonetaDOro;
	public boolean collected;

	public MonetaDOro(){
		init();
	}
	
	private void init(){
		dimensione.set(4f, 4f);
		regMonetaDOro = Oggetti.getInstance().moneta.regMoneta;
		//settiamo i bordi per riconoscere la collisione tra gli oggetti
		bounds.set(0,0,dimensione.x,dimensione.y);
		collected=false;	//la moneta non è stata ancora presa
	}

	public void render(SpriteBatch batch){
		if(collected){
			posizione.set(posizione.x+MathUtils.random(80,120),MathUtils.random(0, 50));
			init();
		}
		TextureRegion reg=null;
		reg=regMonetaDOro;
		batch.draw(reg.getTexture(),posizione.x, posizione.y,origine.x,
				origine.y,dimensione.x, dimensione.y,scala.x, scala.y,
				rotazione, reg.getRegionX(), reg.getRegionY(), 
				reg.getRegionWidth(),reg.getRegionHeight(), false, false);
	}
	
	public int getScore(){
		// ogni moneta presa aumenta il punteggio di 5 punti
		return 5;
	}
	
	@Override
	public void update(float deltaTime){
		super.update(deltaTime);
	}
	
}
