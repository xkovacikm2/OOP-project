package View;

import controller.Controller;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
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
    
    private BufferedImage wallTexture;
    private BufferedImage exitTexture;
    private BufferedImage tankTexture;
    private BufferedImage projectileTexture;
    private Color backgroundColor;
    
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
        } catch (IOException ex) {
            Logger.getLogger(Surface.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            exitTexture = ImageIO.read(new File("images/exit.png"));
        } catch (IOException ex) {
            Logger.getLogger(Surface.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            tankTexture = ImageIO.read(new File("images/tank.png"));
        } catch (IOException ex) {
            Logger.getLogger(Surface.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            projectileTexture = ImageIO.read(new File("images/projectile.png"));
        } catch (IOException ex) {
            Logger.getLogger(Surface.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                    try{
                        Controller.objectClicked((int)object.rect.x, (int)object.rect.y);
                    }
                    catch(Exception exception){
                        JOptionPane.showMessageDialog(Surface.this, exception.getMessage(), "Upozornenie", JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;
                }
            }
        }
    }
}

