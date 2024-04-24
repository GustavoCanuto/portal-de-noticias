CREATE TABLE tb_tag(
    id bigserial NOT NULL ,
    nome VARCHAR (75) NOT NULL unique,
    PRIMARY KEY (Id)
);

CREATE TABLE tb_noticia(
    id bigserial NOT NULL ,
    site_buscado VARCHAR not null,
    link_da_noticia_oficial VARCHAR not null unique, 
    categoria VARCHAR not null,
    autoria VARCHAR not null,
    data_publicacao timestamp not null,
    data_modificada timestamp,
    conteudo text not null,
    titulo VARCHAR (255) not null,
    sinopse VARCHAR, 
    imagem_capa  VARCHAR not null,
    numero_visualizacao bigint default 0,
    PRIMARY KEY (Id)
);

CREATE TABLE tb_tag_noticia(
     id bigserial NOT NULL ,
     fk_noticia int NOT NULL REFERENCES tb_noticia(id),
     fk_tag int NOT NULL REFERENCES tb_tag(id),
     PRIMARY KEY (Id)
);

CREATE INDEX idx_site_buscado ON tb_noticia (site_buscado);
CREATE INDEX idx_data_site_titulo ON tb_noticia (site_buscado, titulo);
CREATE INDEX idx_mais_lidas ON tb_noticia (numero_visualizacao);