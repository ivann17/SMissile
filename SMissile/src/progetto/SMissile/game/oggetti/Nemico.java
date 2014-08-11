package progetto.SMissile.game.oggetti;

import progetto.SMissile.game.Oggetti;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Nemico extends AbstractGameObject{

	private TextureRegion regNemico;
    public boolean urtato;  //per le collisioni
    public Array<Nemico> nemici;
        
    public Nemico(){
       	init();
    }
        
    public void init(){
    	regNemico=Oggetti.getInstance().nemico.regNemico;
    	dimensione.set(4.3f, 3.8f);
    	nemici=new Array<Nemico>();
        //settiamo i bordi per riconoscere la collisione tra gli oggetti
        bounds.set(0,0,dimensione.x,dimensione.y);
        //movimento nemici
        Vector2 speed = new Vector2();
        speed.x += MathUtils.random(6.0f,18.0f);
        speed.y += MathUtils.random(0.5f, 2.5f);
        terminalVelocity.set(speed);
        speed.x = -speed.x;        //verso sinistra
        speed.y = speed.y*MathUtils.random(-1,1);
        velocity.set(speed);
        urtato=false;   //il nemico non è stato ancora toccato
    }

    @Override
    public void render(SpriteBatch batch) {
       	if(urtato){
      		posizione.set(posizione.x+MathUtils.random(90,150),MathUtils.random(0, 50));
       		init();
       	}
           	TextureRegion reg = null;
                reg=regNemico;
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
