/*
 * This class is THE MOFO TANK!!!
 * it changes its position based on orders of the driver
 * it kills itself and all it's components if hit
 * owns entire crew and tankCanon
 * is owned by Colonel, tankDriver and tankCommander have references
 * is observed by FieldObserver
 */
package model.tankObjects;

import View.ViewRequestsHandler;
import controller.ControllerObservable;
import model.abstractObjects.DynamicGameObject;
import model.listeners.OnTankMoveListener;
import model.basic.FieldObserver;
import model.listeners.OnTankDestroyListener;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kovko
 */
public class Tank extends DynamicGameObject{
    private final List<OnTankMoveListener> onTankMoveListeners = new ArrayList<>();
    private final List<OnTankDestroyListener> onTankDestroyListeners = new ArrayList<>();
    private final List<ControllerObservable> controllerObservables = new ArrayList<>();

    public Tank(int x, int y, int parentID){
        super(x, y, 1);
        this.placeHolder = (char) (parentID+'0');
        FieldObserver.addDynamicGameObject(this);
    }
    
    @Override
    /**
     * Loop tick listener
     */
    public boolean logic(){
        if(!FieldObserver.isWall(x+dx, y+dy, size))
            this.move();
        else
            ViewRequestsHandler.consolePrintln("Cannot move there!");
        //send tick to listeners
        onTankMoveListeners.stream().forEach(listener -> listener.onTankMove(this));
        ViewRequestsHandler.repaintBattleGround();
        this.dx=0;
        this.dy=0;
        return false;
    }
    
    /**
     * Adds listener to onTankMoveListeners arraylist
     * @param listener 
     */
    public void addOnTankMoveListener(OnTankMoveListener listener){
        this.onTankMoveListeners.add(listener);
    }

    /**
     * Adds listener to onTankMoveListeners arraylist
     * @param listener 
     */
    public void addOnTankDestroyListener(OnTankDestroyListener listener){
        this.onTankDestroyListeners.add(listener);
    }
    
    @Override
    /**
     *on Projectile hit event handler - if tank in range of explosion, it is destroyed
     */
    public boolean onProjectileHit(int projectileCoords[]) {
        //if collision happened at my coordinates
        if(projectileCoords[0]<=this.x+this.size+2 && projectileCoords[0]>=x-2 && projectileCoords[1]<=this.y+this.size+2 && projectileCoords[1]>=this.y-2){
            ViewRequestsHandler.consolePrintln(this+" has been hit by projectile and destroyed by the explosion");
            this.destroy();
            return true;
        }
        return false;
    }

    @Override
    /**
     * destroys this object
     */
    protected void destroy() {
        FieldObserver.removeDynamicGameObject(this);
        onTankDestroyListeners.stream().forEach(listener -> listener.onTankDestroy());
    }
    
    @Override
    /**
     * checks if one of crew members is focused
     * @return true if one of crew members is focused
     */
    public boolean focused(){
        return controllerObservables.stream().anyMatch((observable) -> (observable.isFocused()));
    }
    
    public void addControllerObservable(ControllerObservable observable){
        controllerObservables.add(observable);
    }
    
    public void removeControllerObservable(ControllerObservable observable){
        controllerObservables.remove(observable);
    }
}
