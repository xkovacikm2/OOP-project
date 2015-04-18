
package model.abstractObjects;

import View.ViewRequestsHandler;

/**
 *
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
     */
    public DynamicGameObject(int x, int y, int size){
        this(x, y, 0, 0, size);
    }
    
    /**
     * simple getter for velocity in x direction
     * @return integer representation of velocity in x direction
     */
    public final int getDx() {
        return dx;
    }

    /**
     * simple setter for velocity in x direction
     * @param dx integer value of velocity in x direction
     */
    public final void setDx(int dx) {
        this.dx = dx;
    }

    /**
     * simple getter for velocity in y direction
     * @return integer representation of velocity in y direction
     */
    public final int getDy() {
        return dy;
    }   

    /**
     * simple setter for velocity in y direction
     * @param dy integer value of velocity in y direction
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
     * simple getter for representation of object that is shown in map
     * @return char representation of object
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
     * simple getter for size of object in map
     * @return integer value of size
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
    
    public boolean focused(){
        return false;
    }
}
