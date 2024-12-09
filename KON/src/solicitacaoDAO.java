import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class solicitacaoDAO<T> {

    protected String getInsertQuery() {
        return "CALL add_solicitacao(?, ?, ?, ?, ?, ?)";
    }

    protected String getDeletaUsuarioQuery() {
        return "CALL REMOVE_USUARIO(?)";
    }

    protected String getUpdateQuery() {
        return "UPDATE solicitacao SET titulo = ?, bairro = ?, descricao = ? WHERE id = ?";
    }

    protected String getBuscaSolicitacaoQuery() {
        return "SELECT * FROM SOLICITACAO WHERE TITULO LIKE ? OR DESCRICAO LIKE ? OR BAIRRO LIKE ?";
    }

    protected String getNumComentariosSolicitacaoQuery() {
        return "CALL num_comentarios_solicitacao(?)";
    }

    protected void setParameters(PreparedStatement stmt, Solicitacao solicitacao) throws SQLException {
        stmt.setInt(1, solicitacao.getIdSolicitacao());
        stmt.setInt(2, solicitacao.getIdUsuario());
        stmt.setString(3, solicitacao.getTituloSolicitacao());
        stmt.setString(4, solicitacao.getDescricaoSolicitacao());
        stmt.setString(5, solicitacao.getBairroSolicitacao());
        stmt.setInt(6, solicitacao.getAnonimo());
    }

    protected void setParametersUpdate(PreparedStatement stmt, Solicitacao solicitacao) throws SQLException {
        stmt.setString(1, solicitacao.getTituloSolicitacao());
        stmt.setString(2, solicitacao.getBairroSolicitacao());
        stmt.setString(3, solicitacao.getDescricaoSolicitacao());
        stmt.setInt(4, solicitacao.getIdSolicitacao());
    }

    protected Solicitacao getSolicitacaoFromResultSet(ResultSet rs) throws SQLException {
        Solicitacao solicitacao = new Solicitacao(
                rs.getString("titulo"),
                rs.getString("descricao"),
                rs.getString("bairro"),
                rs.getInt("id_usuario"),
                rs.getInt("anonimo"));
        solicitacao.setNumComentarios(numeroDeComentariosDaSolicitacao(rs.getInt("id")));
        solicitacao.setDataAbertura(String.valueOf(rs.getTimestamp("data_abertura")));
        solicitacao.setDataConclusao(String.valueOf(rs.getTimestamp("data_conclusao")));
        solicitacao.setStatus(rs.getString("status_solicitacao"));
        solicitacao.setNumLikes(rs.getInt("num_likes"));
        return solicitacao;
    }

    protected Comentario getComentarioFromResultSet(ResultSet rs) throws SQLException{
        Comentario comentario = new Comentario(
            rs.getInt("id"),
            rs.getString("texto"),
            rs.getInt("id_solicitacao"),
            rs.getInt("id_usuario")
        );
        return comentario;
    }

    public void addLikeSolicitacao(int idUsuario, int idSolicitacao) {
        String sql = "CALL ADD_LIKE(?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idSolicitacao);
            stmt.execute();
            System.out.println("Like adicionado!\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeStatement(stmt);
            DatabaseConnection.closeConnection(conn);
        }
    }

    public void AdicionaComentario(Comentario comentario) {
        String sql = "CALL add_comentario(?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, comentario.getIdComentario());
            stmt.setInt(2, comentario.getIdSolicitacao());
            stmt.setInt(3, comentario.getIdUsuario());
            stmt.setString(4, comentario.getComentario());
            stmt.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeStatement(stmt);
            DatabaseConnection.closeConnection(conn);
            System.out.println("Comentário incluído!");
        }
    }

    public void deletaSolicitacao(int idSolicitacao) {
        String sql = "CALL REMOVE_SOLICITACAO(?)";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idSolicitacao);
            stmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeStatement(stmt);
            DatabaseConnection.closeConnection(conn);
            System.out.println("Solicitação excluída!\n");
        }
    }

    public void salvarSolicitacao(Solicitacao solicitacao) {
        String sql = getInsertQuery();
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            setParameters(stmt, solicitacao);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeStatement(stmt);
            DatabaseConnection.closeConnection(conn);
            System.out.println("Solicitação Salva!\n");
        }

    }

    public void atualizaSolicitacao(Solicitacao solicitacao) {
        String sql = getUpdateQuery();
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            setParametersUpdate(stmt, solicitacao);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeStatement(stmt);
            DatabaseConnection.closeConnection(conn);
            System.out.println("Solicitação Atualizada!\n");
        }
    }

    public ArrayList<Solicitacao> pesquisaSolicitacao(String key) {
        String sql = getBuscaSolicitacaoQuery();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Solicitacao> solicitacoesPesquisadas = new ArrayList<>();

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + key + "%");
            stmt.setString(2, "%" + key + "%");
            stmt.setString(3, "%" + key + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Solicitacao solicitacao = getSolicitacaoFromResultSet(rs);

                solicitacoesPesquisadas.add(solicitacao);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeResultSet(rs);
            DatabaseConnection.closeStatement(stmt);
            DatabaseConnection.closeConnection(conn);
        }

        return solicitacoesPesquisadas;
    }

    public ArrayList<Solicitacao> solicitacaoPorLike() {
        String sql = "SELECT * FROM SOLICITACAO ORDER BY NUM_LIKES DESC";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Solicitacao> solicitacoesPesquisadas = new ArrayList<>();

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Solicitacao solicitacao = getSolicitacaoFromResultSet(rs);

                solicitacoesPesquisadas.add(solicitacao);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeResultSet(rs);
            DatabaseConnection.closeStatement(stmt);
            DatabaseConnection.closeConnection(conn);
        }

        return solicitacoesPesquisadas;
    }

    public ArrayList<Solicitacao> solicitacaoPorProximidade(String bairro) {
        String sql = "SELECT * FROM SOLICITACAO WHERE bairro = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Solicitacao> solicitacoesPesquisadas = new ArrayList<>();

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, bairro);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Solicitacao solicitacao = getSolicitacaoFromResultSet(rs);

                solicitacoesPesquisadas.add(solicitacao);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeResultSet(rs);
            DatabaseConnection.closeStatement(stmt);
            DatabaseConnection.closeConnection(conn);
        }

        return solicitacoesPesquisadas;
    }

    public ArrayList<Solicitacao> solicitacaoPorData() {
        String sql = "SELECT * FROM SOLICITACAO ORDER BY data_abertura DESC";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Solicitacao> solicitacoesPesquisadas = new ArrayList<>();

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Solicitacao solicitacao = getSolicitacaoFromResultSet(rs);

                solicitacoesPesquisadas.add(solicitacao);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeResultSet(rs);
            DatabaseConnection.closeStatement(stmt);
            DatabaseConnection.closeConnection(conn);
        }

        return solicitacoesPesquisadas;
    }

    public Solicitacao buscaSolicitacaoPorId(int idSolicitacao) {
        String sql = "SELECT * FROM SOLICITACAO WHERE ID = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Solicitacao solicitacao = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idSolicitacao);
            rs = stmt.executeQuery();

            if (rs.next()) {
                solicitacao = getSolicitacaoFromResultSet(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeResultSet(rs);
            DatabaseConnection.closeStatement(stmt);
            DatabaseConnection.closeConnection(conn);
        }


        return solicitacao;
    }

    protected int numeroDeComentariosDaSolicitacao(int idSolicitacao) {
        String sql = "CALL num_comentarios_solicitacao(?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int numComentarios = 0;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idSolicitacao);
            rs = stmt.executeQuery();

            if (rs.next())
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

    public ArrayList<Comentario> comentariosSolicitacao(int idSolicitacao){
        String sql = "CALL solicitacao_comentarios(?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Comentario> comentarios = new ArrayList<>();

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idSolicitacao);

            rs = stmt.executeQuery();

            while(rs.next()){
                comentarios.add(getComentarioFromResultSet(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeResultSet(rs);
            DatabaseConnection.closeStatement(stmt);
            DatabaseConnection.closeConnection(conn);
        }
        return comentarios;
    }

    public void fechaSolicitacao(int idSolicitacao){
        String sql = "UPDATE solicitacao SET status_solicitacao = 'fechado' WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idSolicitacao);

            stmt.executeUpdate();
            System.out.println("Solicitação fechada!\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeStatement(stmt);
            DatabaseConnection.closeConnection(conn);
        }
    }

    public void imprimeSolicitacoes(ArrayList<Solicitacao> solicitacoes){
        for(Solicitacao solicitacao : solicitacoes){
            System.out.println(solicitacao);
        }
    }

    public void imprimeComentarios(int idSolicitacao){
        ArrayList<Comentario> comentarios = comentariosSolicitacao(idSolicitacao);

        for(Comentario comentario : comentarios){
            System.out.println(comentario);
        }
    }

}
