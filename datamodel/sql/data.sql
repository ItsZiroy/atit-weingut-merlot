INSERT INTO weingutmerlot.weinarten (id, name, art) VALUES (1, 'Riesling', 'Weißwein');
INSERT INTO weingutmerlot.weinarten (id, name, art) VALUES (2, 'Portugieser', 'Rotwein');

INSERT INTO weingutmerlot.weine (id, weinart_id, suessegrad, alkoholgehalt, beschreibung) VALUES (1, 1, 'Trocken', 11.95, 'Der beste Riesling in der Pflaz');
INSERT INTO weingutmerlot.weine (id, weinart_id, suessegrad, alkoholgehalt, beschreibung) VALUES (3, 1, 'Lieblich', 10.85, 'Riesling Lieblich');

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