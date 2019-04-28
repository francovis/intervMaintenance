package Application;

import Application.Utils.CustomButtonBase;
import Application.Utils.EventPanel;
import Application.Utils.ModelTable;
import DataAccess.Constant;
import DataAccess.QHFactory;
import GestionMatInfoException.UnknownTypeException;
import Models.Intervention;
import Models.LotConfiguration;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;


public class DeleteInterventionPanel extends EventPanel{
    private ModelTable toDelete;
    private CustomButtonBase delete, confirm, cancel;
    private JLabel message;
    private JScrollPane toDeletePane;
    public DeleteInterventionPanel(){
        super();
        this.setLayout(new FlowLayout());
        this.setup();
    }

    private void setup(){
        JComboBox lots;
        toDeletePane = new JScrollPane();
        toDeletePane.setPreferredSize(new Dimension(700,300));
        toDeletePane.setBorder(new EmptyBorder(0,0,0,0));
        this.setDeleteButton();
        this.createFinalButtons();
        try{
            lots = new JComboBox(QHFactory.get(LotConfiguration.class).getAll().toArray());
        }
        catch(SQLException ex){
            lots = new JComboBox();
        }
        lots.setSelectedItem(null);
        lots.addItemListener(event -> this.changeDisplayList((LotConfiguration)event.getItem()));
        this.add(new JLabel("Lot de configuration : "));
        this.add(lots);
        this.add(toDeletePane);
    }
    
    private void changeDisplayList(LotConfiguration lot){
        this.toDeletePane.getViewport().removeAll();
        this.hideFinalButtons();
        this.remove(delete);
        try {
            this.toDelete = new ModelTable(QHFactory.get(Intervention.class).getFromJoin(Constant.DELETEJOIN, lot.getNoLot()));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        this.toDelete.on("click", ()->{
            this.remove(delete);
            this.hideFinalButtons();
            SubFrame.get().update();
        });
        this.toDeletePane.getViewport().add(toDelete);
        this.toDeletePane.setVisible(toDelete.getRowCount() != 0);
        SubFrame.get().update();
    }

    private void setDeleteButton() {
        delete = newButton("Supprimer");
        delete.on("click", ()->{
            delete.toggleColor();
            this.showFinalButtons();
        });
    }   
    
    private void showFinalButtons(){ 
        this.remove(delete);
        this.add(message);
        this.add(confirm);
        this.add(cancel);
        SubFrame.get().update();
    }
    
    private void createFinalButtons(){        
        message = new JLabel("Voulez-vous vraiment supprimer cette intervention ?");
        confirm = newButton("Confirmer");
        cancel = newButton("Annuler");
        confirm.on("click", ()->{
            try {
                QHFactory.get(Intervention.class)
                        .setModel((Intervention)toDelete.getSelectedModel())
                        .delete();
                this.trigger("remove");
            } catch (SQLException ex) {
                Logger.getLogger(DeleteInterventionPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnknownTypeException ex) {
                Logger.getLogger(DeleteInterventionPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            SubFrame.get().dispose();
        });
        cancel.on("click", ()->{
            cancel.toggleColor();
            this.hideFinalButtons();
        });
    }
    
    private void hideFinalButtons(){
        this.remove(message);
        this.remove(confirm);
        this.remove(cancel);
        this.add(delete);
        SubFrame.get().update();
    }
    
    public CustomButtonBase newButton(String text){
        var button = new CustomButtonBase(text, JLabel.CENTER, 20);
        button.setBorder(new EmptyBorder(10,10,10,10));
        return button;
    }
}
