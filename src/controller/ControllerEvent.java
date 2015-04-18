
package controller;

/**
 *
 * @author kovko
 */
public class ControllerEvent {
    public static final int LEFT_ARROW = 1;
    public static final int UP_ARROW = 2;
    public static final int RIGHT_ARROW = 3;
    public static final int DOWN_ARROW = 4;
    
    private int eventID;

    public ControllerEvent(int eventID) {
        this.eventID=eventID;
    }

    
    public int getEventID() {
        return eventID;
    }
    
}
