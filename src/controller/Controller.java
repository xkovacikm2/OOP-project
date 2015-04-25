
package controller;

import View.ViewRequestsHandler;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility static class handling communication between Controller and Model
 * @author kovko
 */
public final class Controller {
    
    private static int clickedId = FocusSetter.ID_NULL;
    private static final List<ControllerObservable> Observables = new ArrayList<>();
    
    /**
     * Utility class is not ment to be instanced!!!
     */
    private Controller() throws Exception{
        throw new Exception("Utility class is not ment to be instanced!!!");
    }
    
    /**
     * Simple setter for active menu button
     * @param id id of crew member to be selected
     */
    public static void setClickedId(int id){
        clickedId=id;
    }
    
    /**
     * Creates new instance of FocusSetter and calls {@link Controller#setFocus(controller.FocusSetter) }
     * @param x coordinate
     * @param y coordinate
     * @throws CrewNotSelectedException if player haven't chosen role in tank
     */
    public static void objectClicked(int x, int y) throws CrewNotSelectedException{
        if(clickedId==FocusSetter.ID_NULL){
            throw (CrewNotSelectedException.getInstance());
        }
        int coords[]= {x/ViewRequestsHandler.DEFAULT_MAP_RECT_SIZE,y/ViewRequestsHandler.DEFAULT_MAP_RECT_SIZE};
        System.out.println("Object detected at clicked coords. Matrix coords are: "+coords[0]+" "+coords[1]);
        setFocus(new FocusSetter(clickedId, coords));
        clickedId = FocusSetter.ID_NULL;
    }
    
    /**
     * adds observable to {@link Controller#Observables}
     * @param o object to be added
     */
    public static void addObservable(ControllerObservable o){
        Observables.add(o);
        System.out.println("Controller is aware of "+o);
    }
    
    /**
     * removes observable from {@link Controller#Observables}
     * @param o object to be added
     */
    public static void removeObservable(ControllerObservable o){
        Observables.remove(o);
        ViewRequestsHandler.consolePrintln("Controller lost track of "+o);
    }
    
    /**
     * Sends {@link ControllerEvent} given as param to all observables
     * @param e event to be sent
     */
    public static void notify(ControllerEvent e){
        Observables.stream().forEach((observable) -> {
            observable.controllerActionListener(e);
        });
    }
    
    /**
     * Sets {@link FocusSetter} given as param to all observables
     * @param fs event to be sent
     */
    private static void setFocus(FocusSetter fs){
        Observables.stream().forEach((observable) -> {
            observable.controllerSetFocusListener(fs);
        });
    }
    
}
