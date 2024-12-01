import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class solicitacaolikeDAO<T> {
    
    public ArrayList<Integer> interacoesUsuario(int idUsuario){
        String sql = "SELECT id_solicitacao FROM usuario_solicitacao_like where ID_USUARIO = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Integer> solicitacoesInteragidas = new ArrayList<>();

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idUsuario);
            rs = stmt.executeQuery();

            while(rs.next()){
                solicitacoesInteragidas.add(rs.getInt("id_solicitacao"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return solicitacoesInteragidas;
    }
}
