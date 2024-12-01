

public class Comentario {
    Id id = new Id();

    private String comentario;

    private int idComentario;
    private int idSolicitacao;
    private int idUsuario;
    
    public Comentario(String comentario, int idSolicitacao, int idUsuario){
        this.comentario = comentario;
        this.idComentario = id.generateIdComentario();
        this.idSolicitacao = idSolicitacao;
        this.idUsuario = idUsuario;
    }

    public String toString(){
        return this.comentario;
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
