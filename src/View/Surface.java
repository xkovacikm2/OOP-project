package View;

import controller.Controller;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author kovko
 */
public class Surface extends JPanel{
    
    private Rectangle2D.Float background;
    private Rectangle2D.Float exit;
    private List<Rectangle2D.Float> walls;
    private List<DrawableObject> objects;
    
    private Color backgroundColor;
    private Color wallColor;
    private Color exitColor;
    
    public Surface(){
        super();
        this.walls = new ArrayList<>();
        this.objects = new ArrayList<>();
        this.exitColor = Color.blue;
        this.backgroundColor = Color.white;
        this.wallColor = Color.red;
        this.addMouseListener(new MouseClickListener());
    }
            
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        doDrawing(g);
    }

    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.setColor(backgroundColor);
        g2d.fill(background);
        
        g2d.setColor(wallColor);
        walls.stream().forEach((wall) -> {
            g2d.fill(wall);
        });
        
        g2d.setColor(exitColor);
        g2d.fill(exit);

        ViewRequestsHandler.consolePrintln("Object drawn: "+objects.size());
                
        objects.stream().forEach((object) -> {
            g2d.setColor(object.color);
            g2d.fill(object.rect);
        });
              
        ViewRequestsHandler.consolePrintln("Surface repainted");
    }

    public void setBackground(int x, int y, int size) {
        this.background = new Rectangle2D.Float(x, y, size, size);
    }

    public void setExit(int x, int y, int size) {
        this.exit = new Rectangle2D.Float(x, y, size, size);
    }
    
    public void addWalls(int x, int y, int size) {
        this.walls.add(new Rectangle2D.Float(x, y, size, size));
    }

    public void addObjects(DrawableObject object) {
        this.objects.add(object);
    }
    
    public void resetObjects(){
        this.objects= new ArrayList<>();
    }
    
    private class MouseClickListener extends MouseAdapter{
        @Override
        public void mousePressed(MouseEvent e){
            int x = e.getX();
            int y = e.getY();
            
            System.out.println("Mouse clicked at "+x+" "+y);
            
            for (DrawableObject object:objects){
                if(object.rect.contains(x, y)){
                    Controller.objectClicked((int)object.rect.x, (int)object.rect.y);
                    break;
                }
            }
        }
    }
}

