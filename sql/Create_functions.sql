CREATE OR REPLACE FUNCTION project.sum_product(id int)
RETURNS int AS
$$
    SELECT sum(ilosc) from project.produkty_w_magazynie
    WHERE produkt_id=id;
$$
LANGUAGE SQL;

CREATE OR REPLACE FUNCTION project.get_magazyn(id int)
RETURNS int AS
$$
    SELECT magazyn_id from project.produkty_w_magazynie
    WHERE produkt_id=id AND ilosc>0
    LIMIT 1;
$$
LANGUAGE SQL;

CREATE OR REPLACE FUNCTION project.ile_magazyn(id int)
RETURNS int AS
$$
    SELECT ilosc from project.produkty_w_magazynie
    WHERE produkt_id=id AND magazyn_id=project.get_magazyn(id)
    LIMIT 1;
$$
LANGUAGE SQL;

CREATE OR REPLACE FUNCTION project.u_id_by_mail(mail varchar)
RETURNS int AS
$$
    SELECT urzytkownik_id from project.urzytkownik
    WHERE email LIKE mail;
$$
LANGUAGE SQL;

CREATE OR REPLACE FUNCTION project.p_id_by_mail(mail varchar)
RETURNS int AS
$$
    SELECT pracownik_id from project.pracownik
    WHERE email LIKE mail;
$$
LANGUAGE SQL;

CREATE OR REPLACE FUNCTION project.s_id_by_mail(mail varchar)
RETURNS int AS
$$
    SELECT servisant_id from project.servisant
    WHERE email LIKE mail;
$$
LANGUAGE SQL;