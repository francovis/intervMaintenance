package Application.Utils;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.function.Supplier;
import javax.swing.JLabel;


public class CustomButtonBase extends JLabel implements EventEmitter{
    
    private Color baseForeground;
    private Color baseBackground;
    // liaisons des evenements et de leur nom;
    private HashMap<String, Event> events;
    
    // nom du bouton + sa position + sa taille
    public CustomButtonBase(String label, int position, int size){
        super(label, position);
        this.events = new HashMap<String, Event>();
        // curseur de la souris -> main
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.setColor();
        // police + style d'ecriture
        this.setFont(new Font("Malgun Gothic", Font.PLAIN, size));
        this.addMouseListener(new DesignHandler());
    }
    
    // pour enrigistrer un potentiel event
    @Override
    public void on(String eventName, Event event){
        events.put(eventName, event);
    }
    
    // déclencher un event, si il existe ça go
    @Override
    public void trigger(String eventName){
        var event = events.get(eventName);
        if (event != null)
            event.handle();
    }
    
    
    // set les couleurs de base...
    public void setColor(){
        this.baseForeground = SharedScreenUtilities.LABELCOLOR;
        this.baseBackground = SharedScreenUtilities.MENUTHEME;   
        this.setBackground(this.baseBackground);
        this.setForeground(this.baseForeground);
        this.setOpaque(true);
    }

    public void setbaseBackground(Color baseBackground) {
        this.baseBackground = baseBackground;
    }

    public void setbaseForeground(Color baseForeground) {
        this.baseForeground = baseForeground;
    }

    public Color getbaseBackground() {
        return baseBackground;
    }

    public Color getbaseForeground() {
        return baseForeground;
    }
    
    // inversion des couleurs ...
    public void toggleColor(){
        if(this.baseBackground.equals(this.getBackground())){
            this.setBackground(baseForeground);
            this.setForeground(baseBackground);
        }
        else{
            this.setBackground(baseBackground);
            this.setForeground(baseForeground);            
        }
    }

    // trigger du click au press parce que mouseCliked() fonctionne pas au long click + deplacement par exemple
    private class DesignHandler implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent arg0) {
        }

        @Override
        public void mousePressed(MouseEvent arg0) {
            trigger("click");
        }

        @Override
        public void mouseReleased(MouseEvent arg0) {
        }

        @Override
        public void mouseEntered(MouseEvent arg0){
            // this reference toujours l'object englobbant,
            //on doit donc référer CustomButtonBase parce que l'englobbant est DesignHandler
            CustomButtonBase.this.toggleColor();
        }

        @Override
        public void mouseExited(MouseEvent arg0){
            CustomButtonBase.this.toggleColor();
        }
    }

}
