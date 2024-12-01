CREATE DATABASE KON;

USE KON;

CREATE TABLE USUARIO(
ID INT AUTO_INCREMENT,
NOME VARCHAR(60) NOT NULL,
BAIRRO VARCHAR(30) NOT NULL,
CIDADE VARCHAR(30) NOT NULL,
CPF VARCHAR(14) NOT NULL,
SENHA VARCHAR(20) NOT NULL,
TELEFONE VARCHAR(15) NOT NULL,
EMAIL VARCHAR(45) NOT NULL,
TIPO CHAR NOT NULL default 'c',

primary key(ID)
);

CREATE TABLE SOLICITACAO(
ID INT auto_increment,
ID_USUARIO INT NOT NULL,
TITULO VARCHAR(45),
BAIRRO VARCHAR(30),
DESCRICAO VARCHAR(150),
NUM_LIKES INT DEFAULT 0,
DATA_ABERTURA TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
DATA_CONCLUSAO datetime,
STATUS_SOLICITACAO char default 'a',


PRIMARY KEY (ID),

FOREIGN KEY(ID_USUARIO) REFERENCES USUARIO(ID)
);

CREATE TABLE COMENTARIO(
ID INT auto_increment,
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
CREATE PROCEDURE add_usuario(nome varchar(60), bairro varchar(30), cidade varchar(30), cpf varchar(14), senha varchar(20), telefone varchar(15), email varchar(45))
BEGIN
INSERT INTO USUARIO(nome, bairro, cidade, cpf, senha, telefone, email) 
VALUES(nome, bairro, cidade, cpf, senha, telefone, email);
END$
DELIMITER ;

CALL ADD_USUARIO("João Pedro", "Jardim Aeroporto", "ITU", "401.724.408-40", "12345", "11978521152", "joao.kon@gmail.com");
CALL ADD_USUARIO("Daniel Cravo", "São Luíz", "ITU", "202.469.698-81", "321", "19941289941", "daniel.kon@gmail.com");
CALL ADD_USUARIO("Alexandre Remo", "São Jorge", "ITU", "518.591.729-91", "abc", "15987721663", "alexandre.kon@gmail.com");


#usuario.addSolicitacao
DELIMITER $
CREATE PROCEDURE add_solicitacao(id_usuario int, titulo varchar(45), descricao varchar(150), bairro varchar(30))
BEGIN
INSERT INTO solicitacao(id_usuario, titulo, descricao, bairro) 
VALUES (id_usuario, titulo, descricao, bairro);
END$
DELIMITER ;

CALL add_solicitacao(1, "Faz o M", "Marçal mito mano faz o M", "Xique Xique");
CALL add_solicitacao(2, "Arrumar a rua", "Arrumar a rua tal ta toda estoradassa", "Sao luiz");
CALL add_solicitacao(3, "Colocar lombada", "Carros muito rapidos nessa rua", "Jardim aeroporto");



#solicitacoes.exibeSolicitacoes por like
CREATE OR REPLACE VIEW solicitacoes_por_like AS
select s.id, u.nome as "Nome", s.bairro as "Bairro", s.titulo as "Título", s.num_likes as "Likes", s.descricao as "Solicitação", s.data_abertura as 'Criado em' 
from solicitacao s
inner join usuario u 
on u.id = s.id_usuario
order by s.num_likes desc;

SELECT * FROM SOLICITACOES_POR_LIKE;


#solicitacoes.exibeSolicitacoes por data
CREATE OR REPLACE VIEW solicitacoes_por_data AS
select s.id, u.nome, s.bairro, s.titulo, s.num_likes, s.descricao, s.data_abertura as 'criado em' 
from usuario u, solicitacao s
where u.id = s.id_usuario order by s.data_abertura desc;

SELECT * FROM SOLICITACOES_POR_DATA;


#solicitacoes.exibeSolicitacoes por bairro
DELIMITER $
CREATE PROCEDURE solicitacoes_por_bairro(p_bairro varchar(30))
BEGIN
select s.id, u.nome, s.bairro, s.titulo, s.num_likes, s.descricao, s.data_abertura as 'criado em' 
from usuario u, solicitacao s
where u.id = s.id_usuario and LOWER(s.bairro) = LOWER(p_bairro);
END$
DELIMITER ;

CALL SOLICITACOES_POR_BAIRRO("Sao Luiz");


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

CALL add_like(2, 1);


#usuario.addComentario
DELIMITER $
CREATE PROCEDURE add_comentario(p_id_usuario int, p_texto varchar(100), p_id_solicitacao int)
BEGIN
insert into comentario(id_usuario, texto, id_solicitacao)
values (p_id_usuario, p_texto, p_id_solicitacao);
END $
DELIMITER ; 

CALL add_comentario(3, "verdade", 1);


#exibeComentariosSolicitacao
DELIMITER $
CREATE PROCEDURE solicitacao_comentarios(p_id_solicitacao int)
BEGIN
SELECT u.nome, c.texto FROM comentario c
INNER JOIN usuario u on u.id = c.id_usuario
where c.id_solicitacao = p_id_solicitacao;
END $
DELIMITER ;

CALL solicitacao_comentarios(1);


#usuario.removeSolicitacao
DELIMITER $
CREATE PROCEDURE remove_solicitacao(p_id_solicitacao int)
BEGIN
delete from comentario where id_solicitacao = p_id_solicitacao;
delete from usuario_solicitacao_like where id_solicitacao = p_id_solicitacao;
delete from solicitacao where id = p_id_solicitacao;
END$
DELIMITER ;

drop procedure remove_solicitacao;

call remove_solicitacao(3);


#usuario.removeUsuario
DELIMITER $
CREATE PROCEDURE remove_usuario(p_id_usuario int)
BEGIN
    DELETE FROM USUARIO_SOLICITACAO_LIKE WHERE ID_USUARIO = p_id_usuario;
    DELETE FROM COMENTARIO WHERE ID_USUARIO = p_id_usuario;
    DELETE FROM SOLICITACAO WHERE ID_USUARIO = p_id_usuario;
    DELETE FROM USUARIO WHERE ID = p_id_usuario;
END$
DELIMITER ;

DROP PROCEDURE REMOVE_USUARIO;

CALL REMOVE_USUARIO(1);


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

select * from r_quant_solicitacoes_bairros;




DROP DATABASE KON;


