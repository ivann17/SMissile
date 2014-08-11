package progetto.SMissile.game.oggetti;

import progetto.SMissile.game.Oggetti;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;


public class Nuvole extends AbstractGameObject{
	private final int numNuvole;
	private Array<TextureRegion> regNuvole;
	private Array<Nuvola> nuvole;
	
	private class Nuvola extends AbstractGameObject{
		private TextureRegion regNuvola;
		
		public Nuvola(){ }

		@Override
		public void render(SpriteBatch batch){
			TextureRegion reg = regNuvola;
			batch.draw(reg.getTexture(),posizione.x+origine.x,
					posizione.y+origine.y,origine.x,origine.y,
					dimensione.x,dimensione.y,scala.x,scala.y,
					rotazione,reg.getRegionX(),reg.getRegionY(),
					reg.getRegionWidth(),reg.getRegionHeight(),
					false,false);
		}
	}//class Nuvola
	
	public Nuvole(int numNuvole){
		this.numNuvole = numNuvole;
		init();
	}

	private void init(){
		nuvole=new Array<Nuvola>(numNuvole);
		regNuvole=new Array<TextureRegion>();
		regNuvole.add(Oggetti.getInstance().decorazioneLivello.nuvola01);
		regNuvole.add(Oggetti.getInstance().decorazioneLivello.nuvola02);
		regNuvole.add(Oggetti.getInstance().decorazioneLivello.nuvola03);
		for(int i=0; i<numNuvole; i++){
			Nuvola n= new Nuvola();
			n.dimensione.set(18f,9f);
			n.regNuvola=regNuvole.random();
			n.posizione.x=MathUtils.random(50,80);;
			n.posizione.y=MathUtils.random(0,50);
			//speed
	        Vector2 speed = new Vector2();
	        speed.x += MathUtils.random(8.0f,16.0f);
	        n.terminalVelocity.set(speed);
	        speed.x =-speed.x;    //verso sinistra
	        n.velocity.set(speed);
			nuvole.add(n);
		}
	}
	
	@Override
	public void render (SpriteBatch batch){
		for(Nuvola n : nuvole)
			n.render(batch);
	}
	
	public void update(float deltaTime, float posMissile){
		for(Nuvola nuv : nuvole){
			nuv.update(deltaTime);
			if(nuv.posizione.x < posMissile-20){
				nuv.posizione.x = MathUtils.random(100,140) + posMissile;
				nuv.posizione.y = MathUtils.random(-5,50);
				nuv.velocity.x = -(MathUtils.random(8,16));
			}
		}
	}
	
}
