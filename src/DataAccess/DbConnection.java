package DataAccess;

import java.sql.*;
import static DataAccess.Constant.CONNECTIONSTRING;
import static DataAccess.Constant.USERNAME;
import static DataAccess.Constant.PASSWORD;

// final: ici, la classe ne peut pas être héritée, dans le fond osef mais ça fait classe
public final class DbConnection {
    private static Connection connection;
    
    // singleton = une seule et unique instance de connection
    public static Connection connect() throws SQLException{
        if(DbConnection.connection == null)
            DbConnection.connection = DriverManager.getConnection(CONNECTIONSTRING,USERNAME,PASSWORD);
        return DbConnection.connection;
   
    }
}
