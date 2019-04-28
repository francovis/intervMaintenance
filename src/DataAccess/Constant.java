package DataAccess;

import static Utils.StringUtils.concat;
import java.util.HashMap;
import java.util.Map;
import static java.sql.Types.*;
import java.util.Date;


public final class Constant {
    public static final String CONNECTIONSTRING = "jdbc:mysql://localhost:3306/?useSSL=false&serverTimezone=UTC";
    public static final String USERNAME = "nathan";
    public static final String PASSWORD = "sturtz";
    public static final String NAMESPACE = "intervmaintenance";
    
    // toutes les queries jugées non générique, utilisées dans les recherche et delete
    public static final String DELETEJOIN = 
            concat( "INNER JOIN ",NAMESPACE,".PcUnit pc on pc.IdPcUnit = ",NAMESPACE,".intervention.FkPcUnit ",
                    "INNER JOIN ",NAMESPACE,".lotconfiguration lot on lot.NoLot = pc.FkLot ",
                    "WHERE lot.NoLot = ?;");
    public static final String TYPEINTERVJOIN=
            concat( "INNER JOIN ",NAMESPACE,".typeintervention t ",
                    "ON t.CodeTypeInt = ",NAMESPACE,".intervention.FkTypeInterv ",
                    "WHERE t.LibelleTypeInt = ? ",
                    "AND DATEDIFF(",NAMESPACE,".intervention.DateRemiseService, ",
                    NAMESPACE,".intervention.DateSignalement) > ?;");
    public static final String LOCALITEJOIN=
            concat( "INNER JOIN ",NAMESPACE,".Fournisseur f ",
                    "ON f.FournisseurId = ",NAMESPACE,".intervention.fkFournisseurIntervenant ",
                    "WHERE ",NAMESPACE,".intervention.EtatInterv = 'En cours' AND f.LocalFourn = ?;");
    
    // permet de faire un parallele de type en Java et MySQL
    // map : pour une clef on a une valeur
    public static final Map<Class, Integer> javaTypeToSQL(){
        var javaTypeToSQL = new HashMap<Class, Integer>();
        javaTypeToSQL.put(String.class, VARCHAR);
        javaTypeToSQL.put(Integer.class, INTEGER);
        javaTypeToSQL.put(Boolean.class, BOOLEAN);
        javaTypeToSQL.put(Date.class, DATE);
        javaTypeToSQL.put(java.sql.Date.class, DATE);
        return javaTypeToSQL;
    }
    
}
