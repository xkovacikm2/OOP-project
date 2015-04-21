
package controller;

import View.ViewRequestsHandler;
import java.util.ArrayList;
import java.util.List;

public final class Controller {
    
    private static int clickedId = FocusSetter.ID_NULL;
    
    /**
     * Utility class is not ment to be instanced!!!
     */
    private Controller() throws Exception{
        throw new Exception("Utility class is not ment to be instanced!!!");
    }
    
    private static List<ControllerObservable> Observables = new ArrayList<>();
    
    /**
     * Simple setter for active menu button
     * @param id 
     */
    public static void setClickedId(int id){
        clickedId=id;
    }
    
    
    public static void objectClicked(int x, int y) throws Exception{
        if(clickedId==FocusSetter.ID_NULL){
            throw (CrewNotSelectedException.getInstance());
        }
        int coords[]= {x/ViewRequestsHandler.DEFAULT_MAP_RECT_SIZE,y/ViewRequestsHandler.DEFAULT_MAP_RECT_SIZE};
        System.out.println("Object detected at clicked coords. Matrix coords are: "+coords[0]+" "+coords[1]);
        setFocus(new FocusSetter(clickedId, coords));
        clickedId = FocusSetter.ID_NULL;
    }
    
    /**
     * adds observable to private arraylist of ControllerObservables
     * @param o 
     */
    public static void addObservable(ControllerObservable o){
        Observables.add(o);
        System.out.println("Controller is aware of "+o);
    }
    
    /**
     * removes observable from private arraylist of ControllerObservables
     * @param o 
     */
    public static void removeObservable(ControllerObservable o){
        Observables.remove(o);
        ViewRequestsHandler.consolePrintln("Controller lost track of "+o);
    }
    
    /**
     * Sends ControllerEvent given as param to all observables
     * @param e 
     */
    public static void notify(ControllerEvent e){
        for (ControllerObservable observable:Observables){
            observable.controllerActionListener(e);
        }
    }
    
    /**
     * Sets FocusSetter given as param to all observables
     * @param fs 
     */
    private static void setFocus(FocusSetter fs){
        Observables.stream().forEach((observable) -> {
            observable.controllerSetFocusListener(fs);
        });
    }
    
}
