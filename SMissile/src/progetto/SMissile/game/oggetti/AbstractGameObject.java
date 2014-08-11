package progetto.SMissile.game.oggetti;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class AbstractGameObject {
	
	public Vector2 posizione;
	public Vector2 dimensione;
	public Vector2 origine;
	public Vector2 scala;
	public float rotazione;
	
	public Vector2 velocity;
	public Vector2 terminalVelocity;
	public Vector2 friction;
	public Vector2 acceleration;
	public Rectangle bounds;
	
	public AbstractGameObject(){
		posizione=new Vector2();
		dimensione=new Vector2(1,1);
		origine=new Vector2();
		scala=new Vector2(1,1);
		rotazione=0;
		
		velocity = new Vector2();
		terminalVelocity = new Vector2(1, 1);
		friction = new Vector2();
		acceleration = new Vector2();
		bounds = new Rectangle();
	}
	
	protected void updateMotionX (float deltaTime) { }
	
	protected void updateMotionY (float deltaTime){	}
	
	public void update(float deltaTime){
		updateMotionX(deltaTime);
		updateMotionY(deltaTime);
		// Muoviamo l'oggetto nella nuova posizione: (s=vt)
		posizione.x += velocity.x * deltaTime;
		posizione.y += velocity.y * deltaTime;
	}
	
	public abstract void render (SpriteBatch batch);
	
}
