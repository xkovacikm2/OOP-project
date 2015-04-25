package View;

import controller.Controller;
import controller.ControllerEvent;
import controller.FocusSetter;
import java.awt.BorderLayout;
import java.awt.TextArea;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import model.basic.Main;

/**
 *
 * @author kovko
 */
public class GUI extends JFrame{
    
    public static final int WIDTH = 1080;
    public static final int HEIGHT = 1000;
    public static final int BUTTON_OFFSET = 10;
    public static final int BUTTON_HEIGHT = 20;
    public static final int BUTTON_WIDTH = 120;
    
    private JPanel menuDefault;
    private JPanel menuSetFocus;
    private TextArea console;
    
    private JButton moveLoop;
    private JButton switchToSetFocusMenu;
    private JButton switchToDefaultMenu;
    private JButton tankDriverFocusSetter;
    private JButton tankGunnerFocusSetter;
    
    public GUI(Surface surface){
        
        setTitle("Warsong Tank Edition");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLayout(new BorderLayout());
        setMenus();
        setConsole();
        add(surface, BorderLayout.CENTER);
        add(menuDefault, BorderLayout.EAST);
        add(console, BorderLayout.SOUTH);
        addKeyListener(new KeyPressListener());
        setFocusable(true);
    }
    
    public void consoleWrite(String s){
        console.append(s);
    }
    
    private void setConsole(){
        console = new TextArea();
        console.setSize(WIDTH, 200);
    }
    
    private void setMenus(){
        menuDefault = new JPanel();
        setMenuDefaultButtons();

        menuSetFocus = new JPanel();
        setMenuSetFocusButtons();
    }
        
    private void switchMenus(JPanel toActivate, JPanel toDeactivate){
        remove(toDeactivate);
        add(toActivate, BorderLayout.EAST);
        revalidate();
        repaint();
    }
    
    private JButton createButton(String name, JPanel parent){
        JButton button = new JButton(name);
        int x = parent.getBounds().x + (BUTTON_WIDTH*(parent.getComponentCount()%2))+(BUTTON_OFFSET*(parent.getComponentCount()%2));
        int y = parent.getBounds().y + (BUTTON_HEIGHT*(parent.getComponentCount()/2))+(BUTTON_OFFSET*(parent.getComponentCount()/2));
        button.setBounds(x, y, BUTTON_WIDTH, BUTTON_HEIGHT);
        parent.add(button);
        return button;
    }
    
    private void setMenuDefaultButtons(){
        switchToSetFocusMenu = createButton("Vybrat tank", menuDefault);
        switchToSetFocusMenu.addActionListener(e->{switchMenus(menuSetFocus, menuDefault);});
        
        moveLoop = createButton("Ukončiť ťah", menuDefault);
        moveLoop.addActionListener(e->{Main.mainLoop();});
    };
    
    private void setMenuSetFocusButtons(){
        switchToDefaultMenu = createButton("Spat", menuSetFocus);
        switchToDefaultMenu.addActionListener(e->{switchMenus(menuDefault, menuSetFocus);});
        
        tankDriverFocusSetter = createButton("Vodic", menuSetFocus);
        tankDriverFocusSetter.addActionListener(e->{Controller.setClickedId(FocusSetter.ID_TANK_DRIVER); switchMenus(menuDefault, menuSetFocus);});
        
        tankGunnerFocusSetter = createButton("Strelec", menuSetFocus);
        tankGunnerFocusSetter.addActionListener(e->{Controller.setClickedId(FocusSetter.ID_TANK_GUNNER); switchMenus(menuDefault, menuSetFocus);});
    }
    
    private class KeyPressListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent event){
            System.out.println("KeyPress detected");
            switch(event.getKeyCode()){
                case KeyEvent.VK_UP: 
                    new Thread(() -> Controller.notify(new ControllerEvent(ControllerEvent.UP_ARROW))).start(); break;
                case KeyEvent.VK_DOWN:
                    new Thread(() -> Controller.notify(new ControllerEvent(ControllerEvent.DOWN_ARROW))).start(); break;
                case KeyEvent.VK_LEFT:
                    new Thread(() -> Controller.notify(new ControllerEvent(ControllerEvent.LEFT_ARROW))).start(); break;
                case KeyEvent.VK_RIGHT:
                    new Thread(() -> Controller.notify(new ControllerEvent(ControllerEvent.RIGHT_ARROW))).start(); break;
                default: ;
            }
        }
    }
}
