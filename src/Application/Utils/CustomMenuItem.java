package Application.Utils;

import Application.MainFrame;
import Application.MainPanel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.function.Supplier;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;


public class CustomMenuItem extends CustomButtonBase{
    // pour les couleurs
    private boolean isFixed;
    // titre au dessus de la table, panel de redirection, si le bouton est séléectionner pas defaut
    public CustomMenuItem(String title, Supplier<MainPanel> redirection, boolean isFixed){
        super(title, SwingConstants.RIGHT, 20);
        if(isFixed)this.fixColor();
        this.setBorder(new EmptyBorder(25,0,25,50));
        this.addMouseListener(new ItemClick(redirection));
    }
    // on fix la couleur + envoie d'event
    public void fixColor(){
        this.trigger("fix");
        this.isFixed = true;
        this.setBackground(SharedScreenUtilities.MENUITEMTHEME);
    }
    // on remet la couleur de base
    public void unfix(){
        this.setBackground(SharedScreenUtilities.MENUTHEME);
        this.setForeground(SharedScreenUtilities.LABELCOLOR);
        this.isFixed = false;
    }   
    
    // on inverse les couleurs
    @Override
    public void toggleColor(){
        if(!isFixed)
            this.setBackground(
                    this.getbaseBackground().equals(this.getBackground())?
                            SharedScreenUtilities.MENUITEMTHEME:
                            this.getbaseBackground()
            );
    }
    // mouse listener pour gérer les redirection + changement de couleurs aux click et au passage au dessus par la souris
    private class ItemClick implements MouseListener {
        private Supplier<MainPanel> redirection;

        public ItemClick(Supplier<MainPanel> redirection) {
            this.redirection = redirection;
        }

        @Override
        public void mouseClicked(MouseEvent arg0) {  

        }

        @Override
        public void mousePressed(MouseEvent arg0) {
            if(redirection == null)
                MainFrame.getFrame().dispose();                
            else
                MainFrame.getFrame().setMain(redirection.get());
            CustomMenuItem.this.fixColor();

        }

        @Override
        public void mouseReleased(MouseEvent arg0) {

        }

        @Override
        public void mouseEntered(MouseEvent arg0) {

        }

        @Override
        public void mouseExited(MouseEvent arg0) {          
            
        }
    }
}
