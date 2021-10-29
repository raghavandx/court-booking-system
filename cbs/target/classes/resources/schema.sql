DROP TABLE IF EXISTS Court;

CREATE TABLE Court (
  id INT NOT NULL PRIMARY KEY,
  courtnumber INT NOT NULL,
  courtname VARCHAR(100) not null
);