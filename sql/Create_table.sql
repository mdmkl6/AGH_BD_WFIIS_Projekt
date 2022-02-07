CREATE SCHEMA project;


CREATE SEQUENCE project.magazyn_magazyn_id_seq;

CREATE TABLE project.magazyn (
                magazyn_id INTEGER NOT NULL DEFAULT nextval('project.magazyn_magazyn_id_seq'),
                nazwa VARCHAR NOT NULL,
                adres VARCHAR NOT NULL,
                CONSTRAINT magazyn_pk PRIMARY KEY (magazyn_id)
);


ALTER SEQUENCE project.magazyn_magazyn_id_seq OWNED BY project.magazyn.magazyn_id;

CREATE SEQUENCE project.servisant_servisant_id_seq;

CREATE TABLE project.servisant (
                servisant_id INTEGER NOT NULL DEFAULT nextval('project.servisant_servisant_id_seq'),
                imie VARCHAR NOT NULL,
                nazwisko VARCHAR NOT NULL,
                email VARCHAR NOT NULL,
                adres VARCHAR,
                telefon VARCHAR,
                CONSTRAINT servisant_pk PRIMARY KEY (servisant_id)
);


ALTER SEQUENCE project.servisant_servisant_id_seq OWNED BY project.servisant.servisant_id;

CREATE SEQUENCE project.pracownik_pracownik_id_seq;

CREATE TABLE project.pracownik (
                pracownik_id INTEGER NOT NULL DEFAULT nextval('project.pracownik_pracownik_id_seq'),
                imie VARCHAR NOT NULL,
                nazwisko VARCHAR NOT NULL,
                email VARCHAR NOT NULL,
                adres VARCHAR,
                telefon VARCHAR,
                CONSTRAINT pracownik_pk PRIMARY KEY (pracownik_id)
);


ALTER SEQUENCE project.pracownik_pracownik_id_seq OWNED BY project.pracownik.pracownik_id;

CREATE SEQUENCE project.urzytkownik_urzytkownik_id_seq;

CREATE TABLE project.urzytkownik (
                urzytkownik_id INTEGER NOT NULL DEFAULT nextval('project.urzytkownik_urzytkownik_id_seq'),
                imie VARCHAR NOT NULL,
                nazwisko VARCHAR NOT NULL,
                email VARCHAR NOT NULL,
                adres VARCHAR,
                telefon VARCHAR,
                CONSTRAINT urzytkownik_pk PRIMARY KEY (urzytkownik_id)
);


ALTER SEQUENCE project.urzytkownik_urzytkownik_id_seq OWNED BY project.urzytkownik.urzytkownik_id;

CREATE SEQUENCE project.servis_id_seq;

CREATE TABLE project.servis (
                id INTEGER NOT NULL DEFAULT nextval('project.servis_id_seq'),
                urzadzenie VARCHAR NOT NULL,
                urzytkownik_id INTEGER NOT NULL,
                pracownik_id INTEGER,
                cechy_produktu VARCHAR NOT NULL,
                opis_usterki VARCHAR NOT NULL,
                status VARCHAR NOT NULL,
                CONSTRAINT servis_pk PRIMARY KEY (id)
);


ALTER SEQUENCE project.servis_id_seq OWNED BY project.servis.id;

CREATE SEQUENCE project.naprawy_id_napraw_seq;

CREATE TABLE project.naprawy (
                id_napraw INTEGER NOT NULL DEFAULT nextval('project.naprawy_id_napraw_seq'),
                id INTEGER NOT NULL,
                servisant_id INTEGER NOT NULL,
                opis VARCHAR NOT NULL,
                CONSTRAINT naprawy_pk PRIMARY KEY (id_napraw)
);


ALTER SEQUENCE project.naprawy_id_napraw_seq OWNED BY project.naprawy.id_napraw;

CREATE SEQUENCE project.kategoria_kategoria_id_seq;

CREATE TABLE project.kategoria (
                kategoria_id INTEGER NOT NULL DEFAULT nextval('project.kategoria_kategoria_id_seq'),
                nazwa VARCHAR NOT NULL,
                opis VARCHAR,
                CONSTRAINT kategoria_pk PRIMARY KEY (kategoria_id)
);


ALTER SEQUENCE project.kategoria_kategoria_id_seq OWNED BY project.kategoria.kategoria_id;

CREATE SEQUENCE project.produkt_produkt_id_seq;

CREATE TABLE project.produkt (
                produkt_id INTEGER NOT NULL DEFAULT nextval('project.produkt_produkt_id_seq'),
                nazwa VARCHAR NOT NULL,
                opis_produktu VARCHAR,
                cena VARCHAR NOT NULL,
                CONSTRAINT produkt_pk PRIMARY KEY (produkt_id)
);


ALTER SEQUENCE project.produkt_produkt_id_seq OWNED BY project.produkt.produkt_id;

CREATE TABLE project.kategoria_produkt (
                kategoria_id INTEGER NOT NULL,
                produkt_id INTEGER NOT NULL,
                CONSTRAINT kategoria_produkt_pk PRIMARY KEY (kategoria_id, produkt_id)
);


CREATE TABLE project.produkty_w_magazynie (
                produkt_id INTEGER NOT NULL,
                magazyn_id INTEGER NOT NULL,
                ilosc INTEGER NOT NULL,
                CONSTRAINT produkty_w_magazynie_pk PRIMARY KEY (produkt_id, magazyn_id)
);


CREATE SEQUENCE project.tranzakcje_stacjonarne_id_seq;

CREATE TABLE project.tranzakcje_stacjonarne (
                id INTEGER NOT NULL DEFAULT nextval('project.tranzakcje_stacjonarne_id_seq'),
                produkt_id INTEGER NOT NULL,
                pracownik_id INTEGER NOT NULL,
                Ilosc INTEGER NOT NULL,
                status VARCHAR NOT NULL,
                CONSTRAINT tranzakcje_stacjonarne_pk PRIMARY KEY (id)
);


ALTER SEQUENCE project.tranzakcje_stacjonarne_id_seq OWNED BY project.tranzakcje_stacjonarne.id;

CREATE SEQUENCE project.tranzakcje_internetowe_id_seq;

CREATE TABLE project.tranzakcje_internetowe (
                id INTEGER NOT NULL DEFAULT nextval('project.tranzakcje_internetowe_id_seq'),
                produkt_id INTEGER NOT NULL,
                urzytkownik_id INTEGER NOT NULL,
                Ilosc INTEGER NOT NULL,
                status VARCHAR NOT NULL,
                CONSTRAINT tranzakcje_internetowe_pk PRIMARY KEY (id)
);


ALTER SEQUENCE project.tranzakcje_internetowe_id_seq OWNED BY project.tranzakcje_internetowe.id;

ALTER TABLE project.produkty_w_magazynie ADD CONSTRAINT magazyn_produkty_w_magazynie_fk
FOREIGN KEY (magazyn_id)
REFERENCES project.magazyn (magazyn_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE project.naprawy ADD CONSTRAINT servisant_naprawy_fk
FOREIGN KEY (servisant_id)
REFERENCES project.servisant (servisant_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE project.servis ADD CONSTRAINT pracownik_servis_fk
FOREIGN KEY (pracownik_id)
REFERENCES project.pracownik (pracownik_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE project.tranzakcje_stacjonarne ADD CONSTRAINT pracownik_tranzakcje_stacjonarne_fk
FOREIGN KEY (pracownik_id)
REFERENCES project.pracownik (pracownik_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE project.tranzakcje_internetowe ADD CONSTRAINT urzytkownik_tranzakcje_internetowe_fk
FOREIGN KEY (urzytkownik_id)
REFERENCES project.urzytkownik (urzytkownik_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE project.servis ADD CONSTRAINT urzytkownik_servis_fk
FOREIGN KEY (urzytkownik_id)
REFERENCES project.urzytkownik (urzytkownik_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE project.naprawy ADD CONSTRAINT servis_naprawy_fk
FOREIGN KEY (id)
REFERENCES project.servis (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE project.kategoria_produkt ADD CONSTRAINT kategoria_kategoria_produkt_fk
FOREIGN KEY (kategoria_id)
REFERENCES project.kategoria (kategoria_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE project.tranzakcje_internetowe ADD CONSTRAINT pordukt_tranzakcje_internetowe_fk
FOREIGN KEY (produkt_id)
REFERENCES project.produkt (produkt_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE project.tranzakcje_stacjonarne ADD CONSTRAINT pordukt_tranzakcje_stacjonarne_fk
FOREIGN KEY (produkt_id)
REFERENCES project.produkt (produkt_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE project.produkty_w_magazynie ADD CONSTRAINT pordukt_produkty_w_magazynie_fk
FOREIGN KEY (produkt_id)
REFERENCES project.produkt (produkt_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE project.kategoria_produkt ADD CONSTRAINT pordukt_kategoria_produkt_fk
FOREIGN KEY (produkt_id)
REFERENCES project.produkt (produkt_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;