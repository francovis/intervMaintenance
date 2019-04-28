package Application;

import Application.Utils.CustomButtonBase;
import Application.Utils.EventPanel;
import DataAccess.Constant;
import DataAccess.QHFactory;
import Models.Fournisseur;
import Models.Intervention;
import Models.TypeIntervention;
import java.awt.Color;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Consumer;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSpinner;


public class RechercheInterventionPanel extends EventPanel {
    
    private HashMap<String, Consumer<ArrayList<Intervention>>> events;
    private JComboBox tri, localité, typeInterv;
    private JSpinner tempsJour;  
    private JLabel labelLoc, labelType, labelTemps, infos;
    private CustomButtonBase chercher;
    public RechercheInterventionPanel(){
        super();
        this.setLayout(new GridLayout(8,1));
        this.setup();
    }
    
    private void setup(){
        this.setTri();
        this.setLabels();
        this.add(new JLabel("Tri : "));
        this.add(tri);
        this.setSearch();
        events = new HashMap();
    }
    
    private void setSearch(){        
        chercher = new CustomButtonBase("Chercher", JLabel.CENTER, 20);
        chercher.on("click", ()->{                
            var list = this.getResearch();
            if(list.isEmpty()){
                this.add(infos); 
                SubFrame.get().update();
            }                   
            else{
                this.trigger("find", list);
                SubFrame.get().dispose();
            }
        });
    }
    
    private ArrayList<Intervention> getResearch(){
        String query;
        Object[] args;
        if(tri.getSelectedItem().equals("Localité de fournisseur")){
            query = Constant.LOCALITEJOIN;
            args = new Object[]{localité.getSelectedItem()};
        }
        else{
            query = Constant.TYPEINTERVJOIN;
            args = new Object[]{
                ((TypeIntervention)typeInterv.getSelectedItem()).getLibelleTypeInt(), 
                tempsJour.getValue()
            };
        }
        try{
            return QHFactory.get(Intervention.class).getFromJoin(query, args);
        }
        catch(SQLException exception){
            return new ArrayList<Intervention>();
        }       
                
    }
    
    private void setLabels(){
        labelLoc = new JLabel("Localité cible : ");
        labelType = new JLabel("Type d'intervention : ");
        labelTemps = new JLabel("À partir de (jours) : ");
        infos = new JLabel("Aucun resultat !");
        infos.setForeground(Color.red);
    }
    
    private void setTri(){        
        tri = new JComboBox();
        tri.addItem("Localité de fournisseur");
        tri.addItem("Type d'intervention");
        tri.setSelectedItem(null);
        tri.addItemListener(event -> this.changeTri());
    }
    
    private void changeTri(){
        this.clean();
        if(tri.getSelectedItem().equals("Type d'intervention"))
            this.setTypeInterv();
        else
            this.setLoc();
        this.add(chercher);
        SubFrame.get().update();
    }
    
    private void clean(){
        this.remove(labelLoc,
                    localité,
                    typeInterv,
                    tempsJour,
                    labelTemps,
                    labelType,
                    chercher);
    }
    
    public void on(String eventName, Consumer<ArrayList<Intervention>> event){
        events.put(eventName, event);
    }
    
    private void trigger(String eventName, ArrayList<Intervention> toCarry){
        var event = events.get(eventName);
        if(event != null)
            event.accept(toCarry);
    }
    
    private void remove(JComponent ...components){
        for(var component :  components)
            try{
                this.remove(component);
            }
            catch(NullPointerException exception){}
    }

    private void setTypeInterv(){
        try{
            typeInterv = new JComboBox(QHFactory.get(TypeIntervention.class).getAll().toArray(TypeIntervention[]::new));
        } catch (SQLException exception){            
            typeInterv = new JComboBox();
        }
        tempsJour = new JSpinner();
        this.add(labelType);
        this.add(typeInterv);
        this.add(labelTemps);
        this.add(tempsJour);
    }    

    private void setLoc() {
        try{
            ArrayList<Fournisseur> fourns = QHFactory.get(Fournisseur.class).getAll();
            // un Set peut pas avoir 2 fois la même entrée, donc si 2 localités pareil OSEF
            var localités = new HashSet<String>();
            fourns.forEach(fournisseur -> localités.add(fournisseur.getLocalFourn()));
            localité = new JComboBox(localités.toArray(String[]::new));
        }catch(SQLException exception){
            localité = new JComboBox();
        }
        this.add(labelLoc);
        this.add(localité);
    }
}