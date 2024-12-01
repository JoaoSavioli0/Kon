import java.util.ArrayList;

public class Usuarios {

    private static Usuarios instance;
    private ArrayList<Usuario> usuarios;
    usuarioDAO usuarioDAO = new usuarioDAO<Usuario>();

    private Usuarios() {
        this.usuarios = new ArrayList<>();
    }

    public static Usuarios getInstance() {
        if (instance == null) {
            instance = new Usuarios();
        }
        return instance;
    }

    public void addUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public boolean removeUsuario(int IdUsuario, int tipoUsuario){
        if(tipoUsuario != 2){
            return false;
        }
        Usuario u = buscaUsuarioPorId(IdUsuario);
        if(u != null){
            return usuarios.remove(u);
        }
        return false;
    }

    public Usuario buscaUsuarioPorId(int id) {
        for (Usuario usuario : usuarios) {
            if (usuario.getIdUsuario() == id) {
                return usuario;
            }
        }
        System.out.println("Id de usuário não cadastrado!");
        return null;
    }

    public Usuario fazLogin(String cpfUsuario, String senhaUsuario) {
        Usuario usuario = usuarioDAO.buscaPorCpf(cpfUsuario);

        if(usuario != null){        
            if(usuario.getSenhaUsuario().equals(senhaUsuario)){
                System.out.println("Usuário logado!");
                return usuario;
            }else{
                System.out.println("Senha inválida!");
                return null;
            }
        }else{
            System.out.println("Usuário não encontrado para o CPF!");
            return null;
        }
        
    }

    public int getUltimoId() { // retorna ultimo id cadastrado, para ficar mais facil de salvar o novo usuario
                               // em uma variavel
        if (usuarios.size() > 0) {
            return usuarios.get(usuarios.size() - 1).getIdUsuario();
        } else {
            return -1;
        }
    }
}
