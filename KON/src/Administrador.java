
public class Administrador extends Usuario {
    usuarioDAO usuarioDAO = new usuarioDAO<Usuario>();
    solicitacaoDAO solicitacaoDAO = new solicitacaoDAO<Solicitacao>();

    public Administrador(String nomeUsuario, String cpfUsuario, String senhaUsuario, String cidadeUsuario,
            String bairroUsuario, String uf, String telefone, String email, String tipoUsuario, int idUsuario) {
        super(nomeUsuario, cpfUsuario, senhaUsuario, cidadeUsuario, bairroUsuario, uf, telefone, email, tipoUsuario,
                idUsuario);
    }

    
}
