package DataAccess;

import GestionMatInfoException.UnknownTypeException;
import Models.Model;
import java.sql.*;
import java.util.ArrayList;
import java.util.stream.Stream;
import static Utils.StringUtils.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

// Plus gros morceau de l'app
// si vous pouvez répondre à votre prof sur cette classe, vous aurez surement réussi votre exam


// <T extends Model> : la classe ne va gérer que les classes qui extends Model
// ex : Intervention, PcUnit ...,
/* dans : new QueryHandler<Intervention>(Intervention.class) ou QHFactory.get(Intervention.class)
    --> T sera la représentation du type Intervention
*/
public class QueryHandler<T extends Model>{
    
    private T model;
    private final Connection connection;
    private final Class<T> modelClass;
    public QueryHandler(Class<T> modelClass) throws SQLException{
        // on envoie la classe en argument pour pouvoir créer des nouveaux
        // objets de la classe facilement (cf. méthode emptyModel())
        this.modelClass = modelClass;
        this.connection = DbConnection.connect();
    }
    
    
    public T getById(Object id) throws SQLException, UnknownTypeException{
        if (id == null) return null;
        var model = this.emptyModel();
        
        // on prépare la query SQL
        var statement = this.connection
                         .prepareStatement(concat("SELECT * FROM ", model.name()," WHERE ", model.idKey(),"=?"));
       
        //methode qui va déduire le type de l'argument envoyé et appeler la bonne methode du statement      
        this.statementSetter(statement, 1, id);
        var result = statement.executeQuery();
                            // par rapport aux valeurs reçues, on va remplir le model vide
        return result.next()?this.fillFromResult(result, model):null;
    }
    
    public ArrayList<T> getAll() throws SQLException{
        // nouvelle arraylist générique (Intervention si T représente Intervention, ...)
        var models = new ArrayList<T>();
        var statement = this.connection
                         .prepareStatement(concat("SELECT * FROM ", this.emptyModel().name()));
        var result = statement.executeQuery();
        
        // comme on prend tous les record de la db, 
        // on boucle sur chaque ligne du result pour remplir l'arraylist
        while(result.next()){
            
            // on envoie tj des emptyModel() pour avoir un nouvel objet à chaque fois
            models.add(this.fillFromResult(result, this.emptyModel()));
        }
        return models;
    }
    
    public ArrayList<T> getFromJoin(String customJoin, Object  ... clauses) throws SQLException{
        var models = new ArrayList<T>();
        var query = concat("SELECT ",
                this.emptyModel().name(),
                ".* FROM ", 
                this.emptyModel().name(),
                " ",
                customJoin
        );
        var statement = this.connection.prepareStatement(query);
        for (var i = 0;i<clauses.length;i++)
            try {
                //methode qui va déduire le type de l'argument envoyé et appeler la bonne methode du statement     
                this.statementSetter(statement, i+1, clauses[i]);
            } catch (UnknownTypeException ex) {
                System.out.println("clauses : que des primitifs svp!!");
            }
        var result = statement.executeQuery();
        while(result.next()){
            models.add(this.fillFromResult(result, this.emptyModel()));
        }
        return models;        
    }
    
    // jamais utilisée car pas demandée dans les consignes mais fonctionnelle
    public void update(Object oldId) throws SQLException{
        if(this.model != null && oldId != null){
            var methods = this.allModelGetter().toArray(Method[]::new);
            this.commonUpdateInsert(this.baseUpdate(this.model, methods),
                    this.model,
                    methods,
                    (statement, i) -> {
                        try {
                            this.statementSetter(statement, i, oldId);
                            statement.execute();
                        } catch (UnknownTypeException ex) {
                            Logger.getLogger(QueryHandler.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (SQLException ex) {
                            Logger.getLogger(QueryHandler.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
            );
        }
    }
    
    public void delete() throws SQLException, UnknownTypeException{
        if(this.model != null){
            var query = concat("DELETE FROM ",
                               this.model.name(),
                               " WHERE ",
                               this.model.idKey(),
                               "=?");
                    
            var statement = this.connection.prepareStatement(query);
           
            // on récupère l'id du model courant
            var id = this.modelGetter(this.model.idKey());
            
            //methode qui va déduire le type de l'argument envoyé et appeler la bonne methode du statement     
            this.statementSetter(statement, 1, id);
            
            // on supprime le record de la db
            statement.execute();
        }
    }
    
    public void insert(T model) throws SQLException{
        var methods = this.allModelGetter().toArray(Method[]::new);
        this.commonUpdateInsert(this.baseInsert(model, methods),
                model,
                methods,
                (statement, i) -> statement.execute()
        );
    }
    
    // par rapport à T, on prend tous ses getters (qui représentes les colonnes de la table dans la db)
    // et on construit une query qui sera valable pour n'importe quelle classe
    ///// jamais utilisée comme dit précédemment pour l'update mais fonctionnelle
    private String baseUpdate(Model model, Method[] methods){
        var query = new StringBuilder()
                    .append("UPDATE ")
                    .append(model.name())
                    .append(" SET ");
        for(var method : methods)
            query.append(method.getName().replaceFirst("get","")).append("=?, ");
        query.append("WHERE ")
             .append(model.idKey())
             .append(" = ?");
        return query.toString().replace(", WHERE ", " WHERE "); // on supprime l'imperfection du à la derneire boucle
    }
    
    // meme principe qu'au dessus, getter -> colonne db et on construit la query adéquate
    private String baseInsert(Model model, Method[] methods){
        var query = new StringBuilder()
                .append("INSERT INTO ")
                .append(model.name())
                .append(" (");
        var endQuery = new StringBuilder().append(" VALUES(");
        for (var method : methods){
            query.append(method.getName().replaceFirst("get", "")).append(",");
            endQuery.append("?,");
        }
        query.append(")").append(endQuery.toString()).append(")");
        return query.toString().replaceAll(",\\)",")");  // on supprime l'imperfection du à la derneire boucle
    }
       
    // par rapport au 2 query au dessus on va maintenant les remplir par rapport aux choix de l'utilisateur
    private void commonUpdateInsert(String query, T model, Method[] methods, OnPostTreatment onPost) throws SQLException{
        var statement = this.connection.prepareStatement(query);
        var i = 1;
        try{
            // pour chaque colonne grosso merdo
            for(var method : methods){
                // on recup la valeur que renvoie le getter dans le model
                var value = method.invoke(model);
                
                // on chope sont type
                var typeValue = this.getModelFieldType(model, normalize(method.getName().replaceFirst("get","")));
                
                // si value == null, il faut utilisé setNull() du statement en spécifiant le type sql
                if(value == null){
                    
                    // on recup le type sql depuis la map dans le fichier Constant
                    var sqlTypes = Constant.javaTypeToSQL();
                    var sqlType = sqlTypes.get(typeValue);
                    
                    // si on retrouve pas le type souhaité dans la map :  on est sur une foreign key
                    if(sqlType == null){
                        // on créée un objet foreign key vide,
                        //pour choper le nom de son id dans la db pour ensuite connaitre son type
                        var fkInstance = typeValue.getConstructor().newInstance();
                        sqlType = sqlTypes.get(typeValue.getMethod(concat("get",(String)typeValue.getMethod("idKey").invoke(fkInstance))).getReturnType());
                    }
                    // on setNull avec le type adéquat
                    statement.setNull(i++, sqlType);
                }
                else{
                    // si value est un Model (foreign key donc)
                    if(value instanceof Model)
                        // on chope sa clef primaire, pour ensuite lié dans la db ...
                        value = this.modelGetter((Model)value, ((Model)value).idKey());
                   
                    //methode qui va déduire le type de l'argument envoyé et appeler la bonne methode du statement     
                    this.statementSetter(statement, i++, value);
                }                
            }
            // cf. update et insert pour voir l'action du onPost
            // réimplémentation à la volée dans update et insert: (statement, i) -> ...
            onPost.onPost(statement, i);
        }
        catch (UnknownTypeException ex){} 
        catch (NoSuchMethodException ex) {
            Logger.getLogger(QueryHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(QueryHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(QueryHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(QueryHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(QueryHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(QueryHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    // set le model courant puis renvoie le QueryHandler pour faire des enchainement d'appel
    // ex: handler.setModel(unModelPrécédemmentInstancié).delete()
    public QueryHandler<T> setModel(T model){
        this.model = model;
        return this;
    }
    
    
    // on ne tombe jamais dans l'exception
    private T emptyModel(){
        try{
            // grace à la classe, on crée facilement un nouvel objet de la classe
            // sans connaître à l'avance son type
            return this.modelClass.getConstructor().newInstance();
        }
        catch(Exception e){
            return null;
        }
    }
    
    // par rapport à un resultSet, on remplit le model passé en argument
    // une fois remplis on renvoie le model;
    private T fillFromResult(ResultSet result, T model){
        // nom des setters = nom des colonnes dans la db sans le "set"
        // par rapport à tous les setters de T on sait donc comment est composé le resultSet
        // on ne tombe pas dans l'excpeption normalement
        this.allModelSetter().forEach(setter ->{
            try{
                // si on est pas sur une foreignkey : on invoke le setter sur le model pour le remplir avec la valeur du resultSet
                if(!setter.getName().startsWith("setFk"))
                    // resultSetGetter va chercher la colonne adéquate du resultSet par rapport au nom du setter
                    setter.invoke(model, this.resultSetGetter(result, setter.getName().replaceFirst("set", "")));
                else{
                    // on sait qu'on a affaire a une foreign key, donc ce qui est 
                    // stocké dans le résultSet pour la colonne du setter est d'office l'id de la foreign key
                    // encore inconnue
                    var id = this.resultSetGetter(result, setter.getName().replaceFirst("set", ""));
                    if(id != null){
                        // si l'id n'est pas null, 
                        // getModelFieldType() va deviner le type de la foreignKey
                        // puis on créée un nouvel objet vide qui raura le type de la fk
                        var fkModel = (Model)this.getModelFieldType(model, setter.getName().replaceFirst("setF","f"))
                         .getConstructor()
                         .newInstance();
                        // on set l'id de la fk vide
                        fkModel.idKey(id);
                        //enfin : on lie notre model de base avec sa fk
                        setter.invoke(model,fkModel);
                    }
                }  
            }
            catch(Exception e){
                e.printStackTrace();
            }
        });
        return model;
    }
    
    private String handleKnownType(Object object) throws UnknownTypeException{
        var fullName = object instanceof Class ? ((Class)object).getName():object.getClass().getName();
        /* si type primitf 
            java.util.Date
            java.sql.Date
            java.lang.Boolean
            java.lang.Integer
            java.lang.String
            j'utilise un regex (voir google) qui dit en gros :
            java.util ou lang ou sql.n'importe quoi suivant
            le nom des classes Model sont Models.nomDuModel donc pas de match avec le regex
        */
        if (fullName.matches("java\\.(util|lang|sql)\\..+")){
            // on remplace le préfixe pour récupéré juste String, Boolean etc
            var type = fullName.replaceFirst("java\\.(util|lang|sql)\\.", "");
            // puis on renvoie, si Integer on renvoie juste Int
            return type.equals("Integer")?"Int":type;
        }
        // on est sur une fk (suite dans handleUnknownType())
        throw new UnknownTypeException(fullName);
    }
    
    private String handleUnknownType(String field) throws Exception{
        var model = this.emptyModel();
        String type;
        try{
            // on ne connait pas le type du field
            // on tente handleKnownType pour voir si primitif
            type = this.handleKnownType(this.getModelFieldType(model, normalize(field)));
        } 
        catch(UnknownTypeException exception){
            // on est entré dans l'exception de handleKnownType, donc on sait qu'on a affaire a une fk
            // on créée cette fk (vide ici, parce qu'on veut juste le type de la clef primaire)
            var fkModel = (Model)Class.forName(exception.getMessage()).getConstructor().newInstance();
            // on retest handleKnownType mais la on sait que ça sera ok vu que la clef primaire d'un model
            // est tj primitive
            type = handleKnownType(this.getModelFieldType(fkModel, normalize(fkModel.idKey())));
            // pas de catch ici parce que throws Exception au niveau de la signature de la methode
        }
        return type.equals("Integer")?"Int":type;
    }
    
    private void statementSetter(PreparedStatement stmnt,
                                   int place, 
                                   Object value) throws UnknownTypeException{
        // identifier sera donc soit Int, Date, Boolean, String
        var identifier = this.handleKnownType(value);
        // on trigger le setter adéquate par rapport à l'identifier : setDate, setString ...
        this.triggerMethodFrom(stmnt, "set", identifier, place, value);
    }
    
    //meme princicpe pour un result set
    //la methode renvoie qqch vu que qu'ici c'est un getter et non un setter
    private Object resultSetGetter(ResultSet rs, String columnName) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException, UnknownTypeException, NoSuchFieldException, Exception{
        var identifier = handleUnknownType(columnName);
        return this.triggerMethodFrom(rs, "get", identifier, columnName);
    }
    
    // on prend tous les setters de T
    // stream c'est comme une liste sauf qu'il y a des methodes comme filter, sorted (assez pratique)
    private Stream<Method> allModelSetter(){
        return Stream.of(this.emptyModel().getClass().getDeclaredMethods()).filter(x -> x.getName().startsWith("set"));
    }
        
    // on prend tous les getters de T
    private Stream<Method> allModelGetter(){
        return Stream.of(this.emptyModel().getClass().getDeclaredMethods()).filter(x -> x.getName().startsWith("get"));
    }
    
    //applique un setter sur le model courant
    //jamais utilisé mais fonctionnel
    private void modelSetter(String identifier, Object toSet){
        this.modelSetter(this.model, identifier, toSet);
    }
    
    //applique un getter sur le model courant
    private Object modelGetter(String identifier){
        return this.modelGetter(this.model, identifier);
    }
    
    // applique un setter sur un model
    private void modelSetter(Model model, String identifier, Object toSet){
        this.triggerMethodFrom(model, "set", identifier, toSet);
    }
    
    //applique un getter sur un model
    private Object modelGetter(Model model, String identifier){
        return this.triggerMethodFrom(model, "get", identifier);
    }
    
    // pour un object cible, 
    // on invoke la methode par rapport au prefix + identifier 
    // on envoie eventuellement les arguements que pourrait prendre la methode voulant etre invoquée
    // ex : triggerMethodFrom(monsieur, "set", "age", 21);
    // on ne tombe pas dans l'exception
    private Object triggerMethodFrom(Object target, 
                                   String prefix, 
                                   String identifier,
                                   Object ...args) {
        try{
            // format pour les date (cf. formatArgs())
            args = formatArgs(args);
            // on va chercher la methode par rapport au prefix+identifier
            // la methode map du stream va encore formatter les arguements 
            // mais cette fois pour Interger et Boolean, si pas Interger ou Boolean: on prend les classes des arguements
            // pq Boolean et Integer ? si une methode prend en arguement int, elle acceptera pas Integer mais l'inverse fonctionne
            // idem pour Boolean
            // puis toArray: le map + toArray ici permet de tranformer notre tableau d'objet args 
            // en tableau de classe basé sur args
            // avec l'exemple ça donne : 
                //target.getClass().getDeclaredMethod("setAge",int.class).invoke(monsieur, 21);
                // c'est aussi chiant parce que java permet la surcharge de methode
                // c'est a dire que pour un nom de methode, tu peux avoir plusieurs signatures
            return target.getClass()
                    .getDeclaredMethod(concat(prefix,identifier), 
                            Stream.of(args)
                                  .map(arg -> arg instanceof Integer?int.class:
                                          arg instanceof Boolean?boolean.class:arg.getClass())
                                  .toArray(Class[]::new))
                    .invoke(target, args);
        }
        catch(Exception exception){
            return null;
        }
    }
    
    // on récupère le type d'un attribut d'un model
    private Class getModelFieldType(Model model, String fieldName){
        try{
            return model.getClass().getDeclaredField(fieldName).getType();
        }
        catch(Exception ex){
            return null;
        }
    }
    
    // soucis entre java.sql.Date et java.util.Date
    // donc on transforme tout java.util.Date rencontrer dans le tableau passer en argument
    // en java.Sql.Date
    private Object[] formatArgs(Object ...args){
        var formattedArgs = new Object[args.length];
        for(var i =0;i<args.length;i++){
            if (args[i] instanceof java.util.Date)
                args[i]= new java.sql.Date(((java.util.Date)args[i]).getTime());
            formattedArgs[i] = args[i];
        }
        return formattedArgs;
    }
}
