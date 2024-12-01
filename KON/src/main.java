import java.util.ArrayList;

public class main {
    public static void main(String[] args) {
        Usuarios usuariosManager = Usuarios.getInstance();
        Solicitacoes solicitacaoManager = Solicitacoes.getInstance();
        usuarioDAO usuarioDAO = new usuarioDAO<Usuario>();
        solicitacaoDAO solicitacaoDAO = new solicitacaoDAO<Solicitacao>();
        Administrador admin = null;
        int idSessao = 0;
        ArrayList<Solicitacao> solicitacoes = new ArrayList<>();

        // usuarioDAO.deletaUsuario(2);

        // Usuario usuario = new Usuario("João Pedro Savioli", "40172440840", "123", "Itu", "Jardim Aeroporto", "SP", "11998986898", "joao.kon@gmail.com", "admin", 0);

        // usuarioDAO.salvarUsuario(usuario);

        // Solicitacao solicitacao = new Solicitacao("Destruíram os locais de descarte público", "As caçambas públicas do bairro x estão parecendo um lixão, ninguém respeita mais, precisamos de vigias para assegurarem a integridade do local", "bairro do divino salvador", usuario.getIdUsuario());

        // solicitacaoDAO.salvarSolicitacao(solicitacao);


        // usuario = new Usuario("Maria Rita Savioli", "12345678910", "321", "Itu", "São Judas", "SP", "11948210041", "maria.kon@gmail.com", "usuario", 0);

        // usuarioDAO.salvarUsuario(usuario);

        // solicitacao = new Solicitacao("Destruíram os locais de descarte público", "As caçambas públicas do bairro x estão parecendo um lixão, ninguém respeita mais, precisamos de vigias para assegurarem a integridade do local", "bairro do divino salvador", usuario.getIdUsuario());

        // solicitacaoDAO.salvarSolicitacao(solicitacao);


        // usuario = new Usuario("Daniel Cravo da Costa", "20246969881", "abc", "Sorocaab", "Votorantim", "SP", "15993901942", "daniel.kon@gmail.com", "usuario", 0);

        // usuarioDAO.salvarUsuario(usuario);

        // solicitacao = new Solicitacao("Destruíram os locais de descarte público", "As caçambas públicas do bairro x estão parecendo um lixão, ninguém respeita mais, precisamos de vigias para assegurarem a integridade do local", "bairro do divino salvador", usuario.getIdUsuario());

        // solicitacaoDAO.salvarSolicitacao(solicitacao);

        Usuario usuario = usuariosManager.fazLogin("40172440840", "123");


        usuario.excluiUsuario(usuario.getTipoUsuario(), 3);

        // solicitacoes = solicitacaoDAO.buscaSolicitacao("caçamba");

        // for (Solicitacao solicitacao : solicitacoes) {
        // System.out.println(solicitacao);
        // }

        //usuario.addLike(usuario, 1);

        

        // 

        // Comentario comentario = new Comentario("Concordo, no começo as caçambas
        // ajudavam muito e eram bem cuidadas", 4,
        // idSessao);
        // usuarioDAO.AdicionaComentario(comentario);

        // Registrando usuarios
        // usuarioDAO.salvarUsuario(usuario);

        // usuario = new Usuario("João Pedro de Oliveira Savioli", "40172440841", "123",
        // "Itu", "Jardim aeroporto II", "11978521152", "joao.kon@gmail.com", 'c');

        // usuarioDAO.atualizaUsuario(usuario, usuario.getCpfUsuario());

        // login no usuario1
        // usuario = usuariosManager.fazLogin("40172440840", "321");
        // if(usuario.getTipoUsuario() == 'a'){
        // admin = (Administrador) usuario;
        // }

        // Adiciona solicitações como usuario1
        // usuario.addSolicitacao("Aumentar a rua", "A rua está muito estreita", "Jardim
        // aeroporto II");

        // login admin
        // usuario = usuariosManager.fazLogin("admin", "admin");
        // if(usuario.getTipoUsuario() == 'a'){
        // admin = (Administrador) usuario;
        // }

        // Exclui solicitacao como administrador
        // if(admin != null){
        // admin.excluiSolicitacao(3);
        // }

        // Adicionar likes como usuario2
        // usuario.addLike(1); //adiciona like na solicitacao 1
        // usuario.addLike(1); //nao pode dar like 2x na mesma solicitacao
        // usuario.addLike(4); //nao pode dar like na propria solicitacao
        // usuario.addLike(3); //nao pode dar like na solicitacao excluida

        // Adiciona comentarios
        // usuario.addComentario(1, "Concordo 100%");
        // usuario.addComentario(3, "Concordo 90%");

        // Exibe solicitações
        // solicitacaoManager.exibeSolicitacoes(2);

        // System.out.println(usuario.exibeComentariosUsuario());

        /*
         * COMANDOS:
         * 
         * 
         * 
         * Comandos da classe usuarios:
         * 
         * usuarios.addUsuario(atributos) - para adicionar um objeto usuario na
         * lista de usuarios, recebe nome, senha, cidade e bairro.
         * 
         * usuarios.buscaUsuarioPorId(atributo) - para buscar um usuario na lista
         * de usuarios, recebe id do usuario
         * 
         * usuarios.getUltimoId() - retorna o ultimo id cadastrado
         * 
         * usuarios.fazLogin(atributos) - retorna o usuario com as credenciais
         * definidas, recebe cpf e senha do usuario
         * 
         * 
         * 
         * Comandos da classe usuario:
         * 
         * usuario.addSolicitacao(atributos) - adiciona solicitacao na conta do usuario,
         * recebe titulo, descricao e bairro
         * 
         * usuario.addLike(atributo) - adiciona like na solicitacao desejada, recebe o
         * id da solicitacao
         * 
         * usuario.resetSenhaUsuario(atributo) - reseta senha do usuario, recebe a nova
         * senha
         * 
         * usuario.addComentario(atributos) - adiciona um comentario na solicitacao
         * desejada, recebe id da solicitacao e o comentario
         * 
         * 
         * Comandos da classe solicitacoes:
         * 
         * solicitacoes.addSolicitacao(atributo) - é chamada pela função
         * usuario.addSolicitacao, adiciona solicitacao na lista de solicitacoes, recebe
         * a solicitacao
         * 
         * solicitacoes.buscaSolicitacaoPorId(atributo) - busca solicitacao na
         * lista de solicitacoes, recebe o id da solicitacao
         * 
         * solicitacoes.like(atributo) - é chamada pela função usuario.addLike,
         * aumenta o numero de likes na solicitacao, recebe o id da solicitacao
         * 
         * solicitacoes.exibeSolicitacoes(atributo) - exibe todas as solicitações
         * disponiveis, recebe o filtro para exibir, 1 - por ordem de cadastro, 2 - por
         * numero de like
         * 
         */

    }

}
