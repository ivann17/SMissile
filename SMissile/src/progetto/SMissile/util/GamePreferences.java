package progetto.SMissile.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.MathUtils;

public class GamePreferences {
	public static final String TAG = GamePreferences.class.getName();
	
	public static final GamePreferences instance = new GamePreferences();
	
	public boolean sound;
	public boolean music;
	public float volSound;
	public float volMusic;
	public float record;
	public float moneteRaccolte;
	public float livelloCalamita;
	public float livelloStella;
	public float livelloScudo;
	private Preferences prefs;
	
	private GamePreferences () {
		prefs = Gdx.app.getPreferences(Constants.PREFERENCES);
	}
	
	/*
	vengono caricati e settati tutti i
	valori delle preferenze di gioco
	*/
	public void load () {
		livelloCalamita = prefs.getFloat("livelloCalamita", 1.0f);
		livelloStella = prefs.getFloat("livelloStella", 1.0f);
		livelloScudo = prefs.getFloat("livelloScudo", 1.0f);
		moneteRaccolte = prefs.getFloat("moneteRaccolte", 0.0f);
		record = prefs.getFloat("record",0.0f);
		sound = prefs.getBoolean("sound", true);
		music = prefs.getBoolean("music", true);
		volSound = MathUtils.clamp(prefs.getFloat("volSound", 0.5f), 0.0f, 1.0f);
		volMusic = MathUtils.clamp(prefs.getFloat("volMusic", 0.5f), 0.0f, 1.0f);
	}
	
	/*
	vengono aggiornati e salvati tutti i
	valori delle preferenze di gioco
	*/
	public void save () {
		prefs.putFloat("livelloCalamita", livelloCalamita);
		prefs.putFloat("livelloStella", livelloStella);
		prefs.putFloat("livelloScudo", livelloScudo);
		prefs.putFloat("moneteRaccolte", moneteRaccolte);
		prefs.putFloat("record", record);
		prefs.putBoolean("sound", sound);
		prefs.putBoolean("music", music);
		prefs.putFloat("volSound", volSound);
		prefs.putFloat("volMusic", volMusic);
		prefs.flush();
	}
	
	//somma monete raccolte
	public void somma(float aggiunta){
		float somma = prefs.getFloat("moneteRaccolte") + aggiunta;
		prefs.putFloat("moneteRaccolte", somma);
		prefs.flush();
	}
	
	//calcolo del record
	public void add(float aggiunta){
		float somma = prefs.getFloat("record") + aggiunta;
		prefs.putFloat("record", somma);
		prefs.flush();
	}
	
}
