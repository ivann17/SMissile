package progetto.SMissile.screens.transizioni;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Transizioni {
	
	public float getDurata();
	
	public void render (SpriteBatch batch, Texture corScreen, Texture nextScreen, float alpha);

}