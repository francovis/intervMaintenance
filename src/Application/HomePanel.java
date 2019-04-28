package Application;

import Application.Utils.SharedScreenUtilities;
import java.awt.Font;
import java.util.List;
import javax.swing.JLabel;

public class HomePanel extends MainPanel{
    public HomePanel(){
        super();
        var constraint = this.getConstraint();
        List<JLabel> labels = List.of(new JLabel("BIENVENUE "),
                                    new JLabel("Cette application permet de tout faire ! "),
                                    new JLabel("Ou presque..."),
                                    new JLabel("En tout cas, on sait gérer des interventions.")                
                                );
        constraint.gridx = 0;
        constraint.gridy = 0;
        labels.forEach(label -> {
            label.setFont(new Font("Gill Sans MT",Font.ITALIC, 50));
            label.setForeground(SharedScreenUtilities.LABELCOLOR);
            this.add(label, constraint);
            constraint.gridy++;
        });
    }
}
