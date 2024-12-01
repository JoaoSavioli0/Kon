import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Id {
    public int generateIdUsuario() {
        Connection conn = null;
        String sql = "SELECT max(id) FROM usuario";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int id = 0;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            if(rs.next()){
                id = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            DatabaseConnection.closeResultSet(rs);
            DatabaseConnection.closeStatement(stmt);
            DatabaseConnection.closeConnection(conn);
        }
        return ++id;
    }

    public int generateIdSolicitacao() {
        Connection conn = null;
        String sql = "SELECT max(id) FROM solicitacao";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int id = 0;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            if(rs.next()){
                id = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            DatabaseConnection.closeResultSet(rs);
            DatabaseConnection.closeStatement(stmt);
            DatabaseConnection.closeConnection(conn);
        }
        return ++id;
    }

    public int generateIdComentario() {
        Connection conn = null;
        String sql = "SELECT max(id) FROM comentario";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int id = 0;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            if(rs.next()){
                id = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            DatabaseConnection.closeResultSet(rs);
            DatabaseConnection.closeStatement(stmt);
            DatabaseConnection.closeConnection(conn);
        }
        return ++id;
    }

}
