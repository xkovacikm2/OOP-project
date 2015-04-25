/*
 * This class if ordered creates instance of Projectile and sets its velocity
 * and direction and passes its reference to FieldObserver
 * TODO after shot has been fired, needs to be reloaded by TankGunner, it takes time == cooldown
 * reference is owned by Tank and TankGunner
 */
package model.tankObjects;

import View.ViewRequestsHandler;
import model.listeners.OnTankMoveListener;
import model.projectileObjects.Projectile;
import model.abstractObjects.StaticGameObject;

/**
 *
 * @author kovko
 */
public class TankCannon extends StaticGameObject implements OnTankMoveListener{
    private final int LEFT = -1;
    private final int RIGHT = 1;
    private final int UP = -1;
    private final int DOWN = 1;
    
    private final int cooldownTime = 2;
    private int cooldown;
    
    public TankCannon(Tank tank){
        super(tank.getX(), tank.getY());
        tank.addOnTankMoveListener(this);
        this.cooldown = 0;
    }
    
    /**
     * creates projectile with default velocity in given direction
     * @param where 
     */
    public void shoot(String where){
        if(cooldown==0){
            int dx, dy;
            switch (where){
                case "left": dx=this.LEFT; dy=0; ViewRequestsHandler.consolePrintln(new Projectile(this.x-1, this.y, dx, dy) + " has been fired by " + this); break;
                case "right": dx=this.RIGHT; dy=0; ViewRequestsHandler.consolePrintln(new Projectile(this.x+2, this.y, dx, dy) + " has been fired by " + this); break;
                case "up": dx=0; dy=this.UP; ViewRequestsHandler.consolePrintln(new Projectile(this.x, this.y-1, dx, dy) + " has been fired by " + this); break;
                case "down": dx=0; dy=this.DOWN; ViewRequestsHandler.consolePrintln(new Projectile(this.x, this.y+2, dx, dy) + " has been fired by " + this);
            }
            
            cooldown = cooldownTime;
        }
        else
            ViewRequestsHandler.consolePrintln(this+" cannot fire, still on cooldown");
    }
    
    /**
     * adjusts position along with tank movement
     * @param x
     * @param y
     */
    private void positionAdjust(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    @Override
    /**
     * on Tank Move event handler
     */
    public void onTankMove(Tank tank){
        this.positionAdjust(tank.getX(), tank.getY());
        if(cooldown>0)
            cooldown--;
    }
}
