package model.listeners;
import model.tankObjects.Tank;

public interface OnTankMoveListener {
    /**
     * OnTankMove Event Handler
     * @param tank 
     */
    public void onTankMove(Tank tank);
}
