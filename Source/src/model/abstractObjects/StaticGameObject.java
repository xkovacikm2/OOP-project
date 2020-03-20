
package model.abstractObjects;


/**
 *Interface for objects that are not able to move by themselves
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
     * simple getter for {@link StaticGameObject#x}
     * @return {@link StaticGameObject#x}
     */
    public final int getX() {
        return x;
    }

    /**
     * simple getter for {@link StaticGameObject#y}
     * @return {@link StaticGameObject#y}
     */
    public final int getY() {
        return y;
    }
    
}
