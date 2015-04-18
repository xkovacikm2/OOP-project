
package controller;

/**
 *
 * @author kovko
 */
public class FocusSetter {
    public static final int ID_NULL = 1;
    public static final int ID_TANK_COMMANDER = 1;
    public static final int ID_TANK_GUNNER = 2;
    public static final int ID_TANK_DRIVER = 3;
    
    private int idCrew;
    private int coords[];

    /**
     * creates instance of FocusSetter, that is sent globally
     * @param id
     * @param coords 
     */
    FocusSetter(int id, int coords[]){
        this.idCrew=id;
        this.coords=coords;
    }
    
    /**
     * getter for id of focused object
     * @return int idCrew
     */
    public int getIdCrew() {
        return idCrew;
    }

    /**
     * getter for coords of focused object
     * @return int{} corrds
     */
    public int[] getCoords() {
        return coords;
    }
    
}
