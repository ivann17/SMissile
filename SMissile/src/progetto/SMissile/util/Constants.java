package progetto.SMissile.util;

public class Constants {
	
	//lunghezza GUI
	public static final float VIEWPORT_GUI_WIDTH = 900f;
	//larghezza GUI
	public static final float VIEWPORT_GUI_HEIGHT = 580f;
	//tempo dopo game-over
	public static final float TIME_DELAY_GAME_OVER = 3;
	//durata power-up stella e calamita				livello
	public static final int DURATA_POWER_UP_3 = 4;		//1
	public static final int DURATA_POWER_UP_5 = 6;		//2
	public static final int DURATA_POWER_UP_7 = 8;		//3	
	public static final int DURATA_POWER_UP_9 = 10;		//4	
	public static final int DURATA_POWER_UP_10 = 11;	//5	
	//percorso delle immagini
	public static final String TEXTURE_OGGETTI_ATLAS = "immagini/supermissile.pack";
	public static final String TEXTURE_ATLAS_UI = "immagini/supermissile-ui.pack";
	public static final String TEXTURE_ATLAS_LIBGDX_UI = "immagini/uiskin.atlas";
	//percorso dei file di skin
	public static final String SKIN_LIBGDX_UI = "immagini/uiskin.json";
	public static final String SKIN_SUPERMISSILE_UI = "immagini/supermissile-ui.json";
	//nome file preferenze di gioco
	public static final String PREFERENCES = "preferences";
	//nome file del punteggio record
	public static final String RECORD = "record";
	//nome file delle monete raccolte
	public static final String MONETE_RACCOLTE = "moneteRaccolte";
	//nome file delle difficoltà
	public static final String LIVELLO_CALAMITA = "difficoltaCalamita";
	public static final String LIVELLO_STELLA = "difficoltaStella";
	public static final String LIVELLO_SCUDO = "difficoltaScudo";
}
