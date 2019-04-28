package Models;

import static DataAccess.Constant.NAMESPACE;

// toutes les classe qui hériteront de model seront la copie exacte des tables de la db
// on oublie qu'un héritier d'une classe abstraite doit impérativement implémenter
    // les methodes abstraites de sa classe mère
public abstract class Model {
    
    // renverra le nom de clef primaire PAS SA VALEUR
    public abstract String idKey();
    
    // permettra de set l'id sans se faire chier
    public abstract void idKey(Object key);
    
    // nom de base pour un model -> Models.nomDeLaClasse
    // on veut son nom en db soit : intervmaintenance.nomDeLaClasse
    // Pour PcUnit, ça renverra intervmaintenance.PcUnit
    // voir Constant dans DataAccess pour la valeur du NAMESPACE (intervmaintenance pour le coup)
    public String name(){
        return this.getClass().getName().replace("Models", NAMESPACE);
    }
}
