package progetto.SMissile.game;

import progetto.SMissile.game.oggetti.Bomba;
import progetto.SMissile.game.oggetti.Calamita;
import progetto.SMissile.game.oggetti.MonetaBlu;
import progetto.SMissile.game.oggetti.MonetaDOro;
import progetto.SMissile.game.oggetti.Nemico;
import progetto.SMissile.game.oggetti.Scudo;
import progetto.SMissile.game.oggetti.Stella;
import progetto.SMissile.screens.DirectedGame;
import progetto.SMissile.screens.GameOverScreen;
import progetto.SMissile.screens.MenuScreen;
import progetto.SMissile.util.AudioManager;
import progetto.SMissile.util.CameraHelper;
import progetto.SMissile.util.Constants;
import progetto.SMissile.util.GamePreferences;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class WorldController extends InputAdapter {
	private static final String TAG = WorldController.class.getName();
	
	public CameraHelper cameraHelper;
	public Livello livello;
	public boolean gameOver;
	public int score;
	public float scoreVisual;
	private float timeLeftGameOverDelay;
	private DirectedGame game;
	public int metriPercorsi;
	public int diff;
	private GamePreferences prefs;
	
	//rettangoli per gestire le collisioni tra oggetti
	private Rectangle r1 = new Rectangle();	//missile
	private Rectangle r2 = new Rectangle();	//monete
	private Rectangle r3 = new Rectangle();	//nemici
	private Rectangle r4 = new Rectangle(); //stelle
	private Rectangle r5 = new Rectangle(); //bombe
	private Rectangle r6 = new Rectangle(); //calamite
	private Rectangle r7 = new Rectangle(); //scudi
	private Rectangle r8 = new Rectangle(); //moneteBlu
	
	public void initLivello(){
		score=0;
		scoreVisual = score;
		diff = 1;
		livello=new Livello();
		prefs = GamePreferences.instance;
		prefs.load();
		metriPercorsi = (int)livello.missile.posizione.x - 40;	//40 è la pos iniziale del missile
		cameraHelper.setTarget(livello.missile);
	}
	
	public void backToMenu(){
		//switch al menu principale
		game.setSchermo(new MenuScreen(game));
	}
	
	public WorldController(DirectedGame game){
		this.game=game;
		init();
	}
	
	private void init(){
		Gdx.input.setInputProcessor(this);
		cameraHelper = new CameraHelper();
		gameOver=false;
		timeLeftGameOverDelay=0;
		initLivello();
	}
	
	public void update(float deltaTime){
		if(isGameOver()){
			timeLeftGameOverDelay -= deltaTime;
			if(timeLeftGameOverDelay < 0)
				// '-10' metri perchè il missile parte da lì
				game.setSchermo(new GameOverScreen(game, scoreVisual, -10 + (metriPercorsi)));
		} else {
				handleInputGame(deltaTime);
		}
		livello.update(deltaTime);
		metriPercorsi = ((int)livello.missile.posizione.x)/5;
		livello.missile.setDifficolta(metriPercorsi);
		testCollisioni();
		cameraHelper.update(deltaTime);
		
		if(scoreVisual < score)
			scoreVisual = Math.min(score, scoreVisual + 250 * deltaTime);
	}
	
	private void handleInputGame(float deltaTime){
		livello.missile.velocity.y = -livello.missile.terminalVelocity.y;	//gravità
		if (Gdx.app.getType() == ApplicationType.Desktop){	
			//movimento missile
			if(Gdx.input.isKeyPressed(Keys.UP)) {
				livello.missile.velocity.y=livello.missile.terminalVelocity.y;
			}
		}//desktop
		if( Gdx.input.isKeyPressed(Keys.ESCAPE) || Gdx.input.isKeyPressed(Keys.HOME) || Gdx.input.isKeyPressed(Keys.BACK) ){
			onPause();
		}
		//per android
		if(Gdx.input.isTouched()){
			livello.missile.velocity.y=livello.missile.terminalVelocity.y;
		}
	}
	
	private void onPause() {
		game.pause();
	}
	
	private void collisioneMissileMoneta(MonetaDOro m){
		m.collected=true;
		AudioManager.instance.play(Oggetti.getInstance().sounds.moneta);
		score+=m.getScore();
		Gdx.app.log(TAG, "Moneta raccolta!");
	}
	
	private void collisioneMissileMonetaBlu(MonetaBlu m){
		m.collected=true;
		AudioManager.instance.play(Oggetti.getInstance().sounds.moneta);
		score+=m.getScore();
		Gdx.app.log(TAG, "Moneta raccolta!");
	}
	
	private void collisioneMissileStella(Stella s){
		s.presa=true;
		s.posizione.x=MathUtils.random(1200,1800);
		s.posizione.y=MathUtils.random(0,50);
		AudioManager.instance.play(Oggetti.getInstance().sounds.stella);
		livello.missile.hasPowerUp = true;
		if(prefs.livelloStella == 1)
			livello.missile.timeLeftPowerUp = Constants.DURATA_POWER_UP_3;
		else if(prefs.livelloStella == 2)
			livello.missile.timeLeftPowerUp = Constants.DURATA_POWER_UP_5;
		else if(prefs.livelloStella == 3)
			livello.missile.timeLeftPowerUp = Constants.DURATA_POWER_UP_7;
		else if(prefs.livelloStella == 4)
			livello.missile.timeLeftPowerUp = Constants.DURATA_POWER_UP_9;
		else if(prefs.livelloStella == 5)
			livello.missile.timeLeftPowerUp = Constants.DURATA_POWER_UP_10;
		//in ogni caso
		if(livello.missile.velocity.x < 80)				//da considerare se la velocità non aumenta
			livello.missile.velocity.x += 15;
	}
	
	private void collisioneMissileCalamita(Calamita c){
		c.presa = true;
		c.posizione.x=MathUtils.random(1000,1600);
		c.posizione.y=MathUtils.random(0,50);
		AudioManager.instance.play(Oggetti.getInstance().sounds.calamita);
		livello.missile.hasCalamita = true;
		if(prefs.livelloCalamita == 1)
			livello.missile.timeLeftCalamita = Constants.DURATA_POWER_UP_3;
		else if(prefs.livelloCalamita == 2)
			livello.missile.timeLeftCalamita = Constants.DURATA_POWER_UP_5;
		else if(prefs.livelloCalamita == 3)
			livello.missile.timeLeftCalamita = Constants.DURATA_POWER_UP_7;
		else if(prefs.livelloCalamita == 4)
			livello.missile.timeLeftCalamita = Constants.DURATA_POWER_UP_9;
		else if(prefs.livelloCalamita == 5)
			livello.missile.timeLeftCalamita = Constants.DURATA_POWER_UP_10;
	}
	
	private void collisioneMissileScudo(Scudo s){
		s.preso = true;
		s.posizione.x=MathUtils.random(1000,1600);
		s.posizione.y=MathUtils.random(0,50);
		AudioManager.instance.play(Oggetti.getInstance().sounds.scudo);
		livello.missile.hasScudo = true;
		if(prefs.livelloScudo == 1)
			livello.missile.timeLeftScudo = Constants.DURATA_POWER_UP_3;
		else if(prefs.livelloScudo == 2)
			livello.missile.timeLeftScudo = Constants.DURATA_POWER_UP_5;
		else if(prefs.livelloScudo == 3)
			livello.missile.timeLeftScudo = Constants.DURATA_POWER_UP_7;
		else if(prefs.livelloScudo == 4)
			livello.missile.timeLeftScudo = Constants.DURATA_POWER_UP_9;
		else if(prefs.livelloScudo == 5)
			livello.missile.timeLeftScudo = Constants.DURATA_POWER_UP_10;
	
	}
	
	private void collisioneMissileBomba(Bomba b){
		b.presa = true;
		livello.missile.esplodi = true;
		AudioManager.instance.play(Oggetti.getInstance().sounds.esplosione);
		//GAME OVER
		livello.missile.velocity.x=0;
		livello.missile.velocity.y=0;
		gameOver=true;
		timeLeftGameOverDelay = Constants.TIME_DELAY_GAME_OVER;
		if(timeLeftGameOverDelay < 0)
			init();
		Gdx.app.log(TAG, "Bomba toccata!");
	}
	
	private void collisioneMissileNemico(Nemico n){
		n.urtato=true;
		AudioManager.instance.play(Oggetti.getInstance().sounds.esplosione);
		livello.missile.esplodi=true;
		//GAME OVER
		livello.missile.velocity.x=0;
		livello.missile.velocity.y=0;
		gameOver=true;
		timeLeftGameOverDelay = Constants.TIME_DELAY_GAME_OVER;
		if(timeLeftGameOverDelay < 0)
			init();
		Gdx.app.log(TAG, "Nemico toccato!");
	}
	
	private void testCollisioni(){
		if(isGameOver())
			return;
		
		r1.set(	livello.missile.posizione.x,
				livello.missile.posizione.y,
				livello.missile.bounds.width,
				livello.missile.bounds.height );

		//test collisione: missile-moneta
		for(MonetaDOro m:livello.monete){
			if(m.collected) continue;
			r2.set(m.posizione.x,m.posizione.y,m.bounds.width,m.bounds.height);
			if(!r1.overlaps(r2)) continue;
			collisioneMissileMoneta(m);
			break;
		}

		//test collisione: missile-monetaBlu
		for(MonetaBlu m:livello.moneteBlu){
			if(m.collected) continue;
			r8.set(m.posizione.x,m.posizione.y,m.bounds.width,m.bounds.height);
			if(!r1.overlaps(r8)) continue;
			collisioneMissileMonetaBlu(m);
			break;
		}
		
		//test collisione: missile-stella
		for(Stella s : livello.stelle){
			if(s.presa) continue;
			r4.set(s.posizione.x, s.posizione.y, s.bounds.width, s.bounds.height);
			if(!r1.overlaps(r4)) continue;
			collisioneMissileStella(s);
			break;
		}
		
		//test collisione: missile-calamita
		for(Calamita c : livello.calamite){
			if(c.presa) continue;
			r6.set(c.posizione.x, c.posizione.y, c.bounds.width, c.bounds.height);
			if(!r1.overlaps(r6)) continue;
			collisioneMissileCalamita(c);
			break;
		}
	
		//se c'è la calamita, raccoglie monete limitrofe
		if(livello.missile.hasCalamita()){
			for(MonetaDOro m : livello.monete){
				if((m.posizione.dst(livello.missile.posizione) < 15 ))
						m.posizione.lerp(livello.missile.posizione, 0.25f);
			}
		}
		
		//test collisione: missile-calamita
		for(Scudo s : livello.scudi){
			if(s.preso) continue;
				r7.set(s.posizione.x, s.posizione.y, s.bounds.width, s.bounds.height);
			if(!r1.overlaps(r7)) continue;
			collisioneMissileScudo(s);
			break;
		}

		// Test collisione: missile-nemico
		for (Nemico n : livello.nemici) {
			//se hai la stella, distruggi i nemici e +20
			if(livello.missile.hasPowerUp()){
				if(n.urtato) continue;
				r3.set(n.posizione.x,n.posizione.y,n.bounds.width,n.bounds.height);
				if (!r1.overlaps(r3)) continue;
				n.urtato=true;
				//aumentare punteggio
				score+=20;
				AudioManager.instance.play(Oggetti.getInstance().sounds.esplosione2);
			return;
			}
			if(livello.missile.hasScudo()){
				if(n.urtato) continue;
				r3.set(n.posizione.x,n.posizione.y,n.bounds.width,n.bounds.height);
				if (!r1.overlaps(r3)) continue;
				n.urtato=true;
				AudioManager.instance.play(Oggetti.getInstance().sounds.esplosione2);
			return;
			}
			//se non hai nè stella nè scudo
			if (n.urtato) continue;
			r3.set(n.posizione.x,n.posizione.y,n.bounds.width,n.bounds.height);
			if (!r1.overlaps(r3)) continue;
			collisioneMissileNemico(n);
			break;
		}

		//test collisione: missile-bomba
		for(Bomba b : livello.bombe){
			//se hai la stella, distruggi bombe e +50
			if(livello.missile.hasPowerUp()){
				if(b.presa) continue;
				r5.set(b.posizione.x, b.posizione.y, b.bounds.width, b.bounds.height);
				if (!r1.overlaps(r5)) continue;
				b.presa=true;
				b.posizione.x=MathUtils.random(400,1000);
				b.posizione.y=MathUtils.random(0,50);
				//aumenta punteggio
				score+=50;
				AudioManager.instance.play(Oggetti.getInstance().sounds.esplosione2);
			return;
			}
			if(livello.missile.hasScudo()){
				if(b.presa) continue;
				r5.set(b.posizione.x, b.posizione.y, b.bounds.width, b.bounds.height);
				if (!r1.overlaps(r5)) continue;
				b.presa=true;
				b.posizione.x=MathUtils.random(400,1000);
				b.posizione.y=MathUtils.random(0,50);
				AudioManager.instance.play(Oggetti.getInstance().sounds.esplosione2);
			return;
			}
			
			//se non hai nè stella nè scudo
			if(livello.missile.hasPowerUp()) return;
			if(b.presa) continue;
			r5.set(b.posizione.x, b.posizione.y, b.bounds.width, b.bounds.height);
			if(!r1.overlaps(r5)) continue;
			collisioneMissileBomba(b);
			break;
		}
	}

	public boolean isGameOver(){
		return gameOver;
	}
	
}
