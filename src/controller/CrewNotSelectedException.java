
package controller;

/**
 *Excetion thrown when there is attempt to create new ControllerEvent while crew member is not selected
 * @author kovko
 */
public class CrewNotSelectedException extends Exception{
    
    private final String message;
    private static CrewNotSelectedException instance;

    /**
     * Constructor replacement
     * @return instance of CrewNotSelectedException
     */
    public static CrewNotSelectedException getInstance(){
        if(instance==null){
            instance=new CrewNotSelectedException();
        }
        return instance;
    }
    
    private CrewNotSelectedException() {
        this.message = "Nevybratý člen posádky";
    }
    
    /**
     * Getter for exception message
     * @return 
     */
    @Override
    public String getMessage(){
        return message;
    }
}
