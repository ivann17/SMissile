package progetto.SMissile.screens;

import progetto.SMissile.game.Oggetti;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;

public abstract class AbstractGameScreen implements Screen{
	
	protected DirectedGame game;
	
	public AbstractGameScreen(DirectedGame game){
		this.game=game;
	}

	public abstract InputProcessor getInputProcessor();
	
	public abstract void render(float deltaTime);

	public abstract void resize(int width, int height);

	public abstract void show();

	public abstract void hide();

	public abstract void pause();

	public void resume(){ }

	public void dispose(){
		Oggetti.getInstance().dispose();
	}

}
