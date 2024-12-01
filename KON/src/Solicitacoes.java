import java.util.ArrayList;

public class Solicitacoes {

    private static Solicitacoes instance;
    private ArrayList<Solicitacao> solicitacoes;
    private ArrayList<Solicitacao> solicitacoesPorLike;

    private Solicitacoes() {
        this.solicitacoes = new ArrayList<>();
    }

    public static Solicitacoes getInstance() {
        if (instance == null) {
            instance = new Solicitacoes();
        }
        return instance;
    }

    public Solicitacao buscaSolicitacaoPorId(int id) {
        for (Solicitacao solicitacao : solicitacoes) {
            if (solicitacao.getIdSolicitacao() == id) {
                return solicitacao;
            }
        }
        return null;
    }

    public void addSolicitacao(Solicitacao solicitacao) {
        solicitacoes.add(solicitacao);
    }

    // public void exibeSolicitacoes(int filtro) {
    // if (solicitacoes.size() > 0) {
    // if (filtro == 1) {

    // for (Solicitacao solicitacao : this.solicitacoes) {
    // System.out.println(solicitacao);
    // }

    // } else if (filtro == 2) {
    // this.solicitacoesPorLike = new ArrayList<>(this.solicitacoes); // cria uma
    // copia para n alterar a
    // // original
    // solicitacoesPorLike.sort((s1, s2) -> Integer.compare(s2.numLikes,
    // s1.numLikes)); // comando para ordenar
    // // decrescente

    // for (Solicitacao solicitacao : this.solicitacoesPorLike) {
    // System.out.println(solicitacao);
    // }

    // }
    // } else {
    // System.out.println("\nNão há solicitações para exibir no momento...");
    // }

    // }

    // public void like(int idSolicitacao) {
    // buscaSolicitacaoPorId(idSolicitacao).numLikes++;
    // }

    // public void addComentario(int idSolicitacao, Comentario comentarioUsuario,
    // int idUsuario){
    // Solicitacao s = buscaSolicitacaoPorId(idSolicitacao);
    // s.numComentarios++;
    // if(s.comentariosSolicitacao == null){
    // s.comentariosSolicitacao = new ArrayList<>();
    // }
    // s.comentariosSolicitacao.add(comentarioUsuario);
    // }

    public boolean removeSolicitacao(int idSolicitacao, int tipoUsuario) {
        if (tipoUsuario != 2) {
            return false;
        }
        Solicitacao s = buscaSolicitacaoPorId(idSolicitacao);
        if (s != null) {
            return solicitacoes.remove(s);
        }
        return false;
    }

}
