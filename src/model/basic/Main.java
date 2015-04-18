package model.basic;

import View.ViewRequestsHandler;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.terrain.Map;

/*
 * main class of OOP project
 * takes care of app loop
 *
 * @author kovko
 */
public class Main implements ActionListener{
    private static Colonel colonel1;
    private static Colonel colonel2;
    public static final Main instanceOfMain = new Main();
    
    private void Main(){}
    
    /**
     * Spawns colonels. creates new instance of map, starts mainLoop of project
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ViewRequestsHandler.prepareView();
        Map map = new Map();
        ViewRequestsHandler.drawMap(map);
        FieldObserver.setMap(map);
        colonel1 = new Colonel();
        colonel2 = new Colonel();
        FieldObserver.clearMap();
        FieldObserver.animateMap();
        colonel1.announceTarget();
        colonel2.announceTarget();
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!colonel1.won() && !colonel2.won()){
            colonel1.sendLoopSignal();
            colonel2.sendLoopSignal();
            FieldObserver.moveLoop();
            FieldObserver.clearMap();
            FieldObserver.animateMap();
        }
    }
}
