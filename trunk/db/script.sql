DROP TABLE SM_NOTE;
DROP TABLE SM_SCHEDULE;
DROP TABLE SM_RING;
DROP TABLE SM_DAY;
DROP TABLE SM_CLASSROOM;
DROP TABLE SM_PERSON2CLASS;
DROP TABLE SM_SUBJECT;
DROP TABLE SM_TEACHER;
DROP TABLE SM_MESSAGE;
DROP TABLE SM_PERSON_NOTES;
DROP TABLE SM_USER;
DROP TABLE SM_ROLE;
DROP TABLE SM_CLASS;
DROP TABLE SM_PERSON;

CREATE TABLE SM_PERSON(
PER_ID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
PER_NAME VARCHAR(30) NOT NULL,
PER_SURNAME VARCHAR(50) NOT NULL,
PER_PESEL VARCHAR(32) NOT NULL,
PER_NIP VARCHAR(32),
PER_PHONE VARCHAR(32),
PER_ADRESS VARCHAR(300) NOT NULL,
PER_EMAIL VARCHAR(100)
) ENGINE=INNODB;


CREATE TABLE SM_PERSON_NOTES( 
PN_ID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
PN_PER_ID INT,
PN_NOTE VARCHAR(2000),
INDEX PN_PER_IDX (PN_PER_ID), 
FOREIGN KEY (PN_PER_ID) REFERENCES SM_PERSON(PER_ID) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=INNODB;

CREATE TABLE SM_ROLE(
ROL_ID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
ROL_NAME VARCHAR(50) NOT NULL
) ENGINE=INNODB;

CREATE TABLE SM_USER (
USR_ID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
USR_LOGIN VARCHAR(50) NOT NULL,
USR_PASSWD VARCHAR(100) NOT NULL,
USR_ISLOGGED BIT NOT NULL DEFAULT 0,
USR_PER_ID INT,
USR_ROL_ID INT,
INDEX USR_PERID_IDX(USR_PER_ID),
INDEX USR_ROLID_IDX(USR_ROL_ID),
FOREIGN KEY (USR_PER_ID) REFERENCES SM_PERSON(PER_ID) ON DELETE CASCADE ON UPDATE CASCADE,
FOREIGN KEY (USR_ROL_ID) REFERENCES SM_ROLE(ROL_ID) ON UPDATE CASCADE ON DELETE SET NULL,
UNIQUE(USR_LOGIN)
)ENGINE = INNODB;

CREATE TABLE SM_CLASS (
CLS_ID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
CLS_NUMBER INT(1),
CLS_NUMBER_ALPH VARCHAR(20),
CLS_DESCRIPTION VARCHAR(255),
CLS_PER_ID INT,
INDEX CLS_PERID_IDX(CLS_PER_ID),
FOREIGN KEY(CLS_PER_ID) REFERENCES SM_PERSON(PER_ID) ON DELETE SET NULL ON UPDATE CASCADE
)ENGINE = INNODB;


CREATE TABLE SM_MESSAGE (
MSG_ID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
MSG_RECP_USR_ID INT,
MSG_SENDER_USR_ID INT,
MSG_SEND_DATE DATETIME NOT NULL,
MSG_BODY TEXT,
MSG_TOPIC VARCHAR(500),
MSG_PARENT_ID INT,
MSG_CHILD_ID INT,
MSG_RESP_REQ BIT DEFAULT 0,
MSG_READED BIT DEFAULT 0,
MSG_SEVERITY INT(11),
INDEX MSG_RECPUSRID_IDX(MSG_RECP_USR_ID),
INDEX MSG_SENDERUSRID_IDX(MSG_SENDER_USR_ID),
FOREIGN KEY (MSG_RECP_USR_ID) REFERENCES SM_USER(USR_ID),
FOREIGN KEY (MSG_SENDER_USR_ID) REFERENCES SM_USER(USR_ID)
)ENGINE = INNODB;


CREATE TABLE SM_SUBJECT (
SUB_ID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
SUB_NAME VARCHAR(200) NOT NULL
)ENGINE = INNODB;


CREATE TABLE SM_TEACHER (
TCH_ID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
TCH_PER_ID INT,
TCH_SUB_ID INT,
INDEX TCH_PERID_IDX(TCH_PER_ID),
INDEX TCH_SUBID_IDX(TCH_SUB_ID),
FOREIGN KEY (TCH_PER_ID) REFERENCES SM_PERSON(PER_ID) ON DELETE CASCADE ON UPDATE CASCADE,
FOREIGN KEY (TCH_SUB_ID) REFERENCES SM_SUBJECT(SUB_ID) ON DELETE SET NULL ON UPDATE CASCADE
)ENGINE = INNODB;


CREATE TABLE SM_PERSON2CLASS (
P2C_ID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
P2C_PER_ID INT,
P2C_CLS_ID INT,
INDEX P2C_PERID_IDX(P2C_PER_ID),
INDEX P2C_CLSID_IDX(P2C_CLS_ID),
FOREIGN KEY (P2C_PER_ID) REFERENCES SM_PERSON(PER_ID) ON DELETE CASCADE ON UPDATE CASCADE,
FOREIGN KEY (P2C_CLS_ID) REFERENCES SM_CLASS(CLS_ID) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE = INNODB;

CREATE TABLE SM_CLASSROOM (
CLR_ID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
CLR_OWNER_PER_ID INT,
CLR_NUMER INT(11),
CLR_DESCR VARCHAR(100),
INDEX CLR_OWNPERID_IDX(CLR_OWNER_PER_ID),
FOREIGN KEY (CLR_OWNER_PER_ID) REFERENCES SM_PERSON(PER_ID) ON DELETE SET NULL ON UPDATE CASCADE
)ENGINE = INNODB;

CREATE TABLE SM_DAY(
DAY_ID INT PRIMARY KEY NOT NULL,
DAY_NAME VARCHAR(50)
)ENGINE = INNODB;

CREATE TABLE SM_RING(
RNG_ID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
RNG_TIME TIME NOT NULL
)ENGINE = INNODB;

CREATE TABLE `SM_SCHEDULE` (
  `SCH_ID` int(11) NOT NULL auto_increment,
  `SCH_DAY_ID` int(11) default NULL,
  `SCH_RNG_ID` int(11) default NULL,
  `SCH_CLS_ID` int(11) default NULL,
  `SCH_CLR_ID` int(11) default NULL,
  `SCH_TCH_PER_ID` int(11) default NULL,
  `SCH_SUB_ID` int(11) default NULL,
  PRIMARY KEY  (`SCH_ID`),
  KEY `SCH_DAYID_IDX` (`SCH_DAY_ID`),
  KEY `SCH_RNGID_IDX` (`SCH_RNG_ID`),
  KEY `SCH_CLSID_IDX` (`SCH_CLS_ID`),
  KEY `SCH_CLRID_IDX` (`SCH_CLR_ID`),
  KEY `SCH_TCH_PER_ID` (`SCH_TCH_PER_ID`),
  KEY `SCH_SUB_ID` (`SCH_SUB_ID`),
  CONSTRAINT `SM_SCHEDULE_ibfk_2` FOREIGN KEY (`SCH_DAY_ID`) REFERENCES `SM_DAY` (`DAY_ID`) ON UPDATE CASCADE,
  CONSTRAINT `SM_SCHEDULE_ibfk_3` FOREIGN KEY (`SCH_RNG_ID`) REFERENCES `SM_RING` (`RNG_ID`) ON UPDATE CASCADE,
  CONSTRAINT `SM_SCHEDULE_ibfk_4` FOREIGN KEY (`SCH_CLS_ID`) REFERENCES `SM_CLASS` (`CLS_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `SM_SCHEDULE_ibfk_5` FOREIGN KEY (`SCH_CLR_ID`) REFERENCES `SM_CLASSROOM` (`CLR_ID`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `SM_SCHEDULE_ibfk_6` FOREIGN KEY (`SCH_TCH_PER_ID`) REFERENCES `SM_PERSON` (`PER_ID`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `SM_SCHEDULE_ibfk_7` FOREIGN KEY (`SCH_SUB_ID`) REFERENCES `SM_SUBJECT` (`SUB_ID`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE `SM_NOTE` (
  `NOT_ID` int(11) NOT NULL auto_increment,
  `NOT_NOTE` text,
  `NOT_P2C_ID` int(11) default NULL,
  `NOT_SUB_ID` int(11) default NULL,
  `NOT_TCH_PER_ID` int(11) default NULL,
  `NOT_COMMENT` text,
  `NOT_DATE` datetime default '0000-00-00 00:00:00',
  PRIMARY KEY  (`NOT_ID`),
  KEY `SCH_P2C_IDX` (`NOT_P2C_ID`),
  KEY `NOT_TCH_PER_ID` (`NOT_TCH_PER_ID`),
  KEY `NOT_SUB_ID` (`NOT_SUB_ID`),
  CONSTRAINT `SM_NOTE_ibfk_2` FOREIGN KEY (`NOT_P2C_ID`) REFERENCES `SM_PERSON2CLASS` (`P2C_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `SM_NOTE_ibfk_3` FOREIGN KEY (`NOT_TCH_PER_ID`) REFERENCES `SM_PERSON` (`PER_ID`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `SM_NOTE_ibfk_4` FOREIGN KEY (`NOT_SUB_ID`) REFERENCES `SM_SUBJECT` (`SUB_ID`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB;