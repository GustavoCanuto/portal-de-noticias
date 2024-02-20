CREATE TABLE tb_tags(
    id  INT NOT NULL ,
    nome VARCHAR (75) NOT NULL unique,
    PRIMARY KEY (Id)
);

CREATE TABLE tb_noticia(
    id  INT NOT NULL ,
    site_buscado VARCHAR not null,
    link_da_noticia_oficial VARCHAR not null, 
    categoria VARCHAR not null,
    autoria VARCHAR not null,
    data_publicacao timestamp not null,
    data_modificada timestamp,
    conteudo VARCHAR,
    titulo VARCHAR (75) not null,
    sinopse VARCHAR, 
    imagem_capa  VARCHAR not null,
    PRIMARY KEY (Id)
);

CREATE TABLE tb_tags_noticias(
     id  INT NOT NULL ,
     fk_noticia int NOT NULL REFERENCES tb_noticia(id),
     fk_tag int NOT NULL REFERENCES tb_tags(id),
     PRIMARY KEY (Id)
);