insert into usuarios (email, password) values ('arielarmijo@yahoo.es', '$2a$10$O.udAvsveX.UPRbsUyka9.mzvbzEa5FGISE1iXlVfs9esQirvOWB6');
insert into roles (nombre) values ('ROLE_USER');
insert into usuarios_roles values (1, 1);

insert into categorias (nombre) values ('testing');

insert into posts (titulo, contenido, imagen, categoria_id, fecha_creacion, autor_id) values ('Post de prueba 1', 'Texto super largo...', 'https://dummyimage.com/600x400/000/fff', 1, date'2021-05-20', 1);
insert into posts (titulo, contenido, imagen, categoria_id, fecha_creacion, autor_id) values ('Post de prueba 2', 'Texto super largo...', 'https://dummyimage.com/600x400/000/fff', 1, date'2021-06-15', 1);