package Application;

import Application.Utils.StyledButton;
import Models.*;
import static Utils.StringUtils.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import javax.swing.border.EmptyBorder;


public class AllTablePanel extends MainPanel {
    public AllTablePanel(){
        super();
        this.setup();
        this.setBorder(new EmptyBorder(-80,0,0,0));
    }
    
    private void setup(){
        this.setTitle("Listing : Configuration");
        this.setButtons();
        this.setMainTable(ConfigurationPc.class);
    }
    
    private HashMap<String, Class<? extends Model>> createActions(){
        var actions = new HashMap<String, Class<? extends Model>>();
        actions.put("Configuration", ConfigurationPc.class);
        actions.put("Fournisseur", Fournisseur.class);
        actions.put("Image", Imagesw.class);
        actions.put("Intervention", Intervention.class);
        actions.put("Lot de configurations", LotConfiguration.class);
        actions.put("Pc Unit", PcUnit.class);
        actions.put("Type d'interventions", TypeIntervention.class);
        return actions;
    }

    private void setButtons(){
        var buttonSize = new StyledButton("Lot de configurations").getPreferredSize();
        var actions = this.createActions();
        var constraint = this.getButtonsConstraints();
        var sortedLabel = new ArrayList<String>(actions.keySet());
        Collections.sort(sortedLabel);
        sortedLabel.forEach(key -> {
            var button = new StyledButton(key);
            button.on("click", ()->{
                this.setTitle(concat("Listing : ",key));
                this.setMainTable(actions.get(key));
            });
            button.setPreferredSize(buttonSize);
            this.add(button,constraint);
            constraint.gridy++;
        });
    }
}