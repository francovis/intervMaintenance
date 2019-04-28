package DataAccess;

import Models.Model;

// Factory parce que "usine" à QueryHandler
public class QHFactory {
    
    // Création simplifée d'un queryHandler
    // on ne tombe jamais dans l'exception
    public static <T extends Model> QueryHandler get(Class<T> _class){
        try{
            return new QueryHandler<T>(_class);
        }
        catch(Exception exception){
            return null;
        }
    }   
    
}
