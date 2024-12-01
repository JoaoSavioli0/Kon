import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class genericDAO<T> {
    
    protected abstract String getInsertQuery();

    protected abstract String getUpdateQuery();

    protected abstract String getDeleteQuery();

    protected abstract String getSelectQuery();

    protected abstract void setParameters(PreparedStatement stmt, T entity) throws SQLException;

    protected abstract T getEntityFromResultSet(ResultSet rs) throws SQLException;

    public void salvar(T entity){
        String sql = getInsertQuery();
        executeUpdate(sql, entity);
    }

    public void atualizar(T entity){
        String sql = getUpdateQuery();
        executeUpdate(sql, entity);
    }

    public void deletar(String identifier){
        String sql = getDeleteQuery();
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, identifier);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeStatement(stmt);
            DatabaseConnection.closeConnection(conn);
        }
    }

    public T buscaPorId(int id){
        String sql = getSelectQuery();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        T entity = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if(rs.next()){
                entity = getEntityFromResultSet(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeResultSet(rs);
            DatabaseConnection.closeStatement(stmt);
            DatabaseConnection.closeConnection(conn);
        }

        return entity;
    }

    public void executeUpdate(String sql, T entity){
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            setParameters(stmt, entity);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeStatement(stmt);
            DatabaseConnection.closeConnection(conn);            
        }
    }
}
