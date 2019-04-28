package Application;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame{
    private static MainFrame mainFrame;    
    private JPanel main;
    private MainFrame(){
        super();
        this.main = null;
        this.setUpBase();
        this.setVisible(true);        
    }
    
    public static MainFrame getFrame(){
        if(MainFrame.mainFrame == null)
            MainFrame.mainFrame = new MainFrame();
        return MainFrame.mainFrame;
    }
    
    private void setUpMenu(){
        var constraint = new GridBagConstraints();
        constraint.gridx = 1;
        constraint.fill = GridBagConstraints.VERTICAL;
        this.add(new CustomMenu(), constraint);
    }
    
    private void update(){
        this.revalidate();
        this.repaint();
    }
    
    public void setMain(JPanel newPanel){
        if(this.main != null) this.remove(main);        
        var constraint = new GridBagConstraints();
        constraint.gridx = 2;
        this.main = newPanel;
        this.add(this.main, constraint);
        this.update();
    }
    
    private void setUpBase(){
        this.setUndecorated(true);
        this.getContentPane().setLayout(new GridBagLayout());//new FlowLayout());
        this.setUpMenu();
        this.setUpDecoration();
        this.setMain(new HomePanel());
        this.pack();
    }
    
    private void setUpDecoration(){
        var constraint = new GridBagConstraints();
        constraint.gridx = 3;
        this.add(new DecorationPanel(), constraint);
    }
    
    @Override
    public void dispose(){
        super.dispose();   
        System.exit(0);
    }

}
