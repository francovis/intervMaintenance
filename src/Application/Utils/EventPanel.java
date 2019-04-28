package Application.Utils;

import Models.Intervention;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.swing.JPanel;

// cf. EventEmitter
public class EventPanel extends JPanel implements EventEmitter{
    private HashMap<String, Event> simpleEvents;
    
    public EventPanel(){
        super();
        simpleEvents = new HashMap<String, Event>();
    }
    
    @Override
    public void on(String eventName, Event event){
        simpleEvents.put(eventName, event);
    }
    
    @Override
    public void trigger(String eventName){
        var event = simpleEvents.get(eventName);
        if (event != null)
            event.handle();
    }

}
