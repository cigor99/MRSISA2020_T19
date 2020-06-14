INSERT INTO ZDRAVSTEVNI_KARTONI (ID_ZDRAVSTVENOG_KARTONA, DIOPTRIJA, KRVNA_GRUPA, TEZINA, VISINA) VALUES
(1, 0, 7, 80.0, 180.0),
(2, 0.75, 1, 50.0, 160.0),
(3, 0.0, 7, 80.0, 180.0),
(4, 0.55, 5, 60.0, 150.0),
(5, 0.0, 7, 110.0, 200.0),
(6, 1.0, 0, 50.0, 140.0),
(7, 0.6, 7, 80.0, 167.0),
(8, 0.55, 4, 60.0, 158.0);

INSERT INTO PACIJENTI (ID_PACIJENTA, ADRESA, BROJ_TELEFONA, DRZAVA, EMAIL, GRAD, IME, JEDINSTVENI_BROJ_OSIGURANIKA, JMBG, LOZINKA, POL, PREZIME, STANJE_PACIJENTA, TIP_KORISNIKA, ZDRAVSTENI_KARTON) VALUES
(1, 'Bul Oslobodjenja 12', '+38162222222', 'Srbija', 'gdbxns98@gmail.com', 'Novi Sad', 'Marko', '1', '1234567891017', 'Password1', 0, 'Markovic', 2, NULL, 1),
(2, 'Bul Oslobodjenja 15', '+38163333333', 'Srbija', 'neko@gmail.com', 'Novi Sad', 'Marko', '2', '1234567891019', 'Password1', 0, 'Maric', 2, NULL, 2),
(3, 'Nemanjina 15', '+378623332533', 'Srbija', 'ili@hotmail.com', 'Beograd', 'Ana', '3', '1234567891020', 'Sifraaa1', 1, 'Anic', 1, NULL, 3),
(4, 'Nemanjina 20', '+345623653333', 'Srbija', 'neko4@hotmail.com', 'Nis', 'Marko', '4', '1234567891021', 'Stolica12', 0, 'Nemanjic', 2, NULL, 4),
(5, 'Narodnog fronta 76', '+352533653333', 'Srbija', 'marinaMaric@hotmail.com', 'Kragujevac', 'Marina', '5', '1234567891022', 'Frizider1', 1, 'Maric', 2, NULL, 5),
(6, 'Jovana Ducica 12', '+352623696333', 'Srbija', 'dusan6@hotmail.com', 'Smederevo', 'Dusan', '6', '1234567891023', 'Password2', 0, 'Kostic', 2, NULL, 6),
(7, 'Patrijarha Pavla 20', '+352623653333', 'Srbija', 'jovan7@hotmail.com', 'Subotica', 'Jovan', '7', '1234567891024', 'Sifra1', 0, 'Jovanovic', 2, NULL, 7),
(8, 'Kosovska 12', '+352623656633', 'Srbija', 'jovanaj@hotmail.com', 'Novi Sad', 'Jovana', '8', '1234567891025', 'Laptop43', 1, 'Jovic', 2, NULL, 8);

INSERT INTO Klinike (ID_Klinike, ADRESA, NAZIV, OPIS, POCETAK_RV, KRAJ_RV) VALUES
(1, 'Bulevar Oslobodjenja 29, Novi Sad', 'klinika 1', 'opis', '08:00', '16:00'),
(2, 'Balzakova 16, Novi Sad', 'klinika 2', 'opis', '08:00', '16:00');

INSERT INTO KLINICKI_CENTAR (ID_KC) VALUES (1);

INSERT INTO LEKOVI (ID_LEKA, NAZIV, SIFRA, KLINICKI_CENTAR) VALUES
(1, 'lek1', 1, 1),
(2, 'lek2', 2, 1),
(3, 'lek3', 3, 1),
(4, 'lek4', 4, 1);

INSERT INTO DIJAGNOZE (ID_DIJAGNOZA, NAZIV, OPIS, SIFRA, KLINICKI_CENTAR) VALUES
(1, 'prva', 'prva dijagnoza', 8100, 1),
(2, 'druga', 'druga dijagnoza', 1200, 1),
(3, 'treca', 'treca dijagnoza', 3600, 1); 

INSERT INTO ADMINISTRATORIKC (ID_ADMINKC, EMAIL, IME, JMBG, LOZINKA, PREZIME, TIP_KORISNIKA, KLINICKI_CENTAR) VALUES
(1, 'super', 'super', '0000000000000', 'super', 'super', 0, 1),
(2, 'apprentice.magic99@gmail.com', 'Ime', '1234567891291', 'Password1', 'Prezimenko', 0, 1),
(3, 'admin3@nesto.com', 'Imenko', '1234567891293', 'Password1', 'Neko', 0, 1);

INSERT INTO CENOVNIK (ID_CENOVNIKA) VALUES(1);

INSERT INTO ZAHTEVI_ZA_REGISTRACIJU (ID_ZZR, STANJE_ZAHTEVA, KLINICKI_CENTAR, PACIJENT, VERZIJA) VALUES
(1, 1, 1, 3, 0),
(2, 1, 1, 4, 0);

INSERT INTO ADMINI_KLINIKE (ID_ADMINK, EMAIL, IME, JMBG, LOZINKA, PREZIME, TIP_KORISNIKA, KLINIKA) VALUES
(1, 'ilijag123@hotmail.com', 'Marko', '1234567891011', 'nemam', 'Markovic', 1, 1),
(2, 'GDBxNS98@gmail.com', 'Mirko', '1234567891012', 'nema', 'Mirkovic', 1, 2);

INSERT INTO LEKAR (ID_LEKARA, EMAIL, IME, JMBG, LOZINKA, PREZIME, OCENA, TIP_KORISNIKA, KLINIKA) VALUES
(1, 'lekar1@gmail.com', 'Nikola', '1234567891041', '123', 'Milosevic', NULL, 2, 1),
(2, 'lekar2@gmail.com', 'Nikolas', '1234567891052', '123', 'Dragutinovic', NULL, 2, 2),
(3, 'lekar3@gmail.com', 'Milos', '1234567891063', '123', 'Jokic', NULL, 2, 1),
(4, 'anagrk@gmail.com', 'Marko', '1234567891864', '123', 'Markovic', NULL, 2, 1),
(5, 'lekar5@gmail.com', 'Nemanja', '9934567651052', '123', 'Nikolic', NULL, 2, 2);

INSERT INTO MEDICINSKE_SESTRE (ID_MED_SES, EMAIL, IME, JMBG, LOZINKA, PREZIME, OCENA, TIP_KORISNIKA, KLINIKA) VALUES
(1, 'sestra1@gmail.com', 'Sestraprva', '1234567891014', '111', 'Prezime', NULL, 3, 1),
(2, 'sestra2@gmail.com', 'Sestradruga', '1234567891015', '111', 'Prezim2', NULL, 3, 1),
(3, 'sestra3@gmail.com', 'Sestratreca', '1234567891016', '111', 'Prezime3', NULL, 3, 2);

INSERT INTO SALE (ID_SALE, NAZIV, TIP_SALE, KLINIKA, VERZIJA) VALUES
(1, 'sala1', 1, 1, 0),
(2, 'sala2', 1, 1, 0),
(3, 'sala3', 0, 2, 0),
(4, 'sala4', 1, 2, 0),
(5, 'sala5', 0, 1, 0);

INSERT INTO CENE (ID_CENE, IZNOS, CENOVNIK) VALUES
(1, 1000.0, NULL),
(2, 800.0, NULL),
(3, 450.0, NULL);

INSERT INTO TIPOVI_PREGLEDA (ID_TIPA_PREGLEDA, NAZIV, TRAJANJE, CENA) VALUES
(1, 'tip1', 60, 1),
(2, 'tip2', 15, 2),
(3, 'tip3', 45, 3);




INSERT INTO PREGLEDI (ID_PREGLEDA, DATE, POPUST, SLOBODAN, LEKAR, PACIJENT, SALA, TIP_PREGLEDA) VALUES
(1, '2020-05-19 11:00:00', '0.2', TRUE, 1, 6, 1, 1),
(2, '2020-05-31 11:30:00', '0.2', TRUE, 1, 5, 2, 2),
(3, '2020-04-04 11:45:00', '0.1', TRUE, 1, 7, 2, 2),
(4, '2020-03-27 13:30:00', '0.7', TRUE, 1, 1, 1, 3),
(5, '2020-03-26 12:15:00', '0.2', TRUE, 1, 2, 1, 1),
(6, '2020-03-27 14:15:00', '0.3', TRUE, 1, 8, 2, 1),
(7, '2020-01-04 09:45:00', '0.5', TRUE, 1, 6, 2, 2),
(8, '2020-05-24 08:00:00', '0.5', TRUE, 1, 6, 2, 2),
(9, '2020-05-21 14:00:00', '0.5', TRUE, 1, 6, 2, 2),
(10, '2020-05-27 09:45:00', '0.5', TRUE, 1, 1, 2, 2),
(11, '2020-08-01 10:45:00', '0', TRUE, 2, 1, 4, 1),
(12, '2020-06-10 10:00:00', '0', TRUE, 1, 2, 2, 1),
(13, '2020-08-01 12:45:00', '0', TRUE, 3, 3, 1, 2);

INSERT INTO RECEPTI (ID_RECEPTA, DATUM_IZDAVANJA_RECEPTA, STANJE_RECEPTA, LEKAR, MEDICINSKA_SESTRA, PREGLED) VALUES
(1, '07/05/2020 10:00:53', 0, 3, 1, 1),
(2, '06/05/2020 06:23:06', 1, 2, NULL, 2),
(3, '03/05/2020 11:34:54', 1, 1, NULL, 3),
(4, '04/05/2020 09:10:11', 1, 1, NULL, 4),
(5, '08/05/2020 08:00:07', 1, 3, NULL, 5);

INSERT INTO LEK_RECEPT (ID_RECEPTA, ID_LEKA) VALUES
(1,1),
(1,2),
(2,2),
(3,3),
(4,2),
(4,3),
(4,4);

INSERT INTO LEKAR_TIP_PREGLEDA (ID_LEKARA, ID_TIPA_PREGLEDA) VALUES
(1,1),
(1,2),
(1,3),
(2,1),
(2,2),
(3,1);

INSERT INTO LEKAR_OCENE (LEKAR_ID_LEKARA, OCENE) VALUES
(1,4),
(1,4),
(1,4),
(1,2),
(2,0),
(2,3),
(2,0),
(2,2),
(3,4),
(3,4),
(3,1),
(3,2);

INSERT INTO ZAHTEVI_ZA_GODISNJI (ID_ZZG, KRAJNJI_DATUM, POCETNI_DATUM, LEKAR, MEDICINSKA_SESTRA, STANJE) VALUES
(1, '2020-05-15 00:00:00', '2020-05-14 00:00:00', null, 1, 1),
(2, '2020-05-20 00:00:00', '2020-05-17 00:00:00', 1, null, 0),
(3, '2020-05-07 00:00:00', '2020-04-29 00:00:00', 1, null, 1);

INSERT INTO IZVESTAJI_PREGLEDA (ID_IZVESTAJA, OPIS, DIJAGNOZA, LEKAR, PREGLED, RECEPT, ZDRAVSTEVNI_KARTON) VALUES
(1, 'Pregled krajnika', 1, 1, 1,1, 1);

INSERT INTO OPERACIJE ( ID_OPERACIJE, DATUM, PACIJENT, SALA, TRAJANJE, VERZIJA) VALUES
(1, '2020-06-11 10:00:00', 1, 1, 30, 0),
(2, '2020-06-09 11:00:00', 1, 3, 15, 0),
(3, '2020-06-11 12:15:00', 2, 2, 60, 0);

INSERT INTO ZAHTEVI_ZA_OPERACIJU (ID_ZZO, STANJE_ZAHTEVA, OPERACIJA) VALUES
(1, 1, 1),
(2, 1, 2),
(3, 0, 3);

INSERT INTO LEKAR_OPERACIJA (ID_LEKARA, ID_OPERACIJE) VALUES
(1, 1),
(1, 2),
(1, 3);


INSERT INTO ZAHTEVI_ZA_PREGLED (ID_ZZP, STANJE_ZAHTEVA, DATUM_SLANJA, PREGLED) VALUES
(1, 1, '2020-06-15 00:00:00', 13);

INSERT INTO KLINIKA_OCENE (KLINIKA_ID_KLINIKE, OCENE) VALUES
(1,4),
(1,4),
(1,4),
(1,2),
(2,0),
(2,3),
(2,0),
(2,2),
(1,4),
(2,4),
(1,1),
(2,2);


