DROP TABLE IF EXISTS Bedrift;
DROP TABLE IF EXISTS Oppdrag;
DROP TABLE IF EXISTS Kvalifikasjon;
DROP TABLE IF EXISTS Kandidat;
DROP TABLE IF EXISTS Kobling;

CREATE TABLE Bedrift(
  bedriftNr INTEGER NOT NULL,
  bedriftNavn VARCHAR(20) NOT NULL,
  bedriftTelefon VARCHAR(20) NOT NULL,
  bedriftEpost VARCHAR(20) NOT NULL,
  CONSTRAINT bedrift_pk PRIMARY KEY()
);

CREATE TABLE Oppdrag(
  oppdragNr INTEGER NOT NULL,
  planStartDato DATE NOT NULL,
  planSluttDato DATE NOT NULL,
  virkeligStartDato DATE,
  virkeligSluttDato DATE,
  attest VARCHAR(100),
  bedreftNr INTEGER NOT NULL,
  kvalifikasjonNr INTEGER,
  kandidatNr INTEGER
);

CREATE TABLE Kobling(
  kandidatNr INTEGER,
  kvalifikasjonNr INTEGER
);

CREATE TABLE Kvalifikasjon(
  kvalifikasjonrNr INTEGER,
  kvalifikasjonNavn VARCHAR(20),
  kvalifikasjonBeskrivelse VARCHAR(100)
);

CREATE TABLE Kandidat(
  KandidatID INTEGER,
  fornavn VARCHAR(20),
  etternavn VARCHAR(20),
  tlf VARCHAR(20),
  epost VARCHAR(20)
);

-- 1
select bedrift.navn, bedrift.telefon, bedrift.epost from bedrift;

-- 2
select oppdrag.oppdragsnr, bedrift.navn, bedrift.telefon from oppdrag
join bedrift on bedrift.orgNr = oppdrag.orgNr;

-- 3
select kandidater.fornavn, kandidater.etternavn, kvalifikasjon.beskrivelse from kandidater
join kvalifikasjon_kandidat on kandidater.kandidatNr = kvalifikasjon_kandidat.kandidatNr
join kvalifikasjon on kvalifikasjon.kvalifikasjonsNr = kvalifikasjon_kandidat.kvalifikasjonrNr;

-- 4
select kandidater.fornavn, kandidater.etternavn, kvalifikasjon.beskrivelse from kvalifikasjon
join kvalifikasjon_kandidat on kvalifikasjon.kvalifikasjonsNr = kvalifikasjon_kandidat.kvalifikasjonrNr
right join kandidater on kandidater.kandidatNr = kvalifikasjon_kandidat.kandidatNr;

-- 5:
select kandidater.fornavn, kandidater.etternavn, oppdrag.virkeligSlutt, oppdrag.oppdragsNr, bedrift.navn
from kandidater join oppdrag on kandidater.kandidatNr = oppdrag.kandidatNr
join bedrift on bedrift.orgNr = oppdrag.orgNr
where kandidater.kandidatNr = 1;
