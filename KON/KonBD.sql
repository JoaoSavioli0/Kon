CREATE DATABASE KON;

USE KON;

CREATE TABLE USUARIO(
ID INT NOT NULL,
NOME VARCHAR(60) NOT NULL,
BAIRRO VARCHAR(30) NOT NULL,
CIDADE VARCHAR(30) NOT NULL,
CPF VARCHAR(14) NOT NULL unique,
SENHA VARCHAR(20) NOT NULL,
TELEFONE VARCHAR(15) NOT NULL,
EMAIL VARCHAR(45) NOT NULL,
UF VARCHAR(2) NOT NULL,
TIPO VARCHAR(20) default 'usuario',
NOME_USUARIO varchar(50) NOT NULL,
INTERACAO_1 date,
INTERACAO_2 date,

primary key(ID)
);

CREATE TABLE SOLICITACAO(
ID INT not null,
ID_USUARIO INT NOT NULL,
TITULO VARCHAR(65),
BAIRRO VARCHAR(30),
DESCRICAO VARCHAR(500),
NUM_LIKES INT DEFAULT 0,
DATA_ABERTURA TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
DATA_CONCLUSAO datetime,
STATUS_SOLICITACAO varchar(12) default 'aberto',
ANONIMO int default 0,


PRIMARY KEY (ID),

FOREIGN KEY(ID_USUARIO) REFERENCES USUARIO(ID)
);

CREATE TABLE COMENTARIO(
ID INT not null,
ID_USUARIO INT NOT NULL,
TEXTO VARCHAR(100) NOT NULL,
ID_SOLICITACAO INT NOT NULL,

PRIMARY KEY(ID),

FOREIGN KEY(ID_SOLICITACAO) REFERENCES SOLICITACAO(ID),
FOREIGN KEY(ID_USUARIO) REFERENCES USUARIO(ID)
);

CREATE TABLE USUARIO_SOLICITACAO_LIKE (
    ID_USUARIO INT,
    ID_SOLICITACAO INT,
    PRIMARY KEY (ID_USUARIO, ID_SOLICITACAO),
    FOREIGN KEY (ID_USUARIO) REFERENCES USUARIO(ID),
    FOREIGN KEY (ID_SOLICITACAO) REFERENCES SOLICITACAO(ID)
);



#usuarios.add
DELIMITER $
CREATE PROCEDURE add_usuario(p_id_usuario int, p_nome varchar(60), p_bairro varchar(30), p_cidade varchar(30), p_uf varchar(2), p_cpf varchar(14), p_senha varchar(20), p_telefone varchar(15), p_email varchar(45), p_nome_user varchar(50), p_tipo varchar(20))
BEGIN
INSERT INTO USUARIO(id, nome, bairro, cidade, uf, cpf, senha, telefone, email, nome_usuario, tipo) 
VALUES(p_id_usuario, p_nome, p_bairro, p_cidade, p_uf, p_cpf, p_senha, p_telefone, p_email, p_nome_user, p_tipo);
END$
DELIMITER ;


#usuario.addSolicitacao
DELIMITER $
CREATE PROCEDURE add_solicitacao(p_id_solicitacao int, p_id_usuario int, p_titulo varchar(65), p_descricao varchar(500), p_bairro varchar(30), p_anonimo int)
BEGIN
INSERT INTO solicitacao(id, id_usuario, titulo, descricao, bairro, anonimo) 
VALUES (p_id_solicitacao, p_id_usuario, p_titulo, p_descricao, p_bairro, p_anonimo);
END$
DELIMITER ;


#solicitacoes.exibeSolicitacoes por like
CREATE OR REPLACE VIEW solicitacoes_por_like AS
select s.id, u.nome_usuario as "Nome", s.bairro as "Bairro", s.titulo as "Título", s.num_likes as "Likes", s.descricao as "Solicitação", s.data_abertura as 'Criado em' 
from solicitacao s
inner join usuario u 
on u.id = s.id_usuario
order by s.num_likes desc;


#solicitacoes.exibeSolicitacoes por data
CREATE OR REPLACE VIEW solicitacoes_por_data AS
select s.id, u.nome_usuario, s.bairro, s.titulo, s.num_likes, s.descricao, s.data_abertura as 'criado em' 
from usuario u, solicitacao s
where u.id = s.id_usuario order by s.data_abertura desc;


#usuarios.buscaPorId
DELIMITER $
CREATE PROCEDURE busca_usuario_por_id(p_id int)
BEGIN
SELECT * FROM usuario u WHERE u.id = p_id;
END$
DELIMITER ;


#usuarios.buscaPorCpf
DELIMITER $
CREATE PROCEDURE busca_usuario_por_cpf(p_cpf varchar(14))
BEGIN
SELECT * FROM usuario u WHERE u.cpf = p_cpf;
END$
DELIMITER ;

SELECT * FROM SOLICITACAO WHERE TITULO LIKE '%a%' OR DESCRICAO LIKE '%a%';


#solicitacoes.exibeSolicitacoes por bairro
DELIMITER $
CREATE PROCEDURE solicitacoes_por_bairro(p_bairro varchar(30))
BEGIN
select s.id, u.nome_usuario, s.bairro, s.titulo, s.num_likes, s.descricao, s.data_abertura as 'criado em' 
from usuario u, solicitacao s
where u.id = s.id_usuario and LOWER(s.bairro) = LOWER(p_bairro);
END$
DELIMITER ;


#usuario.addLike
DELIMITER $
CREATE PROCEDURE add_like(p_id_usuario int, p_id_solicitacao int)
BEGIN
insert into usuario_solicitacao_like
values(p_id_usuario, p_id_solicitacao);
update solicitacao
set num_likes = num_likes + 1
where id = p_id_solicitacao;
END$
DELIMITER ;


#usuario.addComentario
DELIMITER $
CREATE PROCEDURE add_comentario(p_id_comentario int, p_id_solicitacao int, p_id_usuario int, p_texto varchar(100))
BEGIN
insert into comentario(id, id_usuario, texto, id_solicitacao)
values (p_id_comentario, p_id_usuario, p_texto, p_id_solicitacao);
END $
DELIMITER ; 



#exibeComentariosSolicitacao
DELIMITER $
CREATE PROCEDURE solicitacao_comentarios(p_id_solicitacao int)
BEGIN
SELECT * FROM comentario c
where c.id_solicitacao = p_id_solicitacao;
END $
DELIMITER ;

#Exibe numero de comentarios solicitação
DELIMITER $
CREATE PROCEDURE num_comentarios_solicitacao(p_id_solicitacao int)
BEGIN
SELECT COUNT(id) 
FROM COMENTARIO
WHERE ID_SOLICITACAO = p_id_solicitacao;
END$
DELIMITER ;


#Exibe numero de comentarios usuario
DELIMITER $
CREATE PROCEDURE num_comentarios_usuario(p_id_usuario int)
BEGIN
SELECT COUNT(id) 
FROM COMENTARIO
WHERE ID_USUARIO = p_id_usuario;
END$
DELIMITER ;


#usuario.removeSolicitacao
DELIMITER $
CREATE PROCEDURE remove_solicitacao(p_id_solicitacao int)
BEGIN
delete from comentario where id_solicitacao = p_id_solicitacao;
delete from usuario_solicitacao_like where id_solicitacao = p_id_solicitacao;
delete from solicitacao where id = p_id_solicitacao;
END$
DELIMITER ;


#usuario.removeUsuario
DELIMITER $
CREATE PROCEDURE remove_usuario(p_id_usuario int)
BEGIN
	SET SQL_SAFE_UPDATES = 0;
	DELETE FROM USUARIO_SOLICITACAO_LIKE WHERE id_solicitacao IN (SELECT id FROM solicitacao WHERE id_usuario = p_id_usuario);
    DELETE FROM USUARIO_SOLICITACAO_LIKE WHERE ID_USUARIO = p_id_usuario;
    DELETE FROM COMENTARIO WHERE id_solicitacao IN (SELECT id FROM solicitacao WHERE id_usuario = p_id_usuario);
    DELETE FROM COMENTARIO WHERE ID_USUARIO = p_id_usuario;
    DELETE FROM SOLICITACAO WHERE ID_USUARIO = p_id_usuario;
    DELETE FROM USUARIO WHERE ID = p_id_usuario;
    SET SQL_SAFE_UPDATES = 1;
END$
DELIMITER ;


#usuario.removeUsuario
DELIMITER $
CREATE PROCEDURE limpa_tabela()
BEGIN
	SET SQL_SAFE_UPDATES = 0;
	DELETE FROM USUARIO;
    SET SQL_SAFE_UPDATES = 1;
END$
DELIMITER ;


#removeComentario
DELIMITER $
CREATE PROCEDURE remove_comentario(p_id_comentario int)
BEGIN
DELETE FROM COMENTARIO WHERE ID = p_id_comentario;
END$
DELIMITER ;


#Relatório quantidade de solicitações por bairro
CREATE OR REPLACE VIEW r_quant_solicitacoes_bairros AS 
SELECT s.bairro, count(s.id) as "Quantidade de solicitações"
FROM solicitacao s
group by(s.bairro)
order by count(s.id) desc;

DELIMITER $
CREATE TRIGGER data_fechamento_solicitacao 
BEFORE UPDATE ON solicitacao
FOR EACH ROW
BEGIN
IF NEW.status_solicitacao = 'fechado' THEN
        SET NEW.data_conclusao = SYSDATE();
    END IF;
END $
DELIMITER ; 

select * from r_quant_solicitacoes_bairros;

select * from usuario;

select * from solicitacao;

select * from usuario_solicitacao_like;

select * from comentario;


select max(id) from usuario;
select max(id) from solicitacao;

drop database kon;

SELECT id_solicitacao FROM usuario_solicitacao_like where ID_USUARIO = 2;


