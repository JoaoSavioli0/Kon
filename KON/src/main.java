import java.util.ArrayList;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Usuarios usuariosManager = Usuarios.getInstance();
        Solicitacoes solicitacaoManager = Solicitacoes.getInstance();
        usuarioDAO usuarioDAO = new usuarioDAO<Usuario>();
        solicitacaoDAO solicitacaoDAO = new solicitacaoDAO<Solicitacao>();
        Scanner scn = new Scanner(System.in);
        ArrayList<Solicitacao> solicitacoes = new ArrayList<>();


        

        // Usuario usuario = usuariosManager.fazLogin("20246969881", "abc");

        // scn.nextLine();

        // usuario.addSolicitacao("Água faltando toda semana", "Ficamos sem água todo final de semana no bairro, isso é inadimissível.", "Votorantim", usuario.getIdUsuario(), 0);

        // scn.nextLine();

        // System.out.println(solicitacaoDAO.buscaSolicitacaoPorId(1));

        // scn.nextLine();

        // usuario.addLike(usuario, 1);

        // scn.nextLine();

        // usuario.addLike(usuario, 1);

        // scn.nextLine();

        // usuario.addLike(usuario, 4);

        // scn.nextLine();

        // usuario.addLike(usuario, 2);

        // scn.nextLine();

        // usuario.addLike(usuario, 3);

        // scn.nextLine();

        // usuario.addComentario("Concordo", 1, usuario.getIdUsuario());

        // scn.nextLine();

        // solicitacaoDAO.imprimeComentarios(1);

        // scn.nextLine();

        


        // System.out.println("Próximos");
        // solicitacaoDAO.imprimeSolicitacoes(solicitacaoDAO.solicitacaoPorProximidade(usuario.getBairroUsuario()));
        
        // scn.nextLine();

        // System.out.println("Em destaque");
        // solicitacaoDAO.imprimeSolicitacoes(solicitacaoDAO.solicitacaoPorLike());

        // scn.nextLine();

        // System.out.println("Recentes");
        // solicitacaoDAO.imprimeSolicitacoes(solicitacaoDAO.solicitacaoPorData());

        // scn.nextLine();

        // System.out.println("Respostas para: caçambas");
        // solicitacaoDAO.imprimeSolicitacoes(solicitacaoDAO.pesquisaSolicitacao("caçambas"));

        // scn.nextLine();



        // usuario = usuariosManager.fazLogin("40172440840", "123");

        // scn.nextLine();

        // usuario.fechaSolicitacao(usuario.getTipoUsuario(), 1);

        // scn.nextLine();

        // usuario.excluiSolicitacao(usuario.getTipoUsuario(), 2);

        // scn.nextLine();

        // usuario.excluiUsuario(usuario.getTipoUsuario(), 2);




        

        // Usuario usuario = new Usuario("João Pedro Savioli", "40172440840", "123", "Sorocaba", "Jardim Aeroporto", "SP", "11998986898", "joao.kon@gmail.com", "admin", 0);

        // usuarioDAO.salvarUsuario(usuario);

        // usuario.addSolicitacao("Destruíram os locais de descarte público", "As caçambas públicas do bairro x estão parecendo um lixão, ninguém respeita mais, precisamos de vigias para assegurarem a integridade do local", "Jardim Aeroporto", usuario.getIdUsuario(), 0);


        // usuario = new Usuario("Alexandre Batista Remo", "12345678910", "321", "Sorocaba", "Campolim", "SP", "11948210041", "alexandre.kon@gmail.com", "usuario", 0);

        // usuarioDAO.salvarUsuario(usuario);

        // usuario.addSolicitacao("O semáforo está quebrado há duas semanas.", "O semáforo da rua x está quebrado há duas semanas, gerando dificuldade para quem atravessa por lá em horários de movimento, em duas semanas já aconteceram dois acidentes. Precisam tomar previdências logo!", "Campolim", usuario.getIdUsuario(), 0);

        // usuario.addSolicitacao("A rua x precisa de uma lombada", "Os carros na rua x estão correndo demais, está perigoso para as crianças que saem da escola e atravesam ali.", "São Luiz", usuario.getIdUsuario(), 1);


        // usuario = new Usuario("Daniel Cravo da Costa", "20246969881", "abc", "Sorocaba", "Votorantim", "SP", "15993901942", "daniel.kon@gmail.com", "usuario", 0);

        // usuarioDAO.salvarUsuario(usuario);



    }

}
