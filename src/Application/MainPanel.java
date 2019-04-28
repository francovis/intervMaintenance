package Application;

import Application.Utils.ModelTable;
import Application.Utils.SharedScreenUtilities;
import DataAccess.QHFactory;
import Models.Model;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class MainPanel extends JPanel{

    private GridBagConstraints constraint;
    private JLabel title;
    private JScrollPane mainTable;
    
    public MainPanel(){
        super();
        this.constraint = new GridBagConstraints();
        this.setBackground(SharedScreenUtilities.MAINTHEME);
        this.setPreferredSize(SharedScreenUtilities.mainPanelDim());
        this.setLayout(new GridBagLayout());
    }
    
    public JScrollPane getMainTable(){
        return this.mainTable;
    }
    
    public JViewport getMainViewPort(){
        return this.mainTable.getViewport();
    } 
    
    public GridBagConstraints getConstraint(){
        return this.constraint;
    }
    
    public GridBagConstraints getTitleConstraint(){
        constraint.insets = new Insets(5,10,5,5);
        constraint.gridx = 0;
        constraint.gridy = 0;
        constraint.weightx = GridBagConstraints.HORIZONTAL;
        constraint.fill = GridBagConstraints.HORIZONTAL;
        return constraint;
    }
    
    private void createMainTitle(String title) {
        if(this.title == null){
            var constraint = this.getTitleConstraint();
            this.title = new JLabel(title, SwingConstants.CENTER);
            this.title.setForeground(SharedScreenUtilities.LABELCOLOR);
            this.title.setFont(new Font("Malgun Gothic", Font.PLAIN, 40));
            this.add(this.title,constraint);
            constraint.gridy++;
        }
    }
    
    public void setTitle(String newTitle){
        if(this.title == null)
            createMainTitle(newTitle);
        else
            this.title.setText(newTitle);
    }
    
    public GridBagConstraints getButtonsConstraints(){
        var constraint = this.getConstraint();
        constraint.fill = 0;
        constraint.weightx = 0;
        constraint.gridx = 1;
        constraint.ipady = 10;
        constraint.ipadx = 5;
        constraint.anchor = GridBagConstraints.EAST;
        return constraint;
    }
    
    public GridBagConstraints getTableConstraint(){
        constraint.gridheight = constraint.gridy;
        constraint.gridwidth = 1;
        constraint.fill = GridBagConstraints.BOTH;
        constraint.gridx = 0;
        constraint.gridy=1;
        constraint.anchor = GridBagConstraints.CENTER;
        return constraint;
    }   

    public void setMainTable(Class<? extends Model> toView) {
        try {
            this.setMainTable(QHFactory.get(toView).getAll());
        } catch (SQLException ex) {
            this.setTitle("Pas de résultat");
        }
    }
    
    public void setMainTable(ArrayList<? extends Model> models){
        if(this.mainTable == null){
            this.mainTable = new JScrollPane();
            var constraint = this.getTableConstraint();
            this.getMainViewPort().setBackground(SharedScreenUtilities.MAINTHEME);
            this.mainTable.setBorder(new EmptyBorder(0,0,0,0));
            this.getMainViewPort().add(new ModelTable(models));
            this.add(this.mainTable, constraint);
        }
        else{
            this.updateMainTable(models);
        }
    }
    
    private void updateMainTable(ArrayList<? extends Model> models){
        this.getMainViewPort().removeAll();
        this.getMainViewPort().add(new ModelTable(models));
    }
}
