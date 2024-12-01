import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class usuarioDAO<T> {

    protected String getInsertQuery() {
        return "CALL ADD_USUARIO(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }

    protected String getLimpaTabelaQuery() {
        return "CALL LIMPA_TABELA()";
    }

    protected String getUpdateQuery() {
        return "UPDATE usuario SET nome = ?, bairro = ?, cidade = ?, uf = ?, cpf = ?, senha = ?, telefone = ?, email = ?, nome_usuario = ?, tipo = ? WHERE cpf = ?";
    }

    protected String getBuscaPorIdQuery() {
        return "CALL busca_usuario_por_id(?)";
    }

    protected String getBuscaPorCpfQuery() {
        return "CALL busca_usuario_por_cpf(?)";
    }

    protected int numeroDeComentariosDoUsuario(int idUsuario) {
        String sql = "CALL num_comentarios_usuario(?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int numComentarios = 0;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idUsuario);
            rs = stmt.executeQuery();
            numComentarios = rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeResultSet(rs);
            DatabaseConnection.closeStatement(stmt);
            DatabaseConnection.closeConnection(conn);
        }
        return numComentarios;
    }

    protected void setParameters(PreparedStatement stmt, Usuario usuario) throws SQLException {
        stmt.setInt(1, usuario.getIdUsuario());
        stmt.setString(2, usuario.getNomeUsuario());
        stmt.setString(3, usuario.getBairroUsuario());
        stmt.setString(4, usuario.getCidadeUsuario());
        stmt.setString(5, usuario.getUfUsuario());
        stmt.setString(6, usuario.getCpfUsuario());
        stmt.setString(7, usuario.getSenhaUsuario());
        stmt.setString(8, usuario.getTelefone());
        stmt.setString(9, usuario.getEmail());
        stmt.setString(10, usuario.getNomeUser());
        stmt.setString(11, usuario.getTipoUsuario());
    }

    protected void setParametersUpdate(PreparedStatement stmt, Usuario usuario) throws SQLException {
        stmt.setString(1, usuario.getNomeUsuario());
        stmt.setString(2, usuario.getBairroUsuario());
        stmt.setString(3, usuario.getCidadeUsuario());
        stmt.setString(4, usuario.getUfUsuario());
        stmt.setString(5, usuario.getCpfUsuario());
        stmt.setString(6, usuario.getSenhaUsuario());
        stmt.setString(7, usuario.getTelefone());
        stmt.setString(8, usuario.getEmail());
        stmt.setString(9, usuario.getNomeUser());
        stmt.setString(10, usuario.getTipoUsuario());
        stmt.setString(11, usuario.getCpfUsuario());
    }

    protected Usuario getUsuarioFromResultSet(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario(
                rs.getString("nome"),
                rs.getString("cpf"),
                rs.getString("senha"),
                rs.getString("cidade"),
                rs.getString("bairro"),
                rs.getString("uf"),
                rs.getString("telefone"),
                rs.getString("email"),
                rs.getString("tipo"),
                rs.getInt("id"));
        
    
        if(rs.getDate("interacao_1") != null) usuario.setUltimoLike1(rs.getDate("interacao_1").toLocalDate());
        if(rs.getDate("interacao_2") != null) usuario.setUltimoLike2(rs.getDate("interacao_2").toLocalDate());

        return usuario;
    }

    public void deletaUsuario(int idUsuario) {
        String sql = "CALL REMOVE_USUARIO(?)";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idUsuario);
            stmt.execute();
            System.out.println("Usuário excluído!\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeStatement(stmt);
            DatabaseConnection.closeConnection(conn);           
        }
    }

    public void salvarUsuario(Usuario usuario) {
        String sql = getInsertQuery();
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            setParameters(stmt, usuario);
            stmt.executeUpdate();
            System.out.println("Usuário Salvo!\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeStatement(stmt);
            DatabaseConnection.closeConnection(conn);            
        }

    }

    public void atualizaDataInteracao(int idUsuario, LocalDate data, int numInteracao){
        String sql = numInteracao == 1 ? "UPDATE USUARIO SET INTERACAO_1 = ? WHERE ID = ?" : "UPDATE USUARIO SET INTERACAO_2 = ? WHERE ID = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setDate(1, java.sql.Date.valueOf(data));
            stmt.setInt(2, idUsuario);
            stmt.executeUpdate();
            System.out.println("Data de interação atualizada!\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeStatement(stmt);
            DatabaseConnection.closeConnection(conn);
        }
    }
    

    public void atualizaUsuario(Usuario usuario) {
        String sql = getUpdateQuery();
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            setParametersUpdate(stmt, usuario);
            stmt.executeUpdate();
            System.out.println("Usuário Atualizado!\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeStatement(stmt);
            DatabaseConnection.closeConnection(conn);            
        }
    }

    public Usuario buscaPorId(int id) {
        String sql = getBuscaPorIdQuery();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Usuario usuario = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                usuario = getUsuarioFromResultSet(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeResultSet(rs);
            DatabaseConnection.closeStatement(stmt);
            DatabaseConnection.closeConnection(conn);
        }

        return usuario;
    }

    public Usuario buscaPorCpf(String cpf) {
        String sql = getBuscaPorCpfQuery();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Usuario usuario = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, cpf);
            rs = stmt.executeQuery();

            if (rs.next()) {
                usuario = getUsuarioFromResultSet(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeResultSet(rs);
            DatabaseConnection.closeStatement(stmt);
            DatabaseConnection.closeConnection(conn);
        }

        return usuario;
    }
}
