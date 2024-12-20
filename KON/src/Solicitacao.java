
public class Solicitacao {
    Id idGenerator = new Id();
    usuarioDAO usuarioDAO = new usuarioDAO();

    private int idSolicitacao;

    private String tituloSolicitacao;
    private String descricaoSolicitacao;
    private String bairroSolicitacao;
    private String nomeUsuarioSolicitacao;

    private String dataAbertura;
    private String dataConclusao;

    private String status;
    private int anonimo;

    private int numLikes;
    private int numComentarios;

    private int idUsuario;

    public Solicitacao(String tituloSolicitacao, String descricaoSolicitacao, String bairroSolicitacao, int idUsuario, int anonimo) {
        this.tituloSolicitacao = tituloSolicitacao;
        this.descricaoSolicitacao = descricaoSolicitacao;
        this.bairroSolicitacao = bairroSolicitacao;
        this.idUsuario = idUsuario;
        this.nomeUsuarioSolicitacao = usuarioDAO.buscaPorId(idUsuario).getNomeUser();
        this.idSolicitacao = idGenerator.generateIdSolicitacao();
        this.anonimo = anonimo;
    }

    public String toString() {
        if (this.getAnonimo() == 0){
            return "\nUsuario: " + this.nomeUsuarioSolicitacao + "\nBairro: " + this.bairroSolicitacao + "\n\n"
            + this.tituloSolicitacao + " - " + this.numLikes + " likes "
            + "\n\n" + this.descricaoSolicitacao + "\n---------------------------";
        } else{
            return "\nUsuario: Anônimo\nBairro: " + this.bairroSolicitacao + "\n\n"
                + this.tituloSolicitacao + " - " + this.numLikes + " likes "
                + "\n\n" + this.descricaoSolicitacao + "\n---------------------------";
        }
        
    }



    //Getters e setters

    public int getIdSolicitacao() {
        return idSolicitacao;
    }

    public String getTituloSolicitacao() {
        return tituloSolicitacao;
    }

    public void setTituloSolicitacao(String tituloSolicitacao) {
        this.tituloSolicitacao = tituloSolicitacao;
    }

    public String getDescricaoSolicitacao() {
        return descricaoSolicitacao;
    }

    public void setDescricaoSolicitacao(String descricaoSolicitacao) {
        this.descricaoSolicitacao = descricaoSolicitacao;
    }

    public String getBairroSolicitacao() {
        return bairroSolicitacao;
    }

    public void setBairroSolicitacao(String bairroSolicitacao) {
        this.bairroSolicitacao = bairroSolicitacao;
    }

    public String getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(String dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public String getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(String dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(int numLikes) {
        this.numLikes = numLikes;
    }

    public int getNumComentarios() {
        return numComentarios;
    }

    public void setNumComentarios(int numComentarios) {
        this.numComentarios = numComentarios;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getAnonimo() {
        return anonimo;
    }

    public void setAnonimo(int anonimo) {
        this.anonimo = anonimo;
    }

    
}
