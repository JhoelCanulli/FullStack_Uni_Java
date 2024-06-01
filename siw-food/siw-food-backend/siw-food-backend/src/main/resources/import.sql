
insert into recipe (id, title, description) values(nextval('recipe_seq'), 'Carbonara', 'La cosa migliore della carbonara non è la carbocrema');

insert into chef (id, name, surname, birth, photo) values(nextval('chef_seq'), 'Benedetta', 'Parodi', '1945-02-06', 'https://www.benedettaparodi.it/wp-content/uploads/2021/02/profilo-benedetta-Parodi.png');

insert into ingredient (id, name, origin) values(nextval('ingredient_seq'), 'Zafferano', 'Iran');
