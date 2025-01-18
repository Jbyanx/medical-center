CREATE TABLE usuarios (
                         id SERIAL PRIMARY KEY,
                         login VARCHAR(255) NOT NULL,
                         clave VARCHAR(300) NOT NULL
);