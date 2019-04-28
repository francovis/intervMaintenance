--CREATE SCHEMA intervmaintenance DEFAULT CHARACTER SET utf8;

drop table intervention;
drop table pcunit;
drop table lotconfiguration;
drop table configurationPC;
drop table fournisseur;
drop table imagesw;
drop table typeintervention;

CREATE TABLE configurationpc (
  ConfigId int(11) NOT NULL,
  CarteMere varchar(25) NOT NULL,

  BioType varchar(10) NOT NULL,
  BioVersion varchar(10) NOT NULL,
  Processeur varchar(25) NOT NULL,
  MemoireVive int(11) NOT NULL,
  TailleDisque int(11) NOT NULL,
  CarteSon varchar(25),
  LecteurDvd varchar(25),
  PortsUsb int(11) NOT NULL,
  ImageTpsCharg int(11) NOT NULL,
  CommentaireCharg varchar(25),
  FkImageACharger varchar(25) NOT NULL,
  PRIMARY KEY (ConfigId)
    ) ENGINE=InnoDB CHARSET=utf8;

CREATE TABLE fournisseur (
  FournisseurId varchar(5) NOT NULL,
  NomFourn varchar(25) NOT NULL,
  AdrFourn varchar(25) NOT NULL,
  LocalFourn varchar(25) NOT NULL,
  CPFourn varchar(5) NOT NULL,
  NumTelFourn varchar(5) NOT NULL,
  AdrCourrielFourn varchar(25) NOT NULL,
  PRIMARY KEY (FournisseurId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE imagesw (
  NomImage varchar(25) NOT NULL,
  RisServer varchar(25) NOT NULL,
  TailleImage int(11) NOT NULL,
  DescrBreveImage varchar(25) NOT NULL,
  PRIMARY KEY (NomImage)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE intervention (
  NoInterv int(11) NOT NULL,
  DateSignalement date NOT NULL,
  DescriptifBrefProblème varchar(25),
  SignaleurIncident varchar(25),
  PreneurEnCharge varchar(25) NOT NULL,
  EtatInterv varchar(15) NOT NULL,
  SuiviViaFournisseur tinyint(1) NOT NULL,
  DateContact date,
  DatePrise date,
  DateRetour date,
  EtatRetour varchar(12),
  DateRemiseService date,
  TempsInterne int(11),
  Résultat varchar(15),
  
  FkPcUnit varchar(25) NOT NULL,
  FkTypeInterv varchar(15) NOT NULL,
  FkFournisseurIntervenant varchar(5),PRIMARY KEY (NoInterv))
  ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE lotconfiguration (
  NoLot int(11) NOT NULL,
  DateAcquisition date NOT NULL,
  DateFinGarantie date NOT NULL,
  NbreUnitésAcquises int(11) NOT NULL,
  FkFournisseurLot varchar(5) NOT NULL,
  FkConfig int(11) NOT NULL,
  PRIMARY KEY (NoLot)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE pcunit (
  IdPcUnit varchar(25) NOT NULL,
  Particularité varchar(25),
  Local varchar(5),
  FkLot int(11) DEFAULT  NULL,
  
  PRIMARY KEY (IdPcUnit)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE typeintervention (
  CodeTypeInt varchar(10) NOT NULL,
  LibelleTypeInt varchar(45) NOT NULL,
  PRIMARY KEY (CodeTypeInt)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
alter table intervention add constraint cr1 foreign key (fkpcunit) references pcunit(IdPcUnit) on delete cascade;
alter table intervention add constraint cr2 foreign key (fkfournisseurIntervenant) references Fournisseur(FournisseurId) on delete cascade;
alter table intervention add constraint cr3 foreign key (fkTypeInterv) references TypeIntervention(CodeTypeInt) on delete cascade;
alter table pcunit add constraint cr4 foreign key (fkLot) references lotconfiguration(NoLot) on delete cascade;
alter table lotconfiguration add constraint cr5 foreign key (fkconfig) references configurationpc(ConfigId) on delete cascade;
alter table lotconfiguration add constraint cr6 foreign key (fkfournisseurlot) references Fournisseur(FournisseurId) on delete cascade;
alter table configurationpc add constraint cr7 foreign key (fkImageACharger) references ImageSW(NomImage) on delete cascade;

insert into imagesw values
('IntelWindows7Sp1','WDS',60,'IntelWindows7Sp1');
insert into imagesw values
('IntelWindowsXPSp2','Ris',32,'IntelWindowsXPSp2');
insert into imagesw values
('IntelWindows7Sp2','Ris',32,'IntelWindows7Sp2');
insert into imagesw values
('IntelWindows8Sp1','Ris',32,'IntelWindows8Sp1');

insert into configurationpc values
( 201209,'Intel','RealTek','1,8','Core I3 2 GHz',2048,200,'Creative Pnp','DVDR40',2,45,'Ok','IntelWindowsXPSp2');
insert into configurationpc values
( 201210,'Intel','Amibios','1,8','Core I3 2 GHz',2048,200,'Creative Pnp','DVDR40',2,45,'Ok','IntelWindowsXPSp2');
insert into configurationpc values
( 201309,'Azus','BioStar','2,0','Core I5 2,7GHz',4096,250,'Integrated','DVDR40',4,30,'Ok','IntelWindows7Sp2');
insert into configurationpc values
( 201310,'Intel','Magellan','2,1','Core I5 2,7GHz',8192,250,'Integrated','DVDR40',4,75,'Trop Long','IntelWindows7Sp2');
insert into configurationpc values
( 201409,'Intel','BioStar','2,2','CoreI7 3 GHz',8192,500,'Integrated','DVDR40',6,60,'','IntelWindows8Sp1');

insert into fournisseur values 
('F01','Priminfo','Noville','Noville','5600','081','priminfo@');
insert into fournisseur values 
('F02','SHS','Rue des croisiers','Liege','4000','04','shs@');
insert into fournisseur values 
('F03','BMX','Rue du village 27','Sauvenière','5030','081','bmx@');

insert into LotConfiguration values (
20120901,'2012-06-12' ,'2016-06-12',48,'F01',201209);

insert into LotConfiguration values (
20130901,'2013-05-12','2017-05-12',50,'F01',201309);

insert into LotConfiguration values (
20131001,'2013-09-12','2017-09-12',72,'F02',201310);

insert into LotConfiguration values (
20140901,'2014-9-12','2018-9-12',25,'F02',201409);

insert into LotConfiguration values (
20140902,'2014-9-17','2018-9-12',50,'F03',201409);
insert into LotConfiguration values (
20140903,'2014-9-17','2018-9-12',50,'F03',201409);


insert into PcUnit values('W2530A01','nocomment','L116',20140901);
insert into PcUnit values(
'W2930A02','no comment','L116',20140901);
insert into PcUnit values(
'W2930A03','no comment','L116',20140901);
insert into PcUnit values(
'W2930A12','no Comment','L125',20140901);
insert into PcUnit values(
'W2930A13','Sans DVD','L201',20140901);
insert into PcUnit values(
'W2930A14','Sans DVD','L204',20140901);
insert into PcUnit values(
'W2930A25','Null','L210',20140902);
insert into PcUnit values(
'W3100A12','Null','L005',20131001);
insert into PcUnit values(
'W3200A13','Null','L005',20131001);
insert into PcUnit values(
'W3400A01','Null','L005',20131001);
insert into typeintervention values(
'I01','Panne Disque'
);
insert into typeintervention values(
'I02','Carte Mère'
);
insert into typeintervention values(
'I03','OS'

);
insert into typeintervention values(
'I04','Applications'

);
insert into typeintervention values(
'I11','Alimentation'

);
insert into typeintervention values(
'I21','Carte réseau'
);
insert into typeintervention values(
'I31','Repréparation de la machine'
);
insert into typeintervention values(
'I32','DVD'
);
INSERT INTO Intervention 
VALUES(1,'2015-1-2','',null,'ddddd','Signalé',true,null,null,null,null,null,'50','Déclassé','W3100A12','I01','F01');
INSERT INTO Intervention 
VALUES(4,'2015-4-23','',null,'ddd','Signalé',true,null,null,null,null,null,null,'Déclassé','W3200A13','I11','F03');