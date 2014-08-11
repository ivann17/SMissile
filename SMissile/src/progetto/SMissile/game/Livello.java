package progetto.SMissile.game;

import progetto.SMissile.game.oggetti.Bomba;
import progetto.SMissile.game.oggetti.Calamita;
import progetto.SMissile.game.oggetti.Missile;
import progetto.SMissile.game.oggetti.MonetaBlu;
import progetto.SMissile.game.oggetti.MonetaDOro;
import progetto.SMissile.game.oggetti.Montagne;
import progetto.SMissile.game.oggetti.Nemico;
import progetto.SMissile.game.oggetti.Nuvole;
import progetto.SMissile.game.oggetti.Scudo;
import progetto.SMissile.game.oggetti.Stella;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class Livello {
	public static final String TAG = Livello.class.getName();

	//oggetti e decorazioni
	public Missile missile;
	public Nuvole nuvole;
	public Montagne montagne;
	public Nemico nemico;
	public Array<Scudo> scudi;
	public Array<MonetaDOro> monete;
	public Array<MonetaBlu> moneteBlu;
	public Array<Nemico> nemici;
	public Array<Stella> stelle;
	public Array<Calamita> calamite;
	public Array<Bomba> bombe;
	private final int numCalamite = 1;
	private final int numStelle = 1;
	private final int numScudi = 1;
	private final int numMonete = 4;
	private final int numMoneteBlu = 2;
	private final int numNuvole = 3;
	private final int numNemici = 4;
	private final int numBombe = 4;
	
	public int countdown = 100;
	
	public Livello(){
		init();
	}
	
	private void init(){
		//personaggio principale
		missile=new Missile();
		missile.posizione.set(50, 20);
		//oggetti
		//MONETE
		monete= new Array<MonetaDOro>();
		for(int i=0; i<numMonete; i++)
			monete.add(new MonetaDOro());
		for(MonetaDOro m:monete)
			m.posizione.set(missile.posizione.x + MathUtils.random(80, 140),MathUtils.random(0, 50));
		//MONETE BLU
		moneteBlu= new Array<MonetaBlu>();
		for(int i=0; i<numMoneteBlu; i++)
			moneteBlu.add(new MonetaBlu());
		for(MonetaBlu m:moneteBlu)
			m.posizione.set(missile.posizione.x + MathUtils.random(200, 600),MathUtils.random(0, 50));
		//NEMICI
		nemici= new Array<Nemico>();
		for(int i=0; i<numNemici; i++)
			nemici.add(new Nemico());
		for(Nemico n:nemici)
			n.posizione.set(missile.posizione.x + MathUtils.random(150, 180),MathUtils.random(0, 50));
		//STELLE
		stelle = new Array<Stella>();
		for(int i=0; i<numStelle; i++)
			stelle.add(new Stella());
		for(Stella s : stelle)
			s.posizione.set(missile.posizione.x + MathUtils.random(400, 1500), MathUtils.random(0, 50));
		//CALAMITE
		calamite = new Array<Calamita>();
		for(int i=0; i<numCalamite; i++)
			calamite.add(new Calamita());
		for(Calamita c : calamite)
			c.posizione.set(missile.posizione.x + MathUtils.random(400, 600), MathUtils.random(0, 50));
		//SCUDI
		scudi = new Array<Scudo>();
		for(int i=0; i<numScudi; i++)
			scudi.add(new Scudo());
		for(Scudo s : scudi)
			s.posizione.set(missile.posizione.x + MathUtils.random(400, 600), MathUtils.random(0, 50));
		//BOMBE
		bombe = new Array<Bomba>();
		for(int i=0; i<numBombe; i++)
			bombe.add(new Bomba());
		for(Bomba b : bombe)
			b.posizione.set(missile.posizione.x + MathUtils.random(200, 600), MathUtils.random(0, 50));
		//decorazioni
		montagne = new Montagne();
		nuvole = new Nuvole(numNuvole);
		Gdx.app.debug(TAG, "Livello caricato");
	}

	public void render(SpriteBatch batch){
		// Disegna montagne e nuvole
		montagne.render(batch);
		nuvole.render(batch);
		
		//disegniamo tutti gli oggetti di gioco
		for(MonetaDOro m:monete){
			m.render(batch);
		}
		for(MonetaBlu m:moneteBlu){
			m.render(batch);
		}		
		for(Stella s:stelle){
			s.render(batch);
		}
		for(Calamita c : calamite){
			c.render(batch);
		}		
		for(Scudo s : scudi){
			s.render(batch);
		}	
		for(Bomba b : bombe){
			b.render(batch);
		}
		for(Nemico n:nemici){
			n.render(batch);
		}		
		missile.render(batch);
		if(countdown>0){
			Oggetti.getInstance().fonts.goFont.draw(batch, "Go!", 59, 25);
		}
	}
	
	public void update (float deltaTime) {
		countdown-=deltaTime;
		if(countdown<=0){
			updateNemici(deltaTime);
			updateMontagne(deltaTime);
			updateNuvole(deltaTime);
			updateMonete(deltaTime);
			updateMoneteBlu(deltaTime);
			updateStelle(deltaTime);
			updateCalamite(deltaTime);
			updateBombe(deltaTime);
			updateScudi(deltaTime);
			missile.update(deltaTime);
			for(MonetaDOro m : monete)
				m.update(deltaTime);
			for(Nemico n : nemici)
				n.update(deltaTime);
		countdown=0;
		}
	}

	private void updateStelle(float deltaTime) {
		for(int i=stelle.size-1; i>=0; i--){
			Stella st = stelle.get(i);
			if(st.posizione.x < missile.posizione.x-40){
				st.posizione.x = MathUtils.random(1100,2200) + missile.posizione.x;
				st.posizione.y = MathUtils.random(0,50);
			}
			st.update(deltaTime);
		}
	}
	
	private void updateCalamite(float deltaTime){
		for(int i=calamite.size-1; i>=0; i--){
			Calamita cal = calamite.get(i);
			if(cal.posizione.x < missile.posizione.x-40){
				cal.posizione.x = MathUtils.random(700,1400) + missile.posizione.x;
				cal.posizione.y = MathUtils.random(0,50);
			}					
			cal.update(deltaTime);
		}
	}
	
	private void updateScudi(float deltaTime){
		for(int i=scudi.size-1; i>=0; i--){
			Scudo scu = scudi.get(i);
			if(scu.posizione.x < missile.posizione.x-40){
				scu.posizione.x = MathUtils.random(700,1400) + missile.posizione.x;
				scu.posizione.y = MathUtils.random(0,50);
			}					
			scu.update(deltaTime);
		}
	}
	
	private void updateBombe(float deltaTime){
		for(int i=bombe.size-1; i>=0; i--){
			Bomba bmb = bombe.get(i);
			bmb.update(deltaTime);
			if(bmb.posizione.x < missile.posizione.x-40){
				bmb.posizione.x = MathUtils.random(1000,2000) + missile.posizione.x;
				bmb.posizione.y = MathUtils.random(0,50);
			}
		}
	}

	private void updateMontagne(float deltaTime) {
		montagne.update(deltaTime, missile.posizione.x - 20);
	}

	private void updateMonete(float deltaTime) {
		for(int i=monete.size-1; i>=0; i--){
			MonetaDOro moneta = monete.get(i);
			moneta.update(deltaTime);
			if(moneta.posizione.x < missile.posizione.x-30){
				moneta.posizione.x = MathUtils.random(80,100) + missile.posizione.x;
				moneta.posizione.y = MathUtils.random(0,50);
			}
		}
	}
	
	private void updateMoneteBlu(float deltaTime) {
		for(int i=moneteBlu.size-1; i>=0; i--){
			MonetaBlu moneta = moneteBlu.get(i);
			moneta.update(deltaTime);
			if(moneta.posizione.x < missile.posizione.x-30){
				moneta.posizione.x = MathUtils.random(1000,2000) + missile.posizione.x;
				moneta.posizione.y = MathUtils.random(0,50);
			}
		}
	}
	
	private void updateNuvole(float deltaTime) {
		nuvole.update(deltaTime, missile.posizione.x-30);
	}
	
	private void updateNemici(float deltaTime) {
		for(int i=nemici.size-1; i>=0; i--){
    		Nemico nemico = nemici.get(i);
    		nemico.update(deltaTime);
    		//se esce dallo schermo lo ridisegno
    		if(nemico.posizione.x < missile.posizione.x-30){
    			nemico.posizione.x = MathUtils.random(90,150) + missile.posizione.x;
				nemico.posizione.y = MathUtils.random(-5,50);
				nemico.velocity.x = -(MathUtils.random(8,18));
    		}
    		if(nemico.posizione.y < -10 || nemico.posizione.y > 55){
    			nemico.posizione.x = MathUtils.random(90,150) + missile.posizione.x;
				nemico.posizione.y = MathUtils.random(-5,50);
				nemico.velocity.x = -(MathUtils.random(8,18));
    		}
    		//se il nemico viene urtato
    		if(nemico.urtato){
    			nemico.posizione.x = MathUtils.random(90,150) + missile.posizione.x;
				nemico.posizione.y = MathUtils.random(0,50);
    		}
    	}
	}

}


