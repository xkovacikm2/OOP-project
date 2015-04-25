
package controller;

/**
 *Simple event class for sending messages between controller and model
 * @author kovko
 */
public class ControllerEvent {
    public static final int LEFT_ARROW = 1;
    public static final int UP_ARROW = 2;
    public static final int RIGHT_ARROW = 3;
    public static final int DOWN_ARROW = 4;
    
    private final int eventID;

    public ControllerEvent(int eventID) {
        this.eventID=eventID;
    }

    /**
     * Simple getter for {@link ControllerEvent#eventID}
     * @return 
     */
    public int getEventID() {
        return eventID;
    }
    
}
