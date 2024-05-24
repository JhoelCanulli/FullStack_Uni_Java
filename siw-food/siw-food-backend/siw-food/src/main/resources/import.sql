insert into recipe (id, title, description, image) values(nextval('recipe_seq'), 'Carbonara', 'La cosa migliore della carbonara non è la carbocrema', 'https://upload.wikimedia.org/wikipedia/commons/thumb/3/33/Espaguetis_carbonara.jpg/640px-Espaguetis_carbonara.jpg');

insert into chef (id, name, surname, birth, photo) values(nextval('chef_seq'), 'Benedetta', 'Parodi', '1945-02-06', 'https://www.benedettaparodi.it/wp-content/uploads/2021/02/profilo-benedetta-Parodi.png');

insert into ingredient (id, name, origin, image) values(nextval('chef_seq'), 'Zafferano', 'Iran', 'https://upload.wikimedia.org/wikipedia/commons/2/25/Saffron-IMG_6640-2.jpg');