package Application;

import Application.Utils.CustomMenuItem;
import Application.Utils.SharedScreenUtilities;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class CustomMenu extends JPanel {
    
    private ArrayList<CustomMenuItem> menus;
    
    public CustomMenu(){
        super();
        this.menus = new ArrayList<CustomMenuItem>();
        this.setBackground(SharedScreenUtilities.MENUTHEME);        
        this.setPreferredSize(SharedScreenUtilities.menuDim());
        this.setBorder(new EmptyBorder(-25,0,0,0));
        this.setLayout(new GridBagLayout());
        this.setupItems();
    }

    private void setupItems() {
        var constraint = new GridBagConstraints();
        constraint.fill = GridBagConstraints.HORIZONTAL;
        constraint.weightx = 4;
        constraint.gridx = 0;
        constraint.gridy = 0;
        // ajout des menus + leurs redirections
        menus.add(new CustomMenuItem("Acceuil",HomePanel::new, true));
        menus.add(new CustomMenuItem("Interventions",InterventionPanel::new, false));
        menus.add(new CustomMenuItem("Toutes les tables",AllTablePanel::new, false));
        menus.add(new CustomMenuItem("Quitter", null, false));
        menus.forEach(
                menu -> {
                    menu.on("fix", ()->menus.forEach(menu2 -> menu2.unfix()));
                    this.add(menu, constraint);
                    constraint.gridy++;
                }
        ); 
    }   
}