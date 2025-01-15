CREATE TABLE medicos (
                         id SERIAL PRIMARY KEY,
                         nombre VARCHAR(255) NOT NULL,
                         email VARCHAR(255) NOT NULL UNIQUE,
                         documento VARCHAR(100) NOT NULL UNIQUE,
                         especialidad VARCHAR(255) NOT NULL,
                         calle VARCHAR(255) NOT NULL,
                         distrito VARCHAR(255) NOT NULL,
                         ciudad VARCHAR(150) NOT NULL,
                         complemento VARCHAR(255)
);