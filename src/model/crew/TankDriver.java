
package model.crew;
import View.ViewRequestsHandler;
import controller.Controller;
import controller.ControllerEvent;
import controller.ControllerObservable;
import controller.FocusSetter;
import model.listeners.OnTankDestroyListener;
import model.tankObjects.Tank;

/**
 *Drives the tank
 * @author kovko
 */
public class TankDriver extends ControllerObservable implements OnTankDestroyListener{
    private Tank tank;
    
    TankDriver(int[] coords, int colonelID){
        super();
        this.idCrew=FocusSetter.ID_TANK_DRIVER;
        this.tank = new Tank(coords[0], coords[1], colonelID);
        this.tank.addOnTankDestroyListener(this);
        this.tank.addControllerObservable(this);
        System.out.println(this.toString() +" has been created");
    }
    
    /**
     * getter for tank coordinates
     * @return 
     */
    @Override
    public int[] getCoords(){
       int coords[] = {tank.getX(), tank.getY()};
       return coords;
    }
    
    /**
     * getter for {@link TankDriver#tank}
     * @return 
     */
    Tank getTank(){
        return this.tank;
    }
    
    /**
     * calls {@link Tank#getSize() } and returns its value
     * @return {@link Tank#size } 
     */
    int measureSize(){
        return tank.getSize();
    }
    
    /**
     * calculates where to move and calls {@link Tank#dx} and {@link Tank#dy} 
     * @param coords
     * @return 
     */
    boolean nextCoords(int[] coords){
        if(!focus){
            int myCoords[]={tank.getX(), tank.getY()};
            this.tank.setDx(coords[0]-myCoords[0]);
            this.tank.setDy(coords[1]-myCoords[1]);
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public void onTankDestroy() {
        this.tank = null;
        Controller.removeObservable(this);
        ViewRequestsHandler.consolePrintln(this+" is killed by the explosion");
    }

    @Override
    public void controllerActionListener(ControllerEvent e) {
        switch (e.getEventID()){
            case ControllerEvent.DOWN_ARROW: tank.setDx(0); tank.setDy(1); break;
            case ControllerEvent.LEFT_ARROW: tank.setDx(-1); tank.setDy(0); break;
            case ControllerEvent.RIGHT_ARROW: tank.setDx(1); tank.setDy(0); break;
            case ControllerEvent.UP_ARROW: tank.setDx(0); tank.setDy(-1); 
        }
    }

}
