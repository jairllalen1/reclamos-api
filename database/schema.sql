CREATE TABLE entity_type (
                             id_entity_type INT NOT NULL AUTO_INCREMENT,
                             name_type_entity VARCHAR(45) NOT NULL,
                             entity_size VARCHAR(45) NOT NULL,
                             sector VARCHAR(45) NOT NULL,
                             id_user_create INT NOT NULL,
                             id_user_update INT NULL,
                             date_update DATE NULL,
                             date_create DATE NULL,
                             PRIMARY KEY (id_entity_type)
);