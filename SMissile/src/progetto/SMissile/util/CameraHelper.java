package progetto.SMissile.util;

import progetto.SMissile.game.oggetti.AbstractGameObject;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class CameraHelper {
	@SuppressWarnings("unused")
	private static final String TAG = CameraHelper.class.getName();
	
	private final float MAX_ZOOM_IN=0.085f;
	private final float MAX_ZOOM_OUT=6.0f;
	
	private Vector2 posizione;
	private float zoom;
	private AbstractGameObject target;
	
	public CameraHelper(){
		posizione=new Vector2();
		zoom=0.085f;
	}
	
	public void update(float deltaTime){
		if(!hasTarget()) return;
		
		//+20 per avere il missile non al centro, ma 
		//posizionato sulla parte sinistra dello schermo
		posizione.x=target.posizione.x + 20;
		posizione.y=target.posizione.y;

		//impediamo alla camera di andare troppo in basso e troppo in alto
		
		//'+16' così la camera si ferma prima che il missile arrivi al bordo inferiore
		if(posizione.y < +16)
			posizione.y = +16;
		
		//'-14' così la camera si ferma prima che il missile arrivi al bordo superiore
		if(posizione.y > 35)//Constants.ALTEZZA_MAX_CAM -14)
			posizione.y = 35;// Constants.ALTEZZA_MAX_CAM -14;
	}
	
	public void setPosition(float x, float y){
		this.posizione.set(x, y);
	}
	
	public Vector2 getPosizione(){
		return posizione;
	}
	
	public void addZoom(float amount){
		setZoom(zoom+amount);
	}
	
	public void setZoom(float zoom){
		this.zoom=MathUtils.clamp(zoom, MAX_ZOOM_IN, MAX_ZOOM_OUT);
	}
	
	public float getZoom(){
		return zoom;
	}
	
	public void setTarget(AbstractGameObject target){
		this.target=target;
	}
	
	public AbstractGameObject getTarget(){
		return target;
	}
	
	public boolean hasTarget(){
		return target!=null;
	}
	
	public boolean hasTarget(AbstractGameObject target){
		return hasTarget() && this.target.equals(target);
	}
	
	public void applyTo(OrthographicCamera camera){
		camera.position.x=posizione.x;
		camera.position.y=posizione.y;
		camera.zoom=zoom;
		camera.update();
	}
	
}
