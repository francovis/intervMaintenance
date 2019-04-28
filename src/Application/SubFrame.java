package Application;

import Application.Utils.SharedScreenUtilities;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class SubFrame extends JFrame {
    private static SubFrame SUBFRAME = null;
    
    private SubFrame(JPanel toShow){
        super();
        var dim = new Dimension(700,500);
        this.setPreferredSize(dim);
        toShow.setPreferredSize(dim);
        toShow.setBorder(new EmptyBorder(20,20,20,20));
        this.add(toShow);
        this.setAlwaysOnTop(true);
        this.setBackground(SharedScreenUtilities.MAINTHEME);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
    }
    
    @Override
    public void dispose(){
        super.dispose();
        SUBFRAME = null;
    }
    
    public void update(){
        this.revalidate();
        this.repaint();
    }
    
    public static SubFrame get(JPanel toShow){
        if(SUBFRAME != null) SUBFRAME.dispose();
        return SUBFRAME = new SubFrame(toShow);
    }
    
    public static SubFrame get(){
        return SUBFRAME;
    }
    
}
