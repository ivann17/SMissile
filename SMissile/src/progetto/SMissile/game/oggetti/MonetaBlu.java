package progetto.SMissile.game.oggetti;

import progetto.SMissile.game.Oggetti;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public class MonetaBlu extends AbstractGameObject{
	
	private TextureRegion regMonetaBlu;
	public boolean collected;
	
	public MonetaBlu(){
		init();
	}
	
	private void init(){
		dimensione.set(6f, 6f);
		regMonetaBlu = Oggetti.getInstance().monetaBlu.regMonetaBlu;
		//settiamo i bordi per riconoscere la collisione tra gli oggetti
		bounds.set(0,0,dimensione.x,dimensione.y);
		collected=false;	//la moneta non � stata ancora presa
	}
	
	public void render(SpriteBatch batch){
		if(collected){
			posizione.set(posizione.x+MathUtils.random(1200,2000),MathUtils.random(0, 50));
			init();
		}
		TextureRegion reg=null;
		reg=regMonetaBlu;
		batch.draw(reg.getTexture(),posizione.x, posizione.y,origine.x,
				origine.y,dimensione.x, dimensione.y,scala.x, scala.y,
				rotazione, reg.getRegionX(), reg.getRegionY(), 
				reg.getRegionWidth(),reg.getRegionHeight(), false, false);
	}
	
	public int getScore(){
		// ogni moneta blu presa aumenta il punteggio di 20 punti
		return 20;
	}
	
	@Override
	public void update(float deltaTime){
		super.update(deltaTime);
	}
	
}
