
package View;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.List;
import javax.swing.SwingUtilities;
import model.abstractObjects.DynamicGameObject;
import model.basic.FieldObserver;
import model.terrain.Map;

/**
 *
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
     * Prints string to console
     * @param s
     */
    public static void consolePrintln(String s){
        gui.consoleWrite(s+"\n");
    }
    
    /**
     * Converts double to string and calls overloaded method {@link ViewRequestsHandler#consolePrintln(java.lang.String)  consolePrintln}
     * @param d 
     */
    public static void consolePrintln(double d){
        consolePrintln(Double.toString(d));
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

    private ViewRequestsHandler(){}
    
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
     * move animation request handler for dynamic game objects
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
     * @param map 
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
