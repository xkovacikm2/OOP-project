
package model.crew;
import view.ViewRequestsHandler;
import controller.Controller;
import controller.ControllerEvent;
import controller.ControllerObservable;
import controller.FocusSetter;
import model.listeners.OnTankDestroyListener;
import model.tankObjects.Tank;
import model.tankObjects.TankCannon;

/**
 *Shoots the gun
 * @author kovko
 */
public class TankGunner extends ControllerObservable implements OnTankDestroyListener{
    private TankCannon canon;
    
    TankGunner(Tank tank){
        super();
        this.idCrew=FocusSetter.ID_TANK_GUNNER;
        this.canon=new TankCannon(tank);
        tank.addOnTankDestroyListener(this);
        tank.addControllerObservable(this);
        System.out.println(this.toString() +" has been created");
    }
    
    /**
     * based on target coords decides the direction to shoot
     * @param targetX
     * @param targetY 
     */
    void shoot(int targetX, int targetY){
        int x = canon.getX();
        int y = canon.getY();
        ViewRequestsHandler.consolePrintln("Shooting at target at x: "+targetX+" y: "+targetY);
        if(targetX==x){
            if(targetY>y){
                canon.shoot("down");
            }
            else{
                canon.shoot("up");
            }
        }
        else{
            if(targetX>x){
                canon.shoot("right");
            }
            else{
                canon.shoot("left");
            }
        }
    }

    @Override
    public void onTankDestroy() {
        this.canon = null;
        Controller.removeObservable(this);
        ViewRequestsHandler.consolePrintln(this+" is killed by the explosion");
    }

    @Override
    public void controllerActionListener(ControllerEvent e) {
        if(focus){
            switch (e.getEventID()){
                case ControllerEvent.UP_ARROW: canon.shoot("up"); break;
                case ControllerEvent.DOWN_ARROW: canon.shoot("down"); break;
                case ControllerEvent.LEFT_ARROW: canon.shoot("left"); break;
                case ControllerEvent.RIGHT_ARROW: canon.shoot("right"); break;
            }
        }
    }

    @Override
    public int[] getCoords() {
        return (new int[]{this.canon.getX(), this.canon.getY()});
    }

    /**
     * Shoot commad listener
     * @param targetX
     * @param targetY 
     */
    void shootOrder(int targetX, int targetY) {
        if(!focus)
            shoot(targetX, targetY);
        else
            ViewRequestsHandler.consolePrintln("Enemy spotted at "+targetX+" "+targetY+" shoot!");
    }

}
