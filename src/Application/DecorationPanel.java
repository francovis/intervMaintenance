package Application;

import Application.Utils.SharedScreenUtilities;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class DecorationPanel extends JPanel {
    public DecorationPanel(){
        super();
        this.setPreferredSize(SharedScreenUtilities.decoDim());
        this.setLayout(new FlowLayout(FlowLayout.LEADING,0,0));
        this.setBackground(SharedScreenUtilities.MAINTHEME);
        this.setupControlButton();
    }
    
    private void setupControlButton(){
        var closeButton = new JLabel("<html><p style='font-size:130%;'>X</p></html>");
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.setForeground(SharedScreenUtilities.LABELCOLOR);
        closeButton.setBorder(new EmptyBorder(10,8,10,0));
        closeButton.addMouseListener(new OnClickDecoration());
        closeButton.setPreferredSize(new Dimension(this.getPreferredSize().width,40));
        this.add(closeButton);
    }

    private static class OnClickDecoration implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            MainFrame.getFrame().dispose();
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }
}
