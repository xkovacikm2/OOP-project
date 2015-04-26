
package controller;

import view.ViewRequestsHandler;

/**
 * Serves as interface for controllable objects
 * @author kovko
 */
abstract public class ControllerObservable {
    
    protected int idCrew;
    protected boolean focus;

    public ControllerObservable() {
        this.focus=false;
        Controller.addObservable(this);
    }
    
    /**
     * simple coordinations getter
     * @return int[] coordinates
     */
    abstract public int[] getCoords();
    
    /**
     * Controller Event handler
     * @param e event to be handled
     */
    abstract public void controllerActionListener(ControllerEvent e);
   
    /**
     * observable checks coordinates given with its own and if there is match, it sets it's focus value to true
     * if there is not a match, it sets it's focus to false
     * @param fs event to be handled
     * @return true if focus was set succesfully, returns false otherwise
     */
    public boolean controllerSetFocusListener(FocusSetter fs){
        System.out.println(this+" is checking if clicked. My coords are: "+getCoords()[0]+" "+getCoords()[1]+" click coords are: "+fs.getCoords()[0]+" "+fs.getCoords()[1]);
        System.out.println(this+" my id: "+this.idCrew+" fs id: "+fs.getIdCrew());
        if (fs.getIdCrew()==this.idCrew && fs.getCoords()[0]==getCoords()[0] && fs.getCoords()[1]==getCoords()[1]){
            this.focus = true;
            ViewRequestsHandler.consolePrintln(this+" is now focused");
            return true;
        }
        this.focus = false;
        return false;
    }
    
    /**
     * simple focus getter
     * @return true if focused false otherwise
     */
    public boolean isFocused(){
        return focus;
    }
}
