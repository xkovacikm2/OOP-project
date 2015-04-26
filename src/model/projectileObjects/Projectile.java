/*
 * Projectile that moves itself based on its velocity and direction
 * kills tank if hits
 * has max range, if its reached, stops and kills itself
 * is observed by FieldObserver
 */
package model.projectileObjects;

import view.ViewRequestsHandler;
import model.abstractObjects.DynamicGameObject;
import model.basic.FieldObserver;

/**
 *
 * @author kovko
 */
public class Projectile extends DynamicGameObject{

    private int range;
    private static final int MAXRANGE = 20;
        
    /**
     * getter for maxrange of projectile
     * @return MAXRANGE of projectile
     */
    public static int getMAXRANGE() {
        return MAXRANGE;
    }
    
    /**
     * @param x position x in plane
     * @param y position y in plane
     * @param dx velocity in x direction
     * @param dy velocity in y direction
     */
    public Projectile(int x, int y, int dx, int dy){
        super(x,y, dx, dy, 0);
        this.placeHolder = 'P';
        this.range = MAXRANGE;
        this.announce();
    }
    
    /**
     * adds itself to FieldObserverList
     */
    private void announce(){
        FieldObserver.addDynamicGameObject(this);
    }
    
    /**
     * executes lifecycle
     * observer tick listener
     * @return false on success
     */
    @Override
    public boolean logic(){
        for(int i=0; i<5; i++){
            if(this.pathBlocked()){
                this.destroy();
                ViewRequestsHandler.repaintBattleGround();
                return true;
            }
            this.move();
            ViewRequestsHandler.repaintBattleGround();
            this.checkRange();
        }
        return false;
    }
    
    
    /**
     * moves to position given by velocity
     * decrements range accordingly
     */
    @Override
    protected void move(){
        super.move();
        this.range--;
    }
    
    private void checkRange(){
        if (this.range<1){
            this.destroy();
        }
    }
    
    /**
     * tells observer to forget him
     */
    @Override
    protected void destroy(){
        ViewRequestsHandler.consolePrintln(this +"hit something and is killing itself");
        FieldObserver.removeDynamicGameObject(this);
        FieldObserver.onProjectileExplosion(new int[]{this.x,this.y});
    }
    
    private boolean pathBlocked(){
        ViewRequestsHandler.consolePrintln(this +" is asking if the path is clear");
        return FieldObserver.isSomething(this.x, this.y);
    }

    @Override
    public boolean onProjectileHit(int[] projectileCoords) {
        //if explosion occured nearby
        if(projectileCoords[0]<=this.x+1 && projectileCoords[0]>=this.x-1 && projectileCoords[1]<=this.y+1 && projectileCoords[1]>=this.y-2){
            //and it is not already me exploading
            if(!(projectileCoords[0]==this.x && projectileCoords[1]==y)){
                //explode
                this.destroy();
                return true;
            }
        }
        return false;
    }

}
