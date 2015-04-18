
package View;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author kovko
 */
public class DrawableObject {
    
    public static final Color FIRST_TEAM_COLOR = Color.BLACK;
    public static final Color SECOND_TEAM_COLOR = Color.YELLOW;
    public static final Color CONTROLLED_TANK_COLOR = Color.GREEN;
    public static final Color PROJECTILE_COLOR = Color.MAGENTA;
    
    public final Color color;
    public final Rectangle2D.Float rect;

    public DrawableObject(Color color, Rectangle2D.Float rect) {
        this.color = color;
        this.rect = rect;
    }
    
    
}
