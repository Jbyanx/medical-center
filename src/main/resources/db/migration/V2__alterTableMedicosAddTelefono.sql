alter table medicos
    add column telefono varchar(150);

update medicos m
    set telefono = '00000'
    where m.telefono is null;

alter table medicos
    alter column telefono set not null;