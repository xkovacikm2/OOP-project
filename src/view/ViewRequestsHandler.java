
package view;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.List;
import javax.swing.SwingUtilities;
import model.abstractObjects.DynamicGameObject;
import model.basic.FieldObserver;
import model.terrain.Map;

/**
 *Utility static class for communication between view and model
 * @author kovko
 */
public final class ViewRequestsHandler {
    /**
     * Translation constant from matrix to pixel representation
     */
    public static final int DEFAULT_MAP_RECT_SIZE = 20;
    
    private static Surface surface;
    private static GUI gui;
    
    /**
     * Utility class is not ment to be instanced!!!
     */
    private ViewRequestsHandler() throws Exception{
        throw new Exception("Utility class is not ment to be instanced!!!");
    }
    
    /**
     * Adds newline to string and calls {@link GUI#consoleWrite(java.lang.String) }
     * @param s - string to print to console
     */
    public static void consolePrintln(String s){
        gui.consoleWrite(s+"\n");
    }
    
    /**
     * Converts number to string and calls overloaded method {@link ViewRequestsHandler#consolePrintln(java.lang.String)  consolePrintln}
     * @param n - Number to print to console
     */
    public static void consolePrintln(Number n){
        consolePrintln(n.toString());
    }

    private static Color pickColor(DynamicGameObject object) {
        if(object.focused()){
            return DrawableObject.CONTROLLED_TANK_COLOR;
        }
        if(object.getPlaceHolder()=='3'){
            return DrawableObject.FIRST_TEAM_COLOR;
        }
        if(object.getPlaceHolder()=='4'){
            return DrawableObject.SECOND_TEAM_COLOR;
        }
        return DrawableObject.PROJECTILE_COLOR;
    }
    
    /**
     * Creates instances of GUI, creates menus, prepares Canvas
     */
    public static void prepareView(){
        surface = new Surface();
        gui = new GUI(surface);
        SwingUtilities.invokeLater(()->{gui.setVisible(true);});
        gui.repaint();
    }
    
    /**
     * move animation request handler
     */
    public static void repaintBattleGround() {
        List<DynamicGameObject> Objects = FieldObserver.getDynamicObjectsArrayList();
        surface.resetObjects();
        Objects.stream().forEach((object) -> {
            Rectangle2D.Float rect = new Rectangle2D.Float(object.getX()*DEFAULT_MAP_RECT_SIZE, object.getY()*DEFAULT_MAP_RECT_SIZE, (object.getSize()+1)*DEFAULT_MAP_RECT_SIZE, (object.getSize()+1)*DEFAULT_MAP_RECT_SIZE);
            Color color = pickColor(object);
            surface.addObjects(new DrawableObject(color, rect));
        });
        gui.repaint();
        gui.requestFocus();
    }
    
    /**
     * draws map on canvas
     * @param map - map to be drawn
     */
    public static void drawMap(Map map){
        int mapArray[][]=map.getMap();
        
        surface.setBackground(0, 0, DEFAULT_MAP_RECT_SIZE*mapArray.length);
        
        for(int y=0; y<mapArray.length; y++){
            for(int x=0; x<mapArray[y].length; x++){
                if(mapArray[y][x]==1){
                    surface.addWalls(x*DEFAULT_MAP_RECT_SIZE, y*DEFAULT_MAP_RECT_SIZE, DEFAULT_MAP_RECT_SIZE);
                }
                else if(mapArray[y][x]=='C'-'0'){
                    surface.setExit((x-1)*DEFAULT_MAP_RECT_SIZE, (y-1)*DEFAULT_MAP_RECT_SIZE, 2*DEFAULT_MAP_RECT_SIZE);
                }
            }
        }
        
        surface.repaint();
    }
}
