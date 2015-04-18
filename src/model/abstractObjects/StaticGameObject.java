
package model.abstractObjects;


/**
 *
 * @author kovko
 */
public abstract class StaticGameObject{
    /**
     * represents x position
     */
    protected int x;
    
    /**
     * represents y position
     */
    protected int y;
    
    /**
     * @param x coordinate for spawning object
     * @param y coordinate for spawning object
     */
    public StaticGameObject(int x, int y){
        this.x=x;
        this.y=y;
        System.out.println(this +" has been spawned at location x: "+x+" y: "+y);
    }
    
    /**
     * simple getter for x coordinate of the object
     * @return x coordinate of the object
     */
    public final int getX() {
        return x;
    }

    /**
     * simple getter for y coordinate of the object
     * @return y coordinate of the object
     */
    public final int getY() {
        return y;
    }
    
}
