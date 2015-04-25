package View;

import controller.Controller;
import controller.CrewNotSelectedException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *Canvas for drawing the battle
 * @author kovko
 */
public class Surface extends JPanel{
    
    private Rectangle2D.Float background;
    private Rectangle2D.Float exit;
    private final List<Rectangle2D.Float> walls;
    private List<DrawableObject> objects;
    
    private BufferedImage wallTexture;
    private BufferedImage exitTexture;
    private BufferedImage tankTexture;
    private BufferedImage projectileTexture;
    private final Color backgroundColor;
    
    public Surface(){
        super();
        this.loadImages();
        this.walls = new ArrayList<>();
        this.objects = new ArrayList<>();
        this.backgroundColor = Color.white;
        this.addMouseListener(new MouseClickListener());
    }
    
    private void loadImages(){
        try {
            wallTexture = ImageIO.read(new File("images/brickwall.png"));
            exitTexture = ImageIO.read(new File("images/exit.png"));
            tankTexture = ImageIO.read(new File("images/tank.png"));
            projectileTexture = ImageIO.read(new File("images/projectile.png"));
        } 
        catch (IOException ex) {
            JOptionPane.showMessageDialog(Surface.this, ex.getMessage(), "Chyba", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Prepares Graphics component to be drawn at and calls {@link Surface#doDrawing(java.awt.Graphics) }
     * @param g 
     */
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
        
        walls.stream().forEach((wall) -> {
            g2d.drawImage(wallTexture, (int)wall.x, (int)wall.y, (int)wall.width, (int)wall.height, null);
        });
        
        g2d.drawImage(exitTexture, (int)exit.x, (int)exit.y, (int)exit.width, (int)exit.height, null);

        ViewRequestsHandler.consolePrintln("Object drawn: "+objects.size());
                
        objects.stream().forEach((object) -> {
            g2d.setColor(object.color);
            if(object.rect.width>ViewRequestsHandler.DEFAULT_MAP_RECT_SIZE+1){
                g2d.fill(object.rect);
                g2d.drawImage(tankTexture, (int)object.rect.x, (int)object.rect.y, (int)object.rect.width-5, (int)object.rect.height-5, null);
            }
            else{
                g2d.drawImage(projectileTexture, (int)object.rect.x, (int)object.rect.y, (int)object.rect.width, (int)object.rect.height, null);
            }
        });
              
        ViewRequestsHandler.consolePrintln("Surface repainted");
    }

    /**
     * Setter for {@link Surface#background}
     * @param x
     * @param y
     * @param size 
     */
    public void setBackground(int x, int y, int size) {
        this.background = new Rectangle2D.Float(x, y, size, size);
    }

    /**
     * Setter for {@link Surface#exit}
     * @param x
     * @param y
     * @param size 
     */
    public void setExit(int x, int y, int size) {
        this.exit = new Rectangle2D.Float(x, y, size, size);
    }
    
    /**
     * Creates new wall with given params and adds it to {@link Surface#walls}
     * @param x
     * @param y
     * @param size 
     */
    public void addWalls(int x, int y, int size) {
        this.walls.add(new Rectangle2D.Float(x, y, size, size));
    }

    /**
     * Adds Drawable object to {@link Surface#objects}
     * @param object 
     */
    public void addObjects(DrawableObject object) {
        this.objects.add(object);
    }
    
    /**
     * Empties {@link Surface#objects}
     */
    public void resetObjects(){
        this.objects= new ArrayList<>();
    }
    
    private class MouseClickListener extends MouseAdapter{
        @Override
        public void mousePressed(MouseEvent e){
            int x = e.getX();
            int y = e.getY();
            
            System.out.println("Mouse clicked at "+x+" "+y);
            
            objects.stream().filter((object) -> (object.rect.contains(x, y))).forEach((object) -> {
                new Thread(() -> {
                    try{
                        Controller.objectClicked((int)object.rect.x, (int)object.rect.y);
                    }
                    catch(CrewNotSelectedException exception){
                        JOptionPane.showMessageDialog(Surface.this, exception.getMessage(), "Upozornenie", JOptionPane.INFORMATION_MESSAGE);
                    }
                }).start();
            });
        }
    }
}

