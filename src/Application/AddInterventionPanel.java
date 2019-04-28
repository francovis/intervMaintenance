package Application;

import Application.Utils.CustomButtonBase;
import Application.Utils.EventPanel;
import Models.Intervention;
import DataAccess.QHFactory;
import DataAccess.QueryHandler;
import Models.Fournisseur;
import Models.PcUnit;
import Models.TypeIntervention;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;


public class AddInterventionPanel extends EventPanel{
    
    private CustomButtonBase ajouter, annuler;
    private JLabel infos;
    private JComboBox pc, type, fournisseurIntervenant;
    private JCheckBox suiviViaFournisseur;
    private ButtonGroup resultat, etatInterv, etatRetour;
    private JPanel resultatPanel, etatIntervPanel, etatRetourPanel;
    private JTextField brefDescriptif, preneurEnCharge, signaleurIncident;
    private JSpinner signalement, contact, prise, retour, remiseService, tempsInterne;
    private String dateModel;
    
    public AddInterventionPanel(){
        super();
        this.dateModel = "dd/MM/yyyy";
        this.setLayout(new GridLayout(18,2));
        this.infos = new JLabel();
        this.infos.setForeground(Color.red);
        this.setup();
    }
    
    private void setup(){
        this.ajouter = new CustomButtonBase("Ajouter", JLabel.CENTER, 11);
        this.annuler = new CustomButtonBase("Annuler", JLabel.CENTER, 11);
        ajouter.on("click", this::ajouter);
        annuler.on("click", () -> SubFrame.get().dispose());
        this.setupAllComponent();
        this.addOnGrid(new JLabel("Date de signalement* : ", JLabel.RIGHT), signalement);
        this.addOnGrid(new JLabel("Problème en bref : ", JLabel.RIGHT), brefDescriptif);
        this.addOnGrid(new JLabel("Signaleur : ", JLabel.RIGHT), signaleurIncident);
        this.addOnGrid(new JLabel("Prise en charge* : ", JLabel.RIGHT), preneurEnCharge);
        this.addOnGrid(new JLabel("Etat* : ", JLabel.RIGHT), etatIntervPanel);
        this.addOnGrid(new JLabel("Suivi ia fournisseur* : ", JLabel.RIGHT), suiviViaFournisseur);
        this.addOnGrid(new JLabel("Date de contact : ", JLabel.RIGHT), contact);
        this.addOnGrid(new JLabel("Date prise : ", JLabel.RIGHT), prise);
        this.addOnGrid(new JLabel("Date retour : ", JLabel.RIGHT), retour);
        this.addOnGrid(new JLabel("Etat de retour : ", JLabel.RIGHT), etatRetourPanel);
        this.addOnGrid(new JLabel("Date de remise en service : ", JLabel.RIGHT), remiseService);
        this.addOnGrid(new JLabel("Temps interne(min) : ", JLabel.RIGHT), tempsInterne);
        this.addOnGrid(new JLabel("Résultat : ", JLabel.RIGHT), resultatPanel);
        this.addOnGrid(new JLabel("Founisseur intervenant : ", JLabel.RIGHT), fournisseurIntervenant);
        this.addOnGrid(new JLabel("PC* : ", JLabel.RIGHT), pc);
        this.addOnGrid(new JLabel("Type* : ", JLabel.RIGHT), type);
        this.add(ajouter);
        this.add(annuler);
        this.add(infos);
        
    }
    
    private void addOnGrid(JLabel label, JComponent field){
        this.add(label);
        this.add(field);
    }
    
    private void ajouter(){
        try {
            var intervention =this.getIntervention();
            QueryHandler<Intervention> queryHandler  = QHFactory.get(Intervention.class);
            ArrayList<Intervention> interventions = queryHandler.getAll();
            if(interventions.isEmpty())
                intervention.setNoInterv(1);
            else
                intervention.setNoInterv(interventions.get(interventions.size()-1).getNoInterv() + 1);
            queryHandler.insert(intervention);
            this.trigger("add");
            SubFrame.get().dispose();
        } catch (SQLException ex) {
            this.infos.setText("Veuillez remplir les champs obligatoires");
        }
    }
    
    private Intervention getIntervention(){
        var intervention = new Intervention();
        intervention.setSuiviViaFournisseur(suiviViaFournisseur.isSelected());
        if(intervention.getSuiviViaFournisseur()){
            intervention.setDateContact(((SpinnerDateModel)contact.getModel()).getDate());
            intervention.setDatePrise(((SpinnerDateModel)prise.getModel()).getDate());
            intervention.setDateRetour(((SpinnerDateModel)retour.getModel()).getDate());
            intervention.setEtatRetour(this.verifRadio(etatRetour));
        }
        else
            intervention.setTempsInterne((Integer)tempsInterne.getModel().getValue());
        intervention.setDateRemiseService(((SpinnerDateModel)remiseService.getModel()).getDate());
        intervention.setDateSignalement(((SpinnerDateModel)signalement.getModel()).getDate());
        intervention.setDescriptifBrefProblème(brefDescriptif.getText());
        intervention.setFkFournisseurIntervenant((Fournisseur)fournisseurIntervenant.getSelectedItem());
        intervention.setFkPcUnit((PcUnit)pc.getSelectedItem());
        intervention.setFkTypeInterv((TypeIntervention)type.getSelectedItem());
        intervention.setEtatInterv(this.verifRadio(etatInterv));
        intervention.setPreneurEnCharge(preneurEnCharge.getText());
        intervention.setRésultat(this.verifRadio(resultat));
        intervention.setSignaleurIncident(signaleurIncident.getText());       
        return intervention;
    }
    
    private String verifRadio(ButtonGroup group){
        var buttons = group.getElements();
        while(buttons.hasMoreElements()){
            var button = buttons.nextElement();
            if(button.isSelected())
                return button.getText();
        }
        return null;
    }

    private void setupAllComponent() {
        try{
            pc = new JComboBox(QHFactory.get(PcUnit.class).getAll().toArray());
            type = new JComboBox(QHFactory.get(TypeIntervention.class).getAll().toArray());
            var fournisseurs = QHFactory.get(Fournisseur.class).getAll();
            fournisseurs.add(0, null);
            fournisseurIntervenant = new JComboBox(fournisseurs.toArray());
        }
        catch(SQLException ex){
            pc = new JComboBox();
            type = new JComboBox();
            fournisseurIntervenant = new JComboBox();
        }
        suiviViaFournisseur = new JCheckBox();
        setPanelRadio(resultat = new ButtonGroup(), resultatPanel = new JPanel(), "Ok", "Déclassé", "En suspend");
        setPanelRadio(etatInterv = new ButtonGroup(), etatIntervPanel = new JPanel(), "Signalé", "En cours", "Cloturé");
        setPanelRadio(etatRetour = new ButtonGroup(), etatRetourPanel = new JPanel(), "Ok", "Déclassé", "En suspend");
        brefDescriptif = new JTextField();
        preneurEnCharge = new JTextField();
        signaleurIncident = new JTextField();
        signalement = new JSpinner(new SpinnerDateModel());
        signalement.setEditor(new JSpinner.DateEditor(signalement, dateModel));
        contact = new JSpinner(new SpinnerDateModel());
        contact.setEditor(new JSpinner.DateEditor(contact, dateModel));
        prise = new JSpinner(new SpinnerDateModel());
        prise.setEditor(new JSpinner.DateEditor(prise, dateModel));
        retour = new JSpinner(new SpinnerDateModel());
        retour.setEditor(new JSpinner.DateEditor(retour, dateModel));
        remiseService = new JSpinner(new SpinnerDateModel());
        remiseService.setEditor(new JSpinner.DateEditor(remiseService, dateModel));
        tempsInterne = new JSpinner(new SpinnerNumberModel());
        suiviViaFournisseur.addMouseListener(new MouseListener(){
            public void mouseClicked(MouseEvent arg0) {}
            public void mousePressed(MouseEvent arg0) {}
            public void mouseReleased(MouseEvent arg0) {
                check();
            }
            public void mouseEntered(MouseEvent arg0){}
            public void mouseExited(MouseEvent arg0){}
        });
        check();
    }
    
    private void check(){    
        contact.setEnabled(suiviViaFournisseur.isSelected());
        prise.setEnabled(suiviViaFournisseur.isSelected());
        retour.setEnabled(suiviViaFournisseur.isSelected());
        etatRetourPanel.setVisible(suiviViaFournisseur.isSelected());               
        tempsInterne.setEnabled(!suiviViaFournisseur.isSelected());
    }
    
    private void setRadioFor(JPanel panel, ButtonGroup group){
        var elements = group.getElements();
        while(elements.hasMoreElements())
            panel.add(elements.nextElement());
    }
    
    private void setPanelRadio(ButtonGroup group, JPanel panel, String ...values){
        panel.setLayout(new GridLayout(1, values.length));
        for(var str : values)
            group.add(new JRadioButton(str));
        setRadioFor(panel, group);
    }
}