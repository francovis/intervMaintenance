package Application.Utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

// classe qui permet de retrouver le theme, dimension etc ...
public class SharedScreenUtilities {
    public static final Color LABELCOLOR = Color.WHITE;
    public static final Color MAINTHEME = new Color(36, 33, 36);
    public static final Color MENUITEMTHEME = new Color(52,52,52);
    public static final Color MENUTHEME = new Color(59, 60, 54);
    public static final Dimension SCREENSIZE = Toolkit.getDefaultToolkit().getScreenSize();
    public static Dimension menuDim(){
        return new Dimension(((Double)(SCREENSIZE.width*0.20)).intValue(), SCREENSIZE.height-1);
    }
    public static Dimension mainPanelDim(){
        return new Dimension(((Double)(SCREENSIZE.width*0.78)).intValue(), SCREENSIZE.height-1);
    }
    public static Dimension decoDim(){
        var rest = SCREENSIZE.width -
                ((((Double)(SCREENSIZE.width*0.3)).intValue() + 
                ((Double)(SCREENSIZE.width*0.68)).intValue()));
        // -1 sinon c'est fullScreen sans barre de tâche
        return new Dimension(rest, SCREENSIZE.height-1);
    }
}
