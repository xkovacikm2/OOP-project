
package controller;

/**
 *
 * @author kovko
 */
public class CrewNotSelectedException extends Exception{
    
    private final String message;
    private static CrewNotSelectedException instance;

    /**
     * Constructor replacement
     * @return instanceOfCrewNotSelectedException
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
    
    @Override
    public String getMessage(){
        return message;
    }
}
