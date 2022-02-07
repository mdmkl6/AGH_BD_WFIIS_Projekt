CREATE OR REPLACE FUNCTION project.validate_urzytkownik ()
    RETURNS TRIGGER
    LANGUAGE plpgsql
    AS $$
    BEGIN
    IF New.email NOT LIKE '%@%.%' THEN
        RAISE EXCEPTION 'Niepoprawny e-mail';
    END IF;
    RETURN NEW;                                                          
    END;
    $$;

CREATE TRIGGER urzytkownik_valid 
    BEFORE INSERT OR UPDATE OR DELETE ON project.urzytkownik
    FOR EACH ROW EXECUTE PROCEDURE project.validate_urzytkownik(); 


CREATE OR REPLACE FUNCTION project.kup ()
    RETURNS TRIGGER
    LANGUAGE plpgsql
    AS $$
    DECLARE
    n INTEGER;
    m_id INTEGER;
    BEGIN
    n=NEW.ilosc;
    IF n>project.sum_product(NEW.produkt_id) THEN
        NEW.status='Brak w magazynie';
    ELSE   
        WHILE n>project.ile_magazyn(NEW.produkt_id) LOOP
            m_id=project.get_magazyn(NEW.produkt_id);
            n=n-project.ile_magazyn(NEW.produkt_id);
            UPDATE project.produkty_w_magazynie SET ilosc=0 WHERE produkt_id=NEW.produkt_id AND magazyn_id=m_id;
        END LOOP;
        m_id=project.get_magazyn(NEW.produkt_id);
        UPDATE project.produkty_w_magazynie SET ilosc=ilosc-n WHERE produkt_id=NEW.produkt_id AND magazyn_id=m_id;
    END IF;
    RETURN NEW;                                                          
    END;
    $$;

CREATE TRIGGER kup_internet
    BEFORE INSERT OR UPDATE OR DELETE ON project.tranzakcje_internetowe
    FOR EACH ROW EXECUTE PROCEDURE project.kup(); 

CREATE TRIGGER kup_stacjo
    BEFORE INSERT OR UPDATE OR DELETE ON project.tranzakcje_stacjonarne
    FOR EACH ROW EXECUTE PROCEDURE project.kup(); 