
package model.crew;
import view.ViewRequestsHandler;
import model.listeners.OnTankMoveListener;
import model.tankObjects.Tank;
import model.basic.Colonel;
import model.basic.FieldObserver;
import model.listeners.OnTankDestroyListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Second highest ranked military officer
 * Handles communication between Colonel and other crew members
 * Handles AI of targeting and pathfinding
 * @author kovko
 */
public class TankCommander implements OnTankMoveListener, OnTankDestroyListener{
    private final TankDriver driver;
    private final TankGunner gunner;
    private final Colonel parent;
    private int targetCoords[];
    private List<int[]> path = new ArrayList<>();
    private int tickCounter;
    
    public TankCommander(Colonel parent, int[] spawnCoords){
        this.driver = new TankDriver(spawnCoords, parent.getID());
        this.gunner = new TankGunner(this.driver.getTank());
        this.parent = parent;
        this.driver.getTank().addOnTankMoveListener(this);
        this.driver.getTank().addOnTankDestroyListener(this);
        System.out.println(this.toString() +" has been created");
    }
    
    /**
     * sets new target for tank to reach and calculates shortest path to it
     * @param targetCoords[] coordinates of the exit point
     */
    public void setObjective(int targetCoords[]){
        this.targetCoords=targetCoords;
        this.calculatePath();
        //uncomment to verbose coords
        //this.writePath();
    }
    
    /**
     * calculates shortest way to get to the target and stores it to path
     */
    private void calculatePath(){
        System.out.println(this +" is calculating path to target set by: "+this.parent);
        int myCoords[] = driver.getCoords();
        List<int[]> step = new ArrayList<>();
        step.add(myCoords);
        List<List<int[]>> queue = new ArrayList<>();
        queue.add(step);
        boolean flag = true;
        while(flag){
            step = new ArrayList<>();
            List<int[]> prevstep = queue.get(queue.size()-1);
            for(int[] actcoord:prevstep){
                for(int k=0; k<4; k++){
                    int newcoord[] = {actcoord[0], actcoord[1]};
                    switch (k){
                        case 0: newcoord[0]++; break;
                        case 1: newcoord[0]--; break;
                        case 2: newcoord[1]++; break;
                        case 3: newcoord[1]--; break;
                    }
                    //wall check
                    if(FieldObserver.isWall(newcoord[0], newcoord[1], driver.measureSize())){
                        continue;
                    }
                    //reverse check
                    if(this.cmpCoords(prevstep, newcoord)){
                        continue;
                    }
                    //duplicates check
                    if(this.cmpCoords(step, newcoord)){
                        continue;
                    }
                    step.add(newcoord);
                    //target reached check
                    if(this.cmpCoords(this.targetCoords, newcoord)){
                        flag = false;
                        break;
                    }
                }
                if(!flag){
                    break;
                }
            }
            queue.add(step); 
        }
        
    this.choosePath(queue);
    }
    
    private boolean cmpCoords(List<int[]> step, int[] coord){
        return step.stream().anyMatch((contcoord) -> (cmpCoords(contcoord, coord)));
    }
    
    private boolean cmpCoords(int[] target, int[] coord, int range){
        for (int i = 0-range; i<=range; i++){
            for (int j = 0-range; j<=range; j++){
                int tempCoord[] = {coord[0]+j, coord[1]+i};
                if(cmpCoords(target, tempCoord)){
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean cmpCoords(int[] target, int[] coord){
        return(target[0] == coord[0] && target[1] == coord[1]);
    }
    
    private void choosePath(List<List<int[]>> queue){
        List<int[]> inversePath = new ArrayList<>();
        //check if destination reachable
        if(!this.isReachable(queue)){
            System.out.println(this+": target currently not reachable");
        }
        //select path
        inversePath.add(this.targetCoords);
        for(int i = queue.size()-2; i>=0; i--){
            int prevStep[]=inversePath.get(inversePath.size()-1);
            for(int[] nextStep:queue.get(i)){
                if(cmpCoords(nextStep, prevStep, 1)){
                    inversePath.add(nextStep);
                    break;
                }
            }
        }
        this.storePath(inversePath);
        this.tickCounter=0;
    }

    private void storePath(List<int[]> inversePath) {
        this.path = new ArrayList<>();
        for(int i = inversePath.size()-2; i>=0; i--){
            this.path.add(inversePath.get(i));
        }
        System.out.println(this+" has succesfully calculated path to target");
    }
    
    /**
     * for debugging purposes only
     */
    private void writePath(){
        ViewRequestsHandler.consolePrintln(this+ "is announcing it's path!");
        this.path.stream().forEach((coord) -> ViewRequestsHandler.consolePrintln("x: "+coord[0]+" y: "+coord[1]));
    }
    
    /**
     * Orders listener from Colonel
     */
    public void performLoop(){
        if(this.tickCounter<this.path.size()){
            if(!this.driver.nextCoords(this.path.get(this.tickCounter))){
                ViewRequestsHandler.consolePrintln(this+": acknowledged, searching alternate route");
                this.calculatePath();
            }
            else{
                this.tickCounter++;
            }
        }
        //this.writePath();
    }
    
    /**
     * Implementation of onTankMoveListener interface
     * checks if there is enemy in line of sight after moving to new coordinates
     * if there is, orders TankGunner to shoot at the spotted enemy
     * @param tank 
     */
    @Override
    public void onTankMove(Tank tank) {
        //obzeranie sa okolo seba
        int targetCoordinates[] = FieldObserver.inLOS(this.driver.getCoords(), this.parent.getID());
        //ak nieco okolo mna je
        if(targetCoordinates!=null){
            //popytam sa povolenie k strelbe
            ViewRequestsHandler.consolePrintln(this + " is ordering "+this.gunner+" to shoot!");
            this.gunner.shootOrder(targetCoordinates[0], targetCoordinates[1]);
        }
    }
    
    /**
     * checks if target destination is reached
     * @return true if tank is the destination, false otherwise
     */
    public boolean atTarget(){
        int actcoords[] = this.driver.getCoords();
        return (actcoords[0]==this.targetCoords[0] && actcoords[1]==this.targetCoords[1]);
    }

    private boolean isReachable(List<List<int[]>> queue) {
        List<int[]> tempList = queue.get(queue.size()-1);
        int[] potentialTarget = tempList.get(tempList.size()-1);
        return (this.cmpCoords(this.targetCoords, potentialTarget));
    }

    @Override
    public void onTankDestroy() {
        this.parent.removeTankCommander(this);
        ViewRequestsHandler.consolePrintln(this+" is killed by the explosion");
    }
    
}
