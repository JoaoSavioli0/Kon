import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Usuario {
    solicitacaoDAO solicitacaoDAO = new solicitacaoDAO<Solicitacao>();
    usuarioDAO usuarioDAO = new usuarioDAO<Usuario>();
    solicitacaolikeDAO solicitacaolikeDAO = new solicitacaolikeDAO<>();
    Id idGenerator = new Id();

    private int idUsuario;
    private String tipoUsuario;

    private String cpfUsuario;
    private String senhaUsuario;

    private String nomeUsuario;
    private String nomeUser;
    private String cidadeUsuario;
    private String bairroUsuario;
    private String ufUsuario;

    private String telefone;
    private String email;

    private LocalDate ultimoLike1;
    private LocalDate ultimoLike2;

    // Funções de Usuário

    public void addSolicitacao(String tituloSolicitacao, String descricaoSolicitacao, String bairroSolicitacao, int idUsuario) {
        solicitacaoDAO.salvarSolicitacao(new Solicitacao(tituloSolicitacao, descricaoSolicitacao, bairroSolicitacao, idUsuario));
    }

    public void addComentario(String comentario, int idSolicitacao, int idUsuario){
        solicitacaoDAO.AdicionaComentario(new Comentario(comentario, idSolicitacao, idUsuario));
    }


    public void addLike(Usuario usuario, int idSolicitacao) { // adiciona like na solicitacao do id

        LocalDate dataAtual = LocalDate.now(); // salva a data atual
        Solicitacao solicitacao = solicitacaoDAO.buscaSolicitacaoPorId(idSolicitacao);
        ArrayList<Integer> solicitacoesInteragidas = solicitacaolikeDAO.interacoesUsuario(usuario.getIdUsuario());


        if (usuario.getIdUsuario() == solicitacao.getIdUsuario()) { // ñ permite o usuario interagir com a propria solicitaçao
            System.out.println("\nUsuário não pode interagir com a própria solicitação!\n");
            return; // cancela o resto da funcao
        }
        

        if (solicitacoesInteragidas != null) {
            for (int idSolicitacaoInteragida : solicitacoesInteragidas) { // n pode interagir com a msm solicitacao
                System.out.println(idSolicitacaoInteragida);
                if (idSolicitacao == idSolicitacaoInteragida) {
                    System.out.println("\nUsuário já interagiu com essa solicitação\n");
                    return; // cancela o resto da funcao
                }
            }
        }

        if (this.ultimoLike1 == null || ChronoUnit.DAYS.between(this.ultimoLike1, dataAtual) >= 7) { // se o usuario ainda nao fez a 1 solicitacao, ou se ja se passou 7 dias
            
            this.ultimoLike1 = dataAtual; // salva a data atual na interacao 1
            usuarioDAO.atualizaDataInteracao(usuario.getIdUsuario(), dataAtual, 1);

        } else if (this.ultimoLike2 == null || ChronoUnit.DAYS.between(this.ultimoLike2, dataAtual) >= 7) { // se o usuario ainda n fez a 2 solicitacao ou se ja passou 7 dias

            this.ultimoLike2 = dataAtual; // salva a data atual na interacao 2
            usuarioDAO.atualizaDataInteracao(usuario.getIdUsuario(), dataAtual, 2);

        } else { // se as duas interacoes nao estiverem disponiveis

            long diasRestantes1 = 7 - ChronoUnit.DAYS.between(this.ultimoLike1, dataAtual); //calcula os dias restantes para uma nova solicitacao
            long diasRestantes2 = 7 - ChronoUnit.DAYS.between(this.ultimoLike2, dataAtual);

            long menorDiaRestante = diasRestantes1 > diasRestantes2 ? diasRestantes2 : diasRestantes1;

            System.out.println("\nVocê já interagiu com duas solicitações essa semana, espere " + menorDiaRestante + " dias para fazer uma nova interação.");

            return; // cancela o resto da funcao
        }

        solicitacaoDAO.addLikeSolicitacao(usuario.getIdUsuario(), idSolicitacao);
        System.out.println("Like adicionado na solicitação: " + idSolicitacao + " com sucesso!");

    }

    public String geraNomeUser(String nomeUsuario) {
        StringBuilder sb = new StringBuilder();
        String nomes[] = nomeUsuario.split(" ");
        sb.append(nomes[0]).append(" ").append(nomes[nomes.length - 1]);
        return sb.toString();
    }

    // Função Construtora e toString

    public Usuario(String nomeUsuario, String cpfUsuario, String senhaUsuario, String cidadeUsuario,
            String bairroUsuario, String uf, String telefone, String email, String tipoUsuario, int idUsuario) {
        if (idUsuario == 0) {
            this.idUsuario = idGenerator.generateIdUsuario();
        } else {
            this.idUsuario = idUsuario;
        }
        this.nomeUsuario = nomeUsuario;
        this.cpfUsuario = cpfUsuario;
        this.cidadeUsuario = cidadeUsuario;
        this.bairroUsuario = bairroUsuario;
        this.ufUsuario = uf;
        this.senhaUsuario = senhaUsuario;
        this.telefone = telefone;
        this.email = email;
        this.nomeUser = geraNomeUser(nomeUsuario);
        this.tipoUsuario = tipoUsuario;
    }

    //admin

    public void excluiSolicitacao(String tipoUsuario, int idSolicitacao) {
        if(tipoUsuario.equals("admin")){
            solicitacaoDAO.deletaSolicitacao(idSolicitacao);
        }else{
            System.out.println("Usuário não tem permissão!\n");
        }
    }

    public void excluiUsuario(String tipoUsuario, int idUsuario) {
        if(tipoUsuario.equals("admin")){
            usuarioDAO.deletaUsuario(idUsuario);
        }else{
            System.out.println("Usuário não tem permissão!\n");
        }
    }


    public String toString() {
        return "\nID:" + getIdUsuario() + "\nNome:" + getNomeUsuario() + "\nCPF:" + getCpfUsuario() + "\nCidade: "
                + getCidadeUsuario()
                + "\nBairro: "
                + getBairroUsuario();
    }

    // Getters e Setters

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getNomeUser() {
        return nomeUser;
    }

    public void setNomeUser(String nomeUser) {
        this.nomeUser = nomeUser;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getCidadeUsuario() {
        return cidadeUsuario;
    }

    public void setCidadeUsuario(String cidadeUsuario) {
        this.cidadeUsuario = cidadeUsuario;
    }

    public String getBairroUsuario() {
        return bairroUsuario;
    }

    public void setBairroUsuario(String bairroUsuario) {
        this.bairroUsuario = bairroUsuario;
    }

    public String getSenhaUsuario() {
        return senhaUsuario;
    }

    public void resetSenhaUsuario(String novaSenhaUsuario) {
        this.senhaUsuario = novaSenhaUsuario;
    }

    public String getCpfUsuario() {
        return cpfUsuario;
    }

    public void setCpfUsuario(String cpfUsuario) {
        this.cpfUsuario = cpfUsuario;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void alterarTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getUfUsuario() {
        return ufUsuario;
    }

    public void setUfUsuario(String ufUsuario) {
        this.ufUsuario = ufUsuario;
    }

    public LocalDate getUltimoLike2() {
        return ultimoLike2;
    }

    public void setUltimoLike2(LocalDate ultimoLike2) {
        this.ultimoLike2 = ultimoLike2;
    }

    public LocalDate getUltimoLike1() {
        return ultimoLike1;
    }

    public void setUltimoLike1(LocalDate ultimoLike1) {
        this.ultimoLike1 = ultimoLike1;
    }

    // Fim Getters e Setters
}
