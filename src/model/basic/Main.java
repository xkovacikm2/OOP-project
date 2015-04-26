package model.basic;

import view.ViewRequestsHandler;
import model.terrain.Map;

/*
 * main class of OOP project
 * takes care of app loop
 *
 * @author kovko
 */
public class Main {
    private static Colonel colonel1;
    private static Colonel colonel2;
    
    /**
     * Utility class is not ment to be instanced!!!
     */
    private Main() throws Exception{
        throw new Exception("Utility class is not ment to be instanced!!!");
    }
    
    /**
     * Spawns colonels. creates new instance of map, starts mainLoop of project
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Map map = new Map();
        FieldObserver.setMap(map);
        colonel1 = new Colonel();
        colonel2 = new Colonel();
        colonel1.announceTarget();
        colonel2.announceTarget();
        ViewRequestsHandler.prepareView();
        ViewRequestsHandler.drawMap(map);
        FieldObserver.clearMap();
        FieldObserver.animateMap();
    }
    
    public static void mainLoop() {
        if(!colonel1.won() && !colonel2.won()){
            colonel1.sendLoopSignal();
            colonel2.sendLoopSignal();
            FieldObserver.moveLoop();
            FieldObserver.clearMap();
            FieldObserver.animateMap();
        }
    }
}
