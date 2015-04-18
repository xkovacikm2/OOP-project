
package model.basic;
import View.ViewRequestsHandler;
import model.crew.TankCommander;
import java.util.ArrayList;
import java.util.List;
import model.terrain.Map;
/**
 *
 * @author kovko
 */
public class Colonel {
    /**
     * list of TankCommanders subordinated to the instance of Colonel
     */
    private List<TankCommander> commanders = new ArrayList<>();
    
    /**
     * number of Colonels created - used for generating ID
     */
    private static int count = 0;
    
    /**
     * ID of the instance of Colonel
     */
    private int ID;
    
    /**
     * copy of current map, where battle takes place
     */
    private Map map;
    
    public Colonel(){
        this.map = FieldObserver.getMap();
        count++;
        this.ID=count+2;
        System.out.println(this +" has been created! My ID is: "+this.ID);
        this.create();
    }
    
    /**
     * Method creates an army for Colonel
     */
    private void create(){
        for(int i=1; i<=3; i++){
            int coords[] = this.map.requestSpawnPoint(this.ID);
            this.commanders.add(new TankCommander(this, coords));
            System.out.println(this+" spawned TankCommander number: " +i);
        }
    }
    
    /**
     * Checks if battle is won by the instance of Colonel
     * @return true if battle is won, false otherwise
     */
    boolean won(){
        for(TankCommander commander:commanders){
            if(commander.atTarget()){
                ViewRequestsHandler.consolePrintln(this +" won the battle! Congratulations");
                return true;
            }
        }
        return false;
    }

    /**
     * Simple getter of Colonel's ID
     * @return integer value of ID
     */
    public int getID() {
        return this.ID;
    }
    
    /**
     * Sends coordinates of game objective to subordinated TankCommanders
     */
    void announceTarget(){
        int exit[] = map.getExit();
        for(TankCommander commander:commanders){
            System.out.println(this + "sent the target coordinates to: "+ commander);
            commander.setObjective(exit);
        }
    }
    
    /**
     * Sends new loop tick for subordinated TankCommanders
     */
    void sendLoopSignal(){
        for(TankCommander commander:commanders){
            commander.performLoop();
        }
    }
    
    public void removeTankCommander(TankCommander commander){
        this.commanders.remove(commander);
        ViewRequestsHandler.consolePrintln(this+" lost yet another "+commander);
    }
}
