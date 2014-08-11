package progetto.SMissile.game.oggetti;

import progetto.SMissile.game.Oggetti;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Montagne extends AbstractGameObject{
	
	private final int NUM_MONTAGNE=2;
	private Array<Montagna>montagne;
	
	private class Montagna extends AbstractGameObject{
		private TextureRegion regMontagna;
		
		public Montagna(){}
		
		@Override
		public void render(SpriteBatch batch){
			regMontagna=Oggetti.getInstance().decorazioneLivello.montagna;
			TextureRegion reg = regMontagna;
			
			batch.draw(reg.getTexture(), posizione.x, posizione.y, origine.x,
					origine.y, dimensione.x, dimensione.y, scala.x, scala.y,
					rotazione,reg.getRegionX(),reg.getRegionY(),
					reg.getRegionWidth(),reg.getRegionHeight(),false,false);
		}
	}
	
	public Montagne(){
		init();
	}
	
	private void init(){
		montagne = new Array<Montagna>(NUM_MONTAGNE);
		for(int i=0; i<NUM_MONTAGNE; i++){
			Montagna m = new Montagna();
			m.dimensione.set(170,55);
			m.posizione.set(m.dimensione.x *i, -9);
			//speed
	        Vector2 speed = new Vector2();
	        speed.x = -2;
			montagne.add(m);
		}
	}
	
	@Override
	public void render (SpriteBatch batch){
		for(Montagna m : montagne)
			m.render(batch);
	}
	
	public void update(float deltaTime, float posMissile){
		for(Montagna mont : montagne){
			mont.update(deltaTime);
			if(mont.posizione.x + mont.dimensione.x < posMissile-10){
				mont.posizione.x  += mont.dimensione.x *2;
			}
		}
	}
	
}
