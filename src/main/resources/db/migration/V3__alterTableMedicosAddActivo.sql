alter table medicos
    add column activo boolean;

update medicos
    set activo = true;