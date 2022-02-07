Kolejność Wywoływania komend z plików .sql znajdujących się w katalogu sql:
1.Create_table.sql
2.Create_views.sql
3.Create_functions.sql
4.Create_triggers.sql
5.Create_role.sql
6.Inserts.sql

W katalogu out w pliku url.txt nalerz wpiscać url do połączenia z bazą dzanych wedle przykładu(tylko jedna linijka):
jdbc:<Link>:<Port>/<Baza Danych>
jdbc:postgresql://localhost:5432/BD_P

Program należy uruchomić za pomocą pliku Project_v1.jar urzywjąc najnowszej wersi javy (najlepiej java 17)
Plik Project_v1.jar znajduje się w katalogu out