package DataAccess;

import java.sql.PreparedStatement;
import java.sql.SQLException;

// interface à une methode : réimplmentation à la volée (cf EventEmitter pour plus d'infos)
public interface OnPostTreatment {
    public void onPost(PreparedStatement preparedStatement, int increment) throws SQLException;
}
