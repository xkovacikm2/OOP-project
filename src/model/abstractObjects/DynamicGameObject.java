
package model.abstractObjects;

import View.ViewRequestsHandler;

/**
 *Interface for objects capable of movement
 * @author kovko
 */
public abstract class DynamicGameObject extends StaticGameObject {
    /**
     * velocity in x direction
     */
    protected int dx;
    
    /**
     * velocity in y direction
     */
    protected int dy;
    
    /**
     * char representation of object that is shown in map
     * used in temporary text view
     */
    protected char placeHolder;
    
    /**
     * integer size of object displayed in map
     * e.g 2 means square 2x2
     */
    protected int size;
    
    /**
     * @param x coordinate for spawning object
     * @param y coordinate for spawning object
     * @param dx default velocity in x direction
     * @param dy default velocity in y direction
     * @param size  of object
     */
    public DynamicGameObject(int x, int y, int dx, int dy, int size){
        super(x, y);
        this.dx=dx;
        this.dy=dy;
        this.size=size;
    }
    
    /**
     * 
     * @param x coordinate for spawning object
     * @param y coordinate for spawning object
     * @param size of object
     */
    public DynamicGameObject(int x, int y, int size){
        this(x, y, 0, 0, size);
    }
    
    /**
     * simple getter for velocity in x direction
     * @return {@link DynamicGameObject#dx}
     */
    public final int getDx() {
        return dx;
    }

    /**
     * simple setter for velocity in x direction
     * @param dx int value of velocity in x direction
     */
    public final void setDx(int dx) {
        this.dx = dx;
    }

    /**
     * simple getter for {@link DynamicGameObject#dx}
     * @return {@link DynamicGameObject#dx}
     */
    public final int getDy() {
        return dy;
    }   

    /**
     * simple setter for {@link DynamicGameObject#dy}
     * @param dy int value of velocity in y direction
     */
    public final void setDy(int dy) {
        this.dy = dy;
    }
    
    /**
     * performs move loop of dynamicObject
     */
    protected void move(){
        this.x+=this.dx;
        this.y+=this.dy;
        ViewRequestsHandler.consolePrintln(this +" moved to position x: "+this.x+" y: "+this.y);
    }

    /**
     * simple getter for {@link DynamicGameObject#placeHolder}
     * @return {@link DynamicGameObject#placeHolder}
     */
    public char getPlaceHolder() {
        return placeHolder;
    }
    
    /**
     * abstract method for logic implementation of dynamicObject
     * @return false on success
     */
    abstract public boolean logic();
    
    /**
     * simple getter for {@link DynamicGameObject#size}
     * @return {@link DynamicGameObject#size}
     */
    public int getSize(){
        return size;
    }
    
    /**
     * detects if objects coordines collide with coordinates given as parameters
     * @param x coordinate to compare with
     * @param y coordinate to compare with
     * @return true if collision happened, false otherwise
     */
    public boolean isCollision(int x, int y){
        return ((this.x<=x) && (this.x+this.size>=x) && (this.y<=y) && (this.y+this.size>=y));
    }
    
    /**
     * abstract method of reaction of object to Projectile explosion
     * @param projectileCoords [0] == x coordinate of exploded projectile [1] == y coordinate of exploded projectile
     * @return true if hit by explosion
     */
    abstract public boolean onProjectileHit(int projectileCoords[]);
    
    /**
     * abstract method of destruction of object, if hit by projectile
     */
    abstract protected void destroy();
    
    /**
     * Utility method used by controller
     * @return returns false defaultly
     */
    public boolean focused(){
        return false;
    }
}
