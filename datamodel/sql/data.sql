INSERT INTO weingutmerlot.weinarten (id, name, art) VALUES (1, 'Riesling', 'Weißwein');
INSERT INTO weingutmerlot.weinarten (id, name, art) VALUES (2, 'Portugieser', 'Rotwein');

INSERT INTO weingutmerlot.weine (id, weinart_id, suessegrad, alkoholgehalt, beschreibung) VALUES (1, 1, 'TROCKEN', 11.95, 'Der beste Riesling in der Pfalz');
INSERT INTO weingutmerlot.weine (id, weinart_id, suessegrad, alkoholgehalt, beschreibung) VALUES (3, 1, 'LIEBLICH', 10.85, 'Riesling Lieblich');

INSERT INTO weingutmerlot.gaerungsprozesse (id, weine_id, zuckergehalt, temperatur, dauer, lagerungsbehaelter) VALUES (1, 1, 610, 23, 42, 'Birkenholzfass');

INSERT INTO weingutmerlot.hefen (id, art, temperatur) VALUES (1, 'Wolfshefe', 25);
INSERT INTO weingutmerlot.hefen (id, art, temperatur) VALUES (2, 'Bauernhefe', 23);
INSERT INTO weingutmerlot.hefen (id, art, temperatur) VALUES (3, 'Trüffelhefe', 27);

INSERT INTO weingutmerlot.gaerungsprozessschritte (id, gaerungsprozess_id, nach_zeit, schritt, soll_zucker, soll_temperatur, soll_alkohol) VALUES (1, 1, 0, 1, 610, 25, 0);
INSERT INTO weingutmerlot.gaerungsprozessschritte (id, gaerungsprozess_id, nach_zeit, schritt, soll_zucker, soll_temperatur, soll_alkohol) VALUES (2, 1, 8, 2, 580, 25, 3);
INSERT INTO weingutmerlot.gaerungsprozessschritte (id, gaerungsprozess_id, nach_zeit, schritt, soll_zucker, soll_temperatur, soll_alkohol) VALUES (3, 1, 8, 3, 540, 23, 6);
INSERT INTO weingutmerlot.gaerungsprozessschritte (id, gaerungsprozess_id, nach_zeit, schritt, soll_zucker, soll_temperatur, soll_alkohol) VALUES (4, 1, 8, 4, 510, 23, 10);

INSERT INTO weingutmerlot.gaerungsprozessschritte_has_hefen (gaerungsprozessschritte_id, hefen_id, menge) VALUES (1, 1, 100);
INSERT INTO weingutmerlot.gaerungsprozessschritte_has_hefen (gaerungsprozessschritte_id, hefen_id, menge) VALUES (2, 1, 50);
INSERT INTO weingutmerlot.gaerungsprozessschritte_has_hefen (gaerungsprozessschritte_id, hefen_id, menge) VALUES (3, 2, 70);

INSERT INTO weingutmerlot.chargen (id, weintyp_id, jahrgang, lagerungsort, menge_in_liter, istFertig, istVerworfen) VALUES (1, 1, 2023, null, 100.000, false, false);
INSERT INTO weingutmerlot.chargen (id, weintyp_id, jahrgang, lagerungsort, menge_in_liter, istFertig, istVerworfen) VALUES (2, 1, 2023, null, 100.000, false, false);
INSERT INTO weingutmerlot.chargen (id, weintyp_id, jahrgang, lagerungsort, menge_in_liter, istFertig, istVerworfen) VALUES (3, 1, 2023, null, 100.000, false, false);

INSERT INTO weingutmerlot.ueberpruefungen (id, chargen_id, gaerungsprozessschritte_id, ist_zucker, ist_temperatur, ist_alkohol, anpassung_zucker, anpassung_temperatur, naechster_schritt, datum, next_date) VALUES (1, 1, 1, 0, 0, 0, 610, 25, true, NOW() - INTERVAL  10 DAY, null);
INSERT INTO weingutmerlot.ueberpruefungen (id, chargen_id, gaerungsprozessschritte_id, ist_zucker, ist_temperatur, ist_alkohol, anpassung_zucker, anpassung_temperatur, naechster_schritt, datum, next_date) VALUES (2, 1, 2, 580, 25, 3, 0, 0, true, NOW() - INTERVAL 6 DAY, null);
INSERT INTO weingutmerlot.ueberpruefungen (id, chargen_id, gaerungsprozessschritte_id, ist_zucker, ist_temperatur, ist_alkohol, anpassung_zucker, anpassung_temperatur, naechster_schritt, datum, next_date) VALUES (3, 1, 3, 530, 25, 6, 10, 0, false, NOW() - INTERVAL 4 DAY, NOW());
INSERT INTO weingutmerlot.ueberpruefungen (id, chargen_id, gaerungsprozessschritte_id, ist_zucker, ist_temperatur, ist_alkohol, anpassung_zucker, anpassung_temperatur, naechster_schritt, datum, next_date) VALUES (4, 2, 1, 0, 0, 0, 610, 25, true, NOW() + INTERVAL 10 DAY, null);
INSERT INTO weingutmerlot.ueberpruefungen (id, chargen_id, gaerungsprozessschritte_id, ist_zucker, ist_temperatur, ist_alkohol, anpassung_zucker, anpassung_temperatur, naechster_schritt, datum, next_date) VALUES (5, 3, 1, 0, 0, 0, 0, 0, false, NOW(), NOW());
