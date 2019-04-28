package Application;

import Models.Intervention;
import Application.Utils.StyledButton;
import java.util.ArrayList;

public class InterventionPanel extends MainPanel {
    private StyledButton add;
    private StyledButton delete;
    private StyledButton search;
    
    public InterventionPanel(){
        super();
        this.setup();
    }
    
    private void setup(){
        this.setTitle("Toutes les interventions");
        this.setButtons();
        this.setMainTable(Intervention.class);
    }
    
    private void setButtons(){ 
        this.createButtons();
        this.add.on("click", ()->{
            var panel = new AddInterventionPanel();
            panel.on("add", this::setTable);
            SubFrame.get(panel);
        });
        this.delete.on("click", ()->{
            var panel = new DeleteInterventionPanel();
            panel.on("remove", this::setTable);
            SubFrame.get(panel);
        });
        this.search.on("click", ()->{
            var panel = new RechercheInterventionPanel();
            panel.on("find", this::setFoundTable);
            SubFrame.get(panel);
        });
    }
    
    private void setFoundTable(ArrayList<Intervention> interventions){
        this.setTitle("Interventions de la précédente recherche");
        this.setMainTable(interventions);     
    }
    
    private void setTable(){
        this.setTitle("Toutes les interventions");
        this.setMainTable(Intervention.class);
    } 

    private void createButtons() {
        var constraints = this.getButtonsConstraints();
        this.delete = new StyledButton("Suppression");
        this.add = new StyledButton("Création");
        this.search = new StyledButton("Recherche");
        this.add.setPreferredSize(delete.getPreferredSize());
        this.search.setPreferredSize(delete.getPreferredSize());
        this.add(this.add, constraints);
        constraints.gridy++;
        this.add(this.search, constraints);
        constraints.gridy++;
        this.add(this.delete, constraints);
        constraints.gridy++;
    }
}