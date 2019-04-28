package Application.Utils;

import DataAccess.QHFactory;
import Models.Model;
import static Utils.StringUtils.concat;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Stream;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;

// classe qui affiche les tables de la db
public class ModelTable extends JTable implements EventEmitter{
    // ce qui vient de la db
    private ArrayList models; 
    // cf.EventEmitter
    private HashMap<String, Event> events;
    public ModelTable(ArrayList models){
        super();
        this.events = new HashMap<String, Event>();
        this.models = models;
        // si ce qu'on a pris de la base de donnée n'est pas une liste vide, on affiche la table
        if(!models.isEmpty())
            setup();
        
    }
    
    // une ligne = un model
    public Object getSelectedModel(){
        if(models == null || models.isEmpty() || this.getSelectedRow() == -1)
            return null;
        return models.get(this.getSelectedRow());
    }
    
    // setup, création de la table graphique ...
    private void setup(){
        var tableModel = new TableModel();
        var font = new Font("Malgun Gothic", Font.PLAIN, 11);
        this.setModel(tableModel);
        this.setDragEnabled(false);   
        this.setBackground(SharedScreenUtilities.MAINTHEME);
        this.setForeground(SharedScreenUtilities.LABELCOLOR);
        this.setFont(font);
        this.setSelectionBackground(SharedScreenUtilities.LABELCOLOR);
        this.getTableHeader().setBackground(SharedScreenUtilities.LABELCOLOR);
        this.getTableHeader().setFont(font);
        this.setBorder(BorderFactory.createLineBorder(SharedScreenUtilities.LABELCOLOR));
        this.setGridColor(SharedScreenUtilities.LABELCOLOR);
        this.addMouseListener(new TableListener());
        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void on(String eventName, Event event){
        events.put(eventName, event);
    }
    
    @Override
    public void trigger(String eventName){
        var event = events.get(eventName);
        if (event != null)
            event.handle();
    }
    
    private class TableListener implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent arg0) {
        }

        @Override
        public void mousePressed(MouseEvent arg0) {}

        @Override
        public void mouseReleased(MouseEvent arg0) { 
            ModelTable.this.trigger("click");
        }

        @Override
        public void mouseEntered(MouseEvent arg0) {
        }

        @Override
        public void mouseExited(MouseEvent arg0) {
        }
    
    }
    // "Table", ce qui va être affiché
    private class TableModel extends AbstractTableModel{
        public Method[] getters;
        public TableModel(){
            // on prends tous les getters du model, trié par rdre alphabétique
            getters = Stream.of(models.get(0).getClass().getDeclaredMethods())
                    .filter(method -> method.getName().startsWith("get"))
                    .sorted((a,b) -> a.getName().compareToIgnoreCase(b.getName()))
                    .toArray(Method[]::new);
                    
        }
        
        @Override
        public int getRowCount() {
            return models.size();
        }
        
        // nom de la colonne = nom du getter sans le "get"
        @Override 
        public String getColumnName(int column){
            return getters[column].getName().replaceFirst("get","");
        }

        @Override
        public int getColumnCount() {
            return getters.length;
        }

        // pour l'affichage dans chaque cellule
        @Override
        public Object getValueAt(int x, int y) {
            try{
                var result = getters[y].invoke(models.get(x));
                // si result est une foreign key
                if (result !=null && result instanceof Model){
                    // on prend tout l'objet, on avait que la foreign key jusqu'à présent
                    var id = result.getClass().getMethod(concat("get",((Model)result).idKey())).invoke(result);
                    return QHFactory.get(((Model)result).getClass()).getById(id);
                }
                return result;
            }
            catch(Exception exception){
                return "";
            }
        }
        
        // on autorise pas l'edition de cellule
        @Override
        public boolean isCellEditable(int x, int y){
            return false;
        }
    
    }
}
