package progetto.SMissile.game.oggetti;

import progetto.SMissile.game.Oggetti;
import progetto.SMissile.util.Constants;
import progetto.SMissile.util.GamePreferences;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Missile extends AbstractGameObject{
	
	public static final String TAG = Missile.class.getName();
	
	private TextureRegion regMissile;
	public boolean hasPowerUp;
	public boolean hasCalamita;
	public boolean hasScudo;
	public float timeLeftPowerUp;
	public float timeLeftCalamita;
	public float timeLeftScudo;
	public boolean esplodi;
	private GamePreferences prefs;
	
	
	public ParticleEffect fireParticles = new ParticleEffect();
	public ParticleEffect expParticles = new ParticleEffect();
	public ParticleEffect fireStellaParticles = new ParticleEffect();
	public ParticleEffect calamitaParticles = new ParticleEffect();
	
	public Missile () {
		init();
	}
		
	public void init () {
		dimensione.set(6f, 4f);
		regMissile = Oggetti.getInstance().missile.regMissile;
		// centriamo l'immagine nel centro dell'oggetto
		origine.set(dimensione.x / 2, dimensione.y / 2);
		// settiamo i bordi per riconoscere le collisioni con gli altri oggetti
		bounds.set(0, 0, dimensione.x, dimensione.y);
		// settiamo tutti gli altri valori
		terminalVelocity.set(25f, 25f);
		friction.set(0.0f, 0.0f);
		acceleration.set(0.0f, 0.0f);
		hasPowerUp = false;
		hasCalamita = false;
		hasScudo = false;
		timeLeftPowerUp = 0;
		timeLeftCalamita = 0;
		timeLeftScudo = 0;
		esplodi = false;
		
		// carichiamo le particles
		fireParticles.load(Gdx.files.internal("particles/fire.pfx"), Gdx.files.internal("particles"));
		expParticles.load(Gdx.files.internal("particles/explosion.pfx"), Gdx.files.internal("particles"));
		fireStellaParticles.load(Gdx.files.internal("particles/fireStella.pfx"), Gdx.files.internal("particles"));
		calamitaParticles.load(Gdx.files.internal("particles/calamita.pfx"), Gdx.files.internal("particles"));
	}
	
	@Override
	public void update (float deltaTime) {
		// per dare un senso di movimento al missile
		super.update(deltaTime);
		
		// update delle particles
		fireParticles.update(deltaTime);
		expParticles.update(deltaTime);
		fireStellaParticles.update(deltaTime);
		calamitaParticles.update(deltaTime);

		if(timeLeftPowerUp > 0){
			timeLeftPowerUp -= deltaTime;
			if(timeLeftPowerUp < 0){
				//disabilitiamo la stella
				timeLeftPowerUp = 0;
				setPowerUp(false);
				fireStellaParticles.allowCompletion();
			}
		}		
		if(timeLeftCalamita > 0){
			timeLeftCalamita -= deltaTime;
			if(timeLeftCalamita < 0){
				//disabilita calamita
				timeLeftCalamita = 0;
				setCalamita(false);
				calamitaParticles.allowCompletion();
			}
		}
		if(timeLeftScudo > 0){
			timeLeftScudo -= deltaTime;
			if(timeLeftScudo < 0){
				//disabilita calamita
				timeLeftScudo = 0;
				setScudo(false);
			}
		}
		// controlli per settare le varie particles
		if(hasPowerUp()){
			fireParticles.allowCompletion();
			fireStellaParticles.setPosition(posizione.x, posizione.y+3f);
			fireStellaParticles.start();
		}
		if(hasCalamita()){
			calamitaParticles.setPosition(posizione.x+2.5f, posizione.y+1.75f);
			calamitaParticles.start();
		}
		if(esplodi){
			expParticles.setPosition(posizione.x, posizione.y);
			expParticles.start();
		}
	}
	
	public void setDifficolta(int metriPercorsi){
		if(velocity.x == 0 || hasPowerUp())
			return;
		if(metriPercorsi > 50 && metriPercorsi < 250)
			velocity.x += 5;
		if(metriPercorsi > 250 && metriPercorsi < 500)
			velocity.x += 8;
		if(metriPercorsi > 500 && metriPercorsi < 700)
			velocity.x += 10;
		if(metriPercorsi > 700 && metriPercorsi < 1000)
			velocity.x += 12;
		if(metriPercorsi > 1000 && metriPercorsi < 1200)
			velocity.x += 15;
		if(metriPercorsi > 1200 && metriPercorsi < 1500)
			velocity.x += 17;
		if(metriPercorsi > 1500 && metriPercorsi < 2500)
			velocity.x += 20;
		if(metriPercorsi > 2500 && metriPercorsi < 5000)
			velocity.x += 25;
		if(metriPercorsi > 5000)
			velocity.x += 30;
	}
	
	public void setCalamita(boolean presa){
		hasCalamita = presa;
		if(presa){
			if(prefs.livelloCalamita == 1)
				timeLeftCalamita = Constants.DURATA_POWER_UP_3;
			else if(prefs.livelloCalamita == 2)
				timeLeftCalamita = Constants.DURATA_POWER_UP_5;
			else if(prefs.livelloCalamita == 3)
				timeLeftCalamita = Constants.DURATA_POWER_UP_7;
			else if(prefs.livelloCalamita == 4)
				timeLeftCalamita = Constants.DURATA_POWER_UP_9;
			else if(prefs.livelloCalamita == 5)
				timeLeftCalamita = Constants.DURATA_POWER_UP_10;
		}
	}

	public void setPowerUp(boolean preso){	//stella
		hasPowerUp = preso;
		if(preso){
			if(prefs.livelloStella == 1)
				timeLeftPowerUp = Constants.DURATA_POWER_UP_3;
			else if(prefs.livelloStella == 2)
				timeLeftPowerUp = Constants.DURATA_POWER_UP_5;
			else if(prefs.livelloStella == 3)
				timeLeftPowerUp = Constants.DURATA_POWER_UP_7;
			else if(prefs.livelloStella == 4)
				timeLeftPowerUp = Constants.DURATA_POWER_UP_9;
			else if(prefs.livelloStella == 5)
				timeLeftPowerUp = Constants.DURATA_POWER_UP_10;
		}
	}
	
	public void setScudo(boolean preso){
		hasScudo = preso;
		if(preso){
			if(prefs.livelloScudo == 1)
				timeLeftScudo = Constants.DURATA_POWER_UP_3;
			else if(prefs.livelloScudo == 2)
				timeLeftScudo = Constants.DURATA_POWER_UP_5;
			else if(prefs.livelloScudo == 3)
				timeLeftScudo = Constants.DURATA_POWER_UP_7;
			else if(prefs.livelloScudo == 4)
				timeLeftScudo = Constants.DURATA_POWER_UP_9;
			else if(prefs.livelloScudo == 5)
				timeLeftScudo = Constants.DURATA_POWER_UP_10;
		}
	}
	
	public boolean hasCalamita(){
		return hasCalamita && timeLeftCalamita > 0;
	}
	
	public boolean hasPowerUp(){
		return hasPowerUp && timeLeftPowerUp > 0;
	}
		
	public boolean hasScudo(){
		return hasScudo && timeLeftScudo > 0;
	}
	
	@Override
	protected void updateMotionX (float deltaTime) {
		if(velocity.x != 0){
			if(!hasPowerUp){
				// '+1.65' per posizionare la fiamma al centro del bordo posteriore del missile
				fireParticles.setPosition(-1.5f+posizione.x + dimensione.x/2, posizione.y + 1.65f);
				fireParticles.start();
			}
		}
		if(velocity.x == 0){
			fireParticles.allowCompletion();
		}
	}

	@Override
	protected void updateMotionY (float deltaTime) {
		if (velocity.y != 0) {
			// Applichiamo l'attrito(friction)
			if (velocity.y > 0) {
				velocity.y = Math.max(velocity.y - friction.y * deltaTime, 0);
			} else {
				velocity.y = Math.min(velocity.y + friction.y * deltaTime, 0);
			}
		}
		if(posizione.y > 49) //ALTEZZA_MAX_CAM
			posizione.y = 49;
		if(posizione.y < 0)
			posizione.y = 0;
	}	
	
	@Override
	public void render(SpriteBatch batch) {
		
		//disegniamo le particles
		fireParticles.draw(batch);
		expParticles.draw(batch);
		fireStellaParticles.draw(batch);
		calamitaParticles.draw(batch);
		
		if (esplodi) return;
		TextureRegion reg = null;
		
		// Aumenta dimensioni missile se c'è powerUp
		if (hasPowerUp)
			dimensione.set(8f, 5.5f);
		else{
			//qui nn c'è powerUp quindi resetto dimensione e velocità
			velocity.x = 25f;
			setDifficolta((int)(posizione.x - 10));
			dimensione.set(5.5f, 4f);
		}
		
		// disegniamo l'immagine del missile
		reg = regMissile;
		batch.draw(reg.getTexture(),
				posizione.x, posizione.y,origine.x, origine.y,
				dimensione.x, dimensione.y,	scala.x, scala.y,rotazione,
				reg.getRegionX(), reg.getRegionY(),
				reg.getRegionWidth(), reg.getRegionHeight(), false, false);
		// Reset colori di default
		batch.setColor(1, 1, 1, 1);
	}

}
