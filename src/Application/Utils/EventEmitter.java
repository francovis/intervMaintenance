package Application.Utils;
// interface pour gérer les evenements dans les parents plutot que dans les enfants, plutot pratique
public interface EventEmitter {
    // referencement d'un evenement pour un nom
    public void on(String eventName, Event supplier);
    // déclenchement d'un évenement référencé
    // si pas d'event reférencé pour le nom entré: OSEF
    public void trigger(String eventName);
    
    // interface dans l'interface, on l'utilisera jamais autrement que dans la méthode on() d'EventEmitter
    static interface Event{
        public void handle();
    }
    
    /*Exemple d'utilisation, dans n'importe quelle classe :
    implements EventEmitter sur une classe qui veut émettre un évènement
    
    
    ----> Dans la classe implémentant EventEmitter :
    @Override
    public void on(String eventName, Event event){
        // on store l'event dans une map
        // map : une clef pour une valeure, ici : pour un nom j'aurai un évènement qui sera référencé
        map.put(eventName, event)
    }
    
    @Override trigger(String eventName){
        event = map.get(eventName); // on prend dans la map, si pas trouvé, la map renvoit null
        if(event != null) // Si on a trouvé un event on le déclenche
            event.handle();
    }
    
    ----> DANS LE PARENT : 
    eventEmitter = new ClasseQuiImplementsEventEmitter(); .....
    // l'interface Event n'a qu'une méthode à réimplémenter
    // le language permet de faire de l'implémentation à la volée pour ces petites interfaces
    // () : les arguements, dépendant du nombre d'argument dans la méthode de l'interface,
            si 3 args : (a,b,c) ...
    // -> : implique, comme dans les maths logiques, ceci implique cela
            donc : mes arguments implique telle implémentation
    // après la flèche : le corps implémenté, 
                         si une ligne, on peut juste mettre la ligne comme ça
                         si plusieurs ligne, on dois entoutrer par {}
    
    
    //Maintenant on va référencer des évènements
    eventEmitter.on("testEvent", ()->System.out.println("bonjour"));
    eventEmitter.on("plusieursLigne", ()->{
        System.out.println("sur");
        System.out.println("plusieurs");
        System.out.println("lignes");
    });
    
    // pour les déclenché :
    // soit depuis la parent
    eventEmitter.trigger("testEvent");
    eventEmitter.trigger("plusieursLigne");
    
    // soit depuis l'enfant 
       ce qui est super intéressant : 
       dans le parent on implémente le on(), on sait ce qu'il va se passer,
       si on trigger dans le parent, autant directement mettre le corps à éxécuter ... ça reviens au meme
       si c'est l'enfant qui trigger un evènement, 
       il ne sait pas forcément ce que le parent
       veut de lui + l'enfant ne peut pas facilement modifier le parent;
    
    this.trigger("testEvent");
    this.trigger("plusieursLigne");
    --> on va donc chercher dans la map l'event
    */
}
