package progetto.SMissile.game;

import progetto.SMissile.util.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Disposable;

//equivalente di Assets
public class Oggetti implements Disposable, AssetErrorListener {
	
	public FontOggetti fonts;
	
	public class FontOggetti{
		public final BitmapFont defaultSmall;
		public final BitmapFont defaultNormal;
		public final BitmapFont defaultBig;
		public final BitmapFont gameOverFont;
		public final BitmapFont goFont;
		
		public FontOggetti(){
			defaultSmall = new BitmapFont(Gdx.files.internal("immagini/font.fnt"),true);
			defaultNormal = new BitmapFont(Gdx.files.internal("immagini/font.fnt"),true);
			defaultBig = new BitmapFont(Gdx.files.internal("immagini/font.fnt"),true);
			gameOverFont = new BitmapFont(Gdx.files.internal("immagini/gof.fnt"), true);
			goFont = new BitmapFont(Gdx.files.internal("immagini/gof.fnt"), true);
			
			//set dimensione font
			defaultSmall.setScale(0.2f);
			defaultNormal.setScale(0.35f);
			defaultBig.setScale(0.5f);
			gameOverFont.setScale(1.75f);
			goFont.setScale(0.15f, -0.15f);
			
			//abilita linear texture
			defaultSmall.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
			defaultNormal.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
			defaultBig.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
			gameOverFont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
			goFont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
		
	}
	
	public static final String TAG = Oggetti.class.getName();
	private static Oggetti instance = null;
	private AssetManager managerOggetto;
	public AssetSounds sounds;
	public AssetMusic music;
	
	public OggettoMissile missile;
	public OggettoMoneta moneta;
	public OggettoMonetaBlu monetaBlu;
	public OggettoNemico nemico;
	public OggettoStella stella;
	public OggettoBomba bomba;
	public OggettoCalamita calamita;
	public OggettoScudo scudo;
	public DecorazioneLivello decorazioneLivello;
	
	public class AssetSounds {
		public final Sound moneta;
		public final Sound esplosione;
		public final Sound esplosione2;
		public final Sound stella;
		public final Sound turbo;
		public final Sound calamita;
		public final Sound scudo;
	
		public AssetSounds (AssetManager am) {
			scudo = am.get("sounds/scudo.wav", Sound.class);
			moneta = am.get("sounds/moneta.wav", Sound.class);
			esplosione = am.get("sounds/esplosione.wav", Sound.class);
			stella = am.get("sounds/stella.wav", Sound.class);
			turbo = am.get("sounds/turbo.wav", Sound.class);
			esplosione2 = am.get("sounds/esplosione2.wav", Sound.class);
			calamita = am.get("sounds/calamita.wav", Sound.class);
		}
	}
		
	public class AssetMusic {
		public final Music musica_sottofondo;
		
		public AssetMusic (AssetManager am) {
			musica_sottofondo = am.get("music/musica_sottofondo.mp3", Music.class);
		}
	}
	
	private Oggetti(){
		init(new AssetManager());
	}
	
	public static Oggetti getInstance(){
		if(instance==null)
			instance=new Oggetti();
		return instance;
	}
	
	public void init(AssetManager managerOggetto){
		this.managerOggetto = managerOggetto;
		managerOggetto.setErrorListener(this);
		managerOggetto.load(Constants.TEXTURE_OGGETTI_ATLAS,TextureAtlas.class);
		//carichiamo i suoni
		managerOggetto.load("sounds/moneta.wav", Sound.class);
		managerOggetto.load("sounds/esplosione.wav", Sound.class);
		managerOggetto.load("sounds/esplosione2.wav", Sound.class);
		managerOggetto.load("sounds/stella.wav", Sound.class);
		managerOggetto.load("sounds/turbo.wav", Sound.class);
		managerOggetto.load("sounds/calamita.wav", Sound.class);
		managerOggetto.load("sounds/scudo.wav", Sound.class);
		//carichiamo la musica
		managerOggetto.load("music/musica_sottofondo.mp3", Music.class);
		managerOggetto.finishLoading();
		Gdx.app.debug(TAG, "# di oggetti caricati: "+managerOggetto.getAssetNames().size);
		for(String s : managerOggetto.getAssetNames())
			Gdx.app.debug(TAG, "Oggetto: "+s);
		TextureAtlas atlas = managerOggetto.get(Constants.TEXTURE_OGGETTI_ATLAS);
		for(Texture t: atlas.getTextures())
			t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		//creiamo gli oggetti
		fonts = new FontOggetti();
		missile = new OggettoMissile(atlas);
		moneta = new OggettoMoneta(atlas);
		monetaBlu = new OggettoMonetaBlu(atlas);
		nemico = new OggettoNemico(atlas);
		stella = new OggettoStella(atlas);
		bomba = new OggettoBomba(atlas);
		calamita = new OggettoCalamita(atlas);
		scudo = new OggettoScudo(atlas);
		decorazioneLivello = new DecorazioneLivello(atlas);
		sounds = new AssetSounds(managerOggetto);
		music = new AssetMusic(managerOggetto);

	}
	
	@SuppressWarnings("rawtypes")
	public void error(String fileName, Class type, Throwable throwable) {
		Gdx.app.error(TAG, "Impossibile caricare l'oggetto "+fileName,(Exception)throwable);
	}

	@Override
	public void dispose() {
		managerOggetto.dispose();
		fonts.defaultSmall.dispose();
		fonts.defaultNormal.dispose();
		fonts.gameOverFont.dispose();
		fonts.goFont.dispose();
	}

	//oggetti
	
	public class OggettoMissile{
		public final AtlasRegion regMissile;
		public OggettoMissile(TextureAtlas atlas){
			regMissile = atlas.findRegion("missile");
		}
	}//Missile
		
	public class OggettoMoneta{
		public final AtlasRegion regMoneta;
		public OggettoMoneta(TextureAtlas atlas){
			regMoneta = atlas.findRegion("monetina");
		}
	}//Moneta
	
	public class OggettoMonetaBlu{
		public final AtlasRegion regMonetaBlu;
		public OggettoMonetaBlu(TextureAtlas atlas){
			regMonetaBlu = atlas.findRegion("monetina_blu");
		}
	}//MonetaBlu
	
	public class OggettoStella{
		public final AtlasRegion regStella;
		public OggettoStella(TextureAtlas atlas){
			regStella = atlas.findRegion("stella");
		}
	}//Stella
	
	public class OggettoCalamita{
		public final AtlasRegion regCalamita;
		public OggettoCalamita(TextureAtlas atlas){
			regCalamita = atlas.findRegion("calamita");
		}
	}//Calamita
	
	public class OggettoNemico{
		public final AtlasRegion regNemico;
		public OggettoNemico(TextureAtlas atlas){
			regNemico = atlas.findRegion("ape");
		}
	}//Nemico
	
	public class OggettoBomba{
		public final AtlasRegion regBomba;
		public OggettoBomba(TextureAtlas atlas){
			regBomba = atlas.findRegion("bomba");
		}
	}//Bomba
	
	public class OggettoScudo{
		public final AtlasRegion regScudo;
		public OggettoScudo(TextureAtlas atlas){
			regScudo = atlas.findRegion("scudo");
		}
	}//Scudo
	
	public class DecorazioneLivello{
		public final AtlasRegion montagna;
		public final AtlasRegion nuvola01;
		public final AtlasRegion nuvola02;
		public final AtlasRegion nuvola03;
		
		public DecorazioneLivello(TextureAtlas atlas){
			montagna = atlas.findRegion("montagna");
			nuvola01 = atlas.findRegion("nuvola01");
			nuvola02 = atlas.findRegion("nuvola02");
			nuvola03 = atlas.findRegion("nuvola03");
		}

	}//DecorazioneOggetto
	
}
