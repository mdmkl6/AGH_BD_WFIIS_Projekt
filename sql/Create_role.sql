CREATE ROLE role_creator WITH CREATEROLE LOGIN PASSWORD 'pass';
GRANT ALL PRIVILEGES ON SCHEMA project TO role_creator;
GRANT SELECT,INSERT ON project.urzytkownik,project.servisant,project.pracownik TO role_creator;
GRANT USAGE, SELECT ON SEQUENCE project.urzytkownik_urzytkownik_id_seq TO role_creator;
GRANT USAGE, SELECT ON SEQUENCE project.pracownik_pracownik_id_seq TO role_creator;
GRANT USAGE, SELECT ON SEQUENCE project.servisant_servisant_id_seq TO role_creator;


CREATE ROLE standard_user;
GRANT ALL PRIVILEGES ON SCHEMA project TO standard_user;
GRANT INSERT, SELECT ON project.tranzakcje_internetowe,project.servis TO standard_user;
GRANT USAGE, SELECT ON SEQUENCE project.tranzakcje_internetowe_id_seq, project.servis_id_seq TO standard_user;
GRANT SELECT ON project.lista_prod,project.kategoria,project.urzytkownik,project.kategory_prod, project.t_internetowe TO standard_user;
GRANT SELECT,UPDATE ON project.produkty_w_magazynie TO standard_user;
GRANT EXECUTE ON FUNCTION project.u_id_by_mail TO standard_user;

CREATE ROLE "u1@p.pl" LOGIN PASSWORD 'u1pass';
GRANT standard_user TO "u1@p.pl";

CREATE ROLE "u2@p.pl" LOGIN PASSWORD 'u2pass';
GRANT standard_user TO "u2@p.pl";

CREATE ROLE "u3@p.pl" LOGIN PASSWORD 'u3pass';
GRANT standard_user TO "u3@p.pl";


CREATE ROLE servisant_user;
GRANT ALL PRIVILEGES ON SCHEMA project TO servisant_user;
GRANT SELECT ON project.servisant TO servisant_user;
GRANT SELECT,INSERT ON project.naprawy TO servisant_user;
GRANT SELECT,UPDATE ON project.servis TO servisant_user;
GRANT USAGE, SELECT ON SEQUENCE project.naprawy_id_napraw_seq TO servisant_user;
GRANT EXECUTE ON FUNCTION project.s_id_by_mail TO servisant_user;

CREATE ROLE "s1@p.pl" LOGIN PASSWORD 's1pass';
GRANT servisant_user TO "s1@p.pl";

CREATE ROLE "s2@p.pl" LOGIN PASSWORD 's2pass';
GRANT servisant_user TO "s2@p.pl";

CREATE ROLE "s3@p.pl" LOGIN PASSWORD 's3pass';
GRANT servisant_user TO "s3@p.pl";


CREATE ROLE pracownik_user;
GRANT ALL PRIVILEGES ON SCHEMA project TO pracownik_user;
GRANT SELECT ON project.pracownik,project.lista_prod,project.kategory_prod,
project.t_stacjonarne TO pracownik_user;
GRANT INSERT ON project.kategoria_produkt,project.servis TO pracownik_user;
GRANT SELECT,INSERT ON project.tranzakcje_stacjonarne,project.produkt,project.magazyn,
project.kategoria TO pracownik_user;
GRANT SELECT,UPDATE,INSERT ON project.produkty_w_magazynie TO pracownik_user;
GRANT USAGE, SELECT ON SEQUENCE project.tranzakcje_stacjonarne_id_seq,project.magazyn_magazyn_id_seq,
project.produkt_produkt_id_seq,project.kategoria_kategoria_id_seq,project.servis_id_seq TO pracownik_user;
GRANT EXECUTE ON FUNCTION project.p_id_by_mail TO pracownik_user;

CREATE ROLE "p1@p.pl" LOGIN PASSWORD 'p1pass';
GRANT pracownik_user TO "p1@p.pl";

CREATE ROLE "p2@p.pl" LOGIN PASSWORD 'p2pass';
GRANT pracownik_user TO "p2@p.pl";

CREATE ROLE "p3@p.pl" LOGIN PASSWORD 'p3pass';
GRANT pracownik_user TO "p3@p.pl";