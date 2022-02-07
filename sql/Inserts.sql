INSERT INTO project.urzytkownik (imie, nazwisko, email, adres,telefon ) VALUES 
('u1','u1','u1@p.pl','u1','u1' ),
('u2','u2','u2@p.pl','u2','u2' ),
('u3','u3','u3@p.pl','u3','u3' );

INSERT INTO project.servisant (imie, nazwisko, email, adres,telefon ) VALUES 
('s1','s1','s1@p.pl','s1','s1' ),
('s2','s2','s2@p.pl','s2','s2' ),
('s3','s3','s3@p.pl','s3','s3' );

INSERT INTO project.pracownik (imie, nazwisko, email, adres,telefon ) VALUES 
('p1','p1','p1@p.pl','p1','p1' ),
('p2','p2','p2@p.pl','p2','p2' ),
('p3','p3','p3@p.pl','p3','p3' );

INSERT INTO project.produkt (nazwa, opis_produktu, cena) VALUES 
( 'p1','p1',10 ),
( 'p2','p2',20 ),
( 'p3','p3',30 ),
( 'p4','p4',40 ),
( 'p5','p5',50 ),
( 'p6','p6',60 ),
( 'p7','p7',70 );

INSERT INTO project.magazyn (nazwa, adres) VALUES 
( 'm1','a1'),
( 'm2','a2');

INSERT INTO project.produkty_w_magazynie (produkt_id, magazyn_id,ilosc) VALUES
(1,1,20),
(2,1,20),
(3,1,20),
(4,1,20),
(5,1,20),
(6,1,20),
(7,1,20),
(1,2,20),
(2,2,20),
(3,2,20);


INSERT INTO project.kategoria (nazwa, opis) VALUES
( 'k1','k1'),
( 'k2','k2'),
( 'k3','k3');

INSERT INTO project.kategoria_produkt (kategoria_id, produkt_id) VALUES
( 1, 1 ),
( 1, 2 ),
( 1, 3 ),
( 2, 3 ),
( 2, 4 ),
( 2, 5 ),
( 3, 3 ),
( 3, 5 ),
( 3, 6 ),
( 3, 7 );


-- INSERT INTO project.tranzakcje_internetowe (produkt_id, urzytkownik_id, ilosc,status) VALUES 
-- ( 1,1,10,'s1' );

--INSERT INTO project.servis (urzadzenie, urzytkownik_id, cechy_produktu, opis_usterki, status ) VALUES ( 'p1', 1, 'p1', 'p1', 'p1' );

-- SELECT id AS "ID", urzadzenie AS "Urzadzenie", cechy_produktu AS "Cechy_Urzadzenia", opis_usterki AS "Opis_uster", status AS "Status" FROM project.servis Where urzytkownik_id