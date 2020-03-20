
package controller;

/**
 *Simple event class for sending messages between controller and model
 * @author kovko
 */
public class FocusSetter {
    public static final int ID_NULL = 1;
    public static final int ID_TANK_COMMANDER = 1;
    public static final int ID_TANK_GUNNER = 2;
    public static final int ID_TANK_DRIVER = 3;
    
    private final int idCrew;
    private final int coords[];

    /**
     * creates instance of FocusSetter, that is sent globally
     * @param id id of crew member to be focused
     * @param coords coords of crew member to be focused
     */
    FocusSetter(int id, int coords[]){
        this.idCrew=id;
        this.coords=coords;
    }
    
    /**
     * getter for {@link FocusSetter#idCrew}
     * @return int idCrew
     */
    public int getIdCrew() {
        return idCrew;
    }

    /**
     * getter for {@link FocusSetter#coords}
     * @return int{} corrds
     */
    public int[] getCoords() {
        return coords;
    }
    
}
