CREATE VIEW project.lista_prod AS 
SELECT p.produkt_id AS "ID",p.nazwa AS "Nazwa",p.opis_produktu AS "Opis",p.cena AS "Cena",sum(pm.ilosc) AS "Ilosc w Magazynie"
FROM project.produkt p 
JOIN project.produkty_w_magazynie pm USING (produkt_id)
GROUP BY p.produkt_id,p.nazwa,p.opis_produktu,p.cena
HAVING sum(pm.ilosc)>0;

CREATE VIEW project.kategory_prod AS 
SELECT k.nazwa AS "Kategoria", l."ID",l."Nazwa",l."Opis",l."Cena",l."Ilosc w Magazynie" 
FROM project.lista_prod l
LEFT JOIN project.kategoria_produkt kp ON l."ID"=kp.produkt_id
LEFT JOIN project.kategoria k USING (kategoria_id);

-- SELECT "ID","Nazwa","Opis","Cena","Ilosc w Magazynie" FROM project.kategory_prod WHERE "Kategoria" LIKE 'k2' ORDER BY "ID" ;

CREATE VIEW project.t_internetowe AS 
SELECT t.id AS "ID",t.urzytkownik_id, t.produkt_id AS "ID_Produktu",p.nazwa AS "Nazwa_Produktu", t.ilosc AS "Ilosc", t.status AS "Status_Zakowienia"
FROM project.tranzakcje_internetowe t
LEFT JOIN project.produkt p USING (produkt_id)
Group BY t.urzytkownik_id, t.id,p.nazwa;

CREATE VIEW project.t_stacjonarne AS 
SELECT t.id AS "ID",t.pracownik_id, t.produkt_id AS "ID_Produktu",p.nazwa AS "Nazwa_Produktu", t.ilosc AS "Ilosc", t.status AS "Status_Zakowienia"
FROM project.tranzakcje_stacjonarne t
LEFT JOIN project.produkt p USING (produkt_id)
Group BY t.pracownik_id, t.id,p.nazwa;