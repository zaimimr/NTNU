DROP TABLE IF EXISTS ordredetalj;
DROP TABLE IF EXISTS prisinfo;
DROP TABLE IF EXISTS ordrehode;
DROP TABLE IF EXISTS delinfo;
DROP TABLE IF EXISTS levinfo;

CREATE TABLE delinfo(
delnr       INTEGER,
beskrivelse VARCHAR(30) NOT NULL,
CONSTRAINT delinfo_pk PRIMARY KEY(delnr));

CREATE TABLE levinfo(
levnr   INTEGER,
navn    VARCHAR(20) NOT NULL,
adresse VARCHAR(20) NOT NULL,
levby   VARCHAR(20) NOT NULL,
fylke   VARCHAR(20) NOT NULL,
postnr  INTEGER NOT NULL,
CONSTRAINT levinfo_pk PRIMARY KEY(levnr));

CREATE TABLE ordrehode(
ordrenr INTEGER,
dato    DATE NOT NULL,
levnr   INTEGER NOT NULL,
status  CHAR(1) NOT NULL,
CONSTRAINT ordrehode_pk PRIMARY KEY(ordrenr),
CONSTRAINT ordrehode_fk FOREIGN KEY(levnr) REFERENCES levinfo(levnr));

CREATE TABLE ordredetalj(
ordrenr    INTEGER,
delnr        INTEGER NOT NULL,
kvantum   INTEGER NOT NULL,
CONSTRAINT ordredetalj_pk PRIMARY KEY(ordrenr,delnr),
CONSTRAINT ordredetalj_fk1 FOREIGN KEY(ordrenr) REFERENCES ordrehode(ordrenr),
CONSTRAINT ordredetalj_fk2 FOREIGN KEY(delnr) REFERENCES delinfo(delnr));

CREATE TABLE prisinfo(
delnr     INTEGER,
levnr     INTEGER,
katalognr CHAR(6),
pris      REAL,
CONSTRAINT prisinfo_pk PRIMARY KEY(delnr, levnr),
CONSTRAINT prisinfo_fk1 FOREIGN KEY(delnr)REFERENCES delinfo(delnr),
CONSTRAINT prisinfo_fk2 FOREIGN KEY(levnr)REFERENCES levinfo(levnr));

insert into delinfo(delnr, beskrivelse) values(51173,'Binders');
insert into delinfo(delnr, beskrivelse) values(1,'Kontorstol');
insert into delinfo(delnr, beskrivelse) values(51200,'Linjalsett');
insert into delinfo(delnr, beskrivelse) values(3,'Pult');
insert into delinfo(delnr, beskrivelse) values(4,'Skrivebord');
insert into delinfo(delnr, beskrivelse) values(1909,'Skriveunderlag');
insert into delinfo(delnr, beskrivelse) values(201,'Svarte kulepenner');
insert into delinfo(delnr, beskrivelse) values(202,'Blå kulepenner');

insert into levinfo(levnr, navn, adresse, levby, fylke, postnr) values(6,'Kontorekspressen AS','Skolegata 3','Oslo','Oslo',1234);
insert into levinfo(levnr, navn, adresse, levby, fylke, postnr) values(82,'Kontordata AS','Åsveien 178','Trondheim','S-Trøndelag',7023);
insert into levinfo(levnr, navn, adresse, levby, fylke, postnr) values(9,'Kontorutstyr AS','Villa Villekulla','Ås','Østfold',1456);
insert into levinfo(levnr, navn, adresse, levby, fylke, postnr) values(44,'Billig og Bra AS','Aveny 56','Oslo','Oslo',1222);
insert into levinfo(levnr, navn, adresse, levby, fylke, postnr) values(12,'Mister Office AS','Storgt 56','Ås','Østfold',1456);
insert into levinfo(levnr, navn, adresse, levby, fylke, postnr) values(81,'Kontorbutikken AS','Gjennomveien 78','Ål','Telemark',3345);

insert into ordrehode(ordrenr, dato, levnr, status)  values(11,'1986-05-10',6,'c');
insert into ordrehode(ordrenr, dato, levnr, status)  values(12,'1986-07-17',82,'c');
insert into ordrehode(ordrenr, dato, levnr, status)  values(13,'1986-09-13',44,'p');
insert into ordrehode(ordrenr, dato, levnr, status)  values(14,'1986-12-17',44,'p');
insert into ordrehode(ordrenr, dato, levnr, status)  values(15,'1987-01-03',44,'p');
insert into ordrehode(ordrenr, dato, levnr, status)  values(16,'1987-01-31',6,'c');
insert into ordrehode(ordrenr, dato, levnr, status)  values(17,'1987-05-14',6,'c');
insert into ordrehode(ordrenr, dato, levnr, status)  values(18,'1987-05-12',82,'p');


insert into ordredetalj(ordrenr, delnr, kvantum) values(11,1,5);
insert into ordredetalj(ordrenr, delnr, kvantum) values(11,201,100);
insert into ordredetalj(ordrenr, delnr, kvantum) values(11,202,100);
insert into ordredetalj(ordrenr, delnr, kvantum) values(11,1909,6);
insert into ordredetalj(ordrenr, delnr, kvantum) values(11,51200,20);
insert into ordredetalj(ordrenr, delnr, kvantum) values(12,3,2);
insert into ordredetalj(ordrenr, delnr, kvantum) values(12,201,50);
insert into ordredetalj(ordrenr, delnr, kvantum) values(12,202,60);
insert into ordredetalj(ordrenr, delnr, kvantum) values(13,51173,20);
insert into ordredetalj(ordrenr, delnr, kvantum) values(14,201,100);
insert into ordredetalj(ordrenr, delnr, kvantum) values(14,202,100);
insert into ordredetalj(ordrenr, delnr, kvantum) values(14,51173,30);
insert into ordredetalj(ordrenr, delnr, kvantum) values(15,201,100);
insert into ordredetalj(ordrenr, delnr, kvantum) values(15,202,100);
insert into ordredetalj(ordrenr, delnr, kvantum) values(16,201,50);
insert into ordredetalj(ordrenr, delnr, kvantum) values(16,202,50);
insert into ordredetalj(ordrenr, delnr, kvantum) values(16,51173,20);
insert into ordredetalj(ordrenr, delnr, kvantum) values(16,1909,10);
insert into ordredetalj(ordrenr, delnr, kvantum) values(17,1,10);
insert into ordredetalj(ordrenr, delnr, kvantum) values(17,3,1);
insert into ordredetalj(ordrenr, delnr, kvantum) values(17,4,5);
insert into ordredetalj(ordrenr, delnr, kvantum) values(18,3,2);
insert into ordredetalj(ordrenr, delnr, kvantum) values(18,4,8);

insert into prisinfo(delnr, levnr, katalognr, pris) values(51173,6,'37S',0.57);
insert into prisinfo(delnr, levnr, katalognr, pris) values(51173,44,'312/2',0.44);
insert into prisinfo(delnr, levnr, katalognr, pris) values(51173,82,'300021',0.35);
insert into prisinfo(delnr, levnr, katalognr, pris) values(1,6,'97s',120.00);
insert into prisinfo(delnr, levnr, katalognr, pris) values(1,9,'x120',219.99);
insert into prisinfo(delnr, levnr, katalognr, pris) values(51200,6,'54s',7.35);
insert into prisinfo(delnr, levnr, katalognr, pris) values(1909,9,'X7770',3.00);
insert into prisinfo(delnr, levnr, katalognr, pris) values(201,44,'100/1',1.60);
insert into prisinfo(delnr, levnr, katalognr, pris) values(201,6,'21s',1.90);
insert into prisinfo(delnr, levnr, katalognr, pris) values(202,44,'101/1',1.50);
insert into prisinfo(delnr, levnr, katalognr, pris) values(202,9,'22s',1.76);
insert into prisinfo(delnr, levnr, katalognr, pris) values(3,82,'2077',1299.00);
insert into prisinfo(delnr, levnr, katalognr, pris) values(4,82,'2177',899.00);
insert into prisinfo(delnr, levnr, katalognr, pris) values(201,82,'3140',2.60);
insert into prisinfo(delnr, levnr, katalognr, pris) values(202,82,'3141',1.50);
insert into prisinfo(delnr, levnr, katalognr, pris) values(3,6,'34P',1199.00);
insert into prisinfo(delnr, levnr, katalognr, pris) values(4,6,'67P',550.00);
insert into prisinfo(delnr, levnr, katalognr, pris) values(1909,6,'53P',0.85);
insert into prisinfo(delnr, levnr, katalognr, pris) values(202,6,'345u',6.50);
insert into prisinfo(delnr, levnr, katalognr, pris) values(3,9,'a48',1050.00);

------------------------------------------------------
#1
--a
SELECT * FROM ordrehode JOIN ordredetalj USING(ordrenr) WHERE ordrenr = 44;
--b
SELECT navn, levby FROM levinfo JOIN prisinfo USING (levnr) WHERE delnr = 1;
--c
SELECT levnr, navn, pris FROM levinfo JOIN prisinfo USING (levnr) WHERE delnr = 201 ORDER BY pris limit 1;
--d
SELECT ordrenr, dato, delnr, beskrivelse, kvantum, pris, pris*kvantum "Beregnet beløp" FROM levinfo JOIN ordrehode USING(levnr)
JOIN ordredetalj USING(ordrenr) JOIN delinfo USING(delnr) JOIN prisinfo USING (delnr) WHERE ordrenr = 16;
--e
SELECT delnr, levnr FROM prisinfo WHERE pris > ANY(SELECT pris FROM prisinfo WHERE katalognr = "X7770");
--f
--i
CREATE TABLE levomrade(
  levby   VARCHAR(20) NOT NULL,
  fylke   VARCHAR(20) NOT NULL,
  CONSTRAINT levomrade_pk PRIMARY KEY(levby))
)
INSERT INTO levomrade (levby, fylke) SELECT DISTINCT levby, fylke FROM levinfo;

CREATE TABLE levinfoutenby(
  levnr   INTEGER,
  navn    VARCHAR(20) NOT NULL,
  adresse VARCHAR(20) NOT NULL,
  levby   VARCHAR(20) NOT NULL,
  postnr  INTEGER NOT NULL,
  CONSTRAINT levinfoutenby_pk PRIMARY KEY(levnr));
)
INSERT INTO levinfoutenby(levnr, navn, adresse, levby, postnr) SELECT DISTINCT levnr, navn, adresse, levby, postnr FROM levinfo;
--ii
CREATE VIEW levinfoer AS SELECT * FROM levomrade JOIN levinfoutenby USING(levby);

SELECT `levby`, `fylke`, `levnr`, `navn`, `adresse`, `postnr` FROM `levinfoer` WHERE levby = "Oslo";
--Fungerer bra
UPDATE `levinfoer` SET `levby`='Kristansand',`fylke`='Vest Agder',`levnr`='69',`navn`='Nikkos flyttekasse',`adresse`='Mordi',`postnr`=1339 WHERE levby = 'Ål';
--Kan ikke modifisere mer enn 1 base table i virtuelle tabellene, men fungerer hvis man endrer en tabell av gangen
INSERT INTO `levinfoer`(`levby`, `fylke`, `levnr`, `navn`, `adresse`, `postnr`) VALUES ("Afrika", "Zimbabwe", "139", "Kukhest", "sor", 13);
--Kan ikke modifisere mer enn 1 base table i virtuelle tabellene, men fungerer hvis man endrer en tabell av gangen
DELETE FROM `levinfoer` WHERE 0;
--Can not delete from join view 'zaims database.levinfoer'
--g
CREATE VIEW OppgaveG AS SELECT * FROM levinfo JOIN prisinfo USING(levnr);

SELECT DISTINCT levby FROM levinfo WHERE levby NOT IN (SELECT DISTINCT levby FROM prisinfo JOIN levinfo USING(levnr) GROUP BY levby)
--h
SELECT levnr, totalpris FROM (SELECT b.tot as tot, SUM(pris) as totalpris, levnr
FROM (SELECT levnr, pris, ordrenr FROM prisinfo JOIN ordredetalj USING(delnr) WHERE ordrenr=18) as a
JOIN (SELECT COUNT(*) as tot, ordrenr FROM ordredetalj GROUP BY ordrenr) as b USING(ordrenr)
GROUP BY levnr HAVING COUNT(pris)=tot) as c ORDER by totalpris ASC LIMIT 1;
