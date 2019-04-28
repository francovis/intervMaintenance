package Application.Utils;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;


// CustomButtonBase avec un effet de couleur un peu différent (inversion de couleur + bordure au passage de la souris)
public class StyledButton extends CustomButtonBase{
    public StyledButton(String text){
        super(text, SwingConstants.CENTER ,20);
        this.setupColor();
    }
    
    private void setupColor(){
        this.setBorder(BorderFactory.createMatteBorder(5,5,5,5,SharedScreenUtilities.LABELCOLOR));
        this.setbaseBackground(SharedScreenUtilities.LABELCOLOR);   
        this.setBackground(SharedScreenUtilities.LABELCOLOR);   
        this.setbaseForeground(SharedScreenUtilities.MAINTHEME); 
        this.setForeground(SharedScreenUtilities.MAINTHEME);
    }
}
