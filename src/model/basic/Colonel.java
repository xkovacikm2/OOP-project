
package model.basic;
import View.ViewRequestsHandler;
import model.crew.TankCommander;
import java.util.ArrayList;
import java.util.List;
import model.terrain.Map;

/**
 * Top ranked military officer class
 * @author kovko
 */
public class Colonel {

    private final List<TankCommander> commanders;
    private static int count = 0;
    private final int ID;
    private final Map map;
    
    public Colonel(){
        this.commanders = new ArrayList<>();
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
        for(int i=1; i<=5; i++){
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
        return commanders.stream().anyMatch(c -> c.atTarget());
    }

    /**
     * Simple getter for {@link Colonel#ID}
     * @return {@link Colonel#ID}
     */
    public int getID() {
        return this.ID;
    }
    
    /**
     * Sends coordinates of game objective to subordinated TankCommanders
     */
    void announceTarget(){
        int exit[] = map.getExit();
        commanders.stream().forEach((commander) -> {
            System.out.println(this + "sent the target coordinates to: "+ commander);
            commander.setObjective(exit);
        });
    }
    
    /**
     * Sends new loop tick for subordinated TankCommanders
     */
    void sendLoopSignal(){
        commanders.stream().forEach(commander -> commander.performLoop());
    }
    
    /**
     * Removes commander from {@link Colonel#commanders}
     * @param commander 
     */
    public void removeTankCommander(TankCommander commander){
        this.commanders.remove(commander);
        ViewRequestsHandler.consolePrintln(this+" lost yet another "+commander);
    }
}
