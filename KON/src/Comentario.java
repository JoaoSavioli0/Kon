

public class Comentario {
    Id id = new Id();
    usuarioDAO usuarioDAO = new usuarioDAO<>();

    private String comentario;

    private int idComentario;
    private int idSolicitacao;
    private int idUsuario;

    private String nomeUsuarioComentario;
    
    public Comentario(int idComentario, String comentario, int idSolicitacao, int idUsuario){
        this.comentario = comentario;
        this.idComentario = idComentario == 0 ? id.generateIdComentario() : idComentario;
        this.idSolicitacao = idSolicitacao;
        this.idUsuario = idUsuario;
        this.nomeUsuarioComentario = usuarioDAO.buscaPorId(idUsuario).getNomeUser();
    }

    public String toString(){
        return "\n" + this.nomeUsuarioComentario + "\n" + this.comentario;
    }


    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(int idComentario) {
        this.idComentario = idComentario;
    }

    public int getIdSolicitacao() {
        return idSolicitacao;
    }

    public void setIdSolicitacao(int idSolicitacao) {
        this.idSolicitacao = idSolicitacao;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    


}
