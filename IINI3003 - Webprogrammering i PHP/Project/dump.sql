CREATE TABLE IF NOT EXISTS users (
  userID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  userName VARCHAR( 20 ) NOT NULL,
  password VARCHAR( 30 ) NOT NULL
);

CREATE TABLE IF NOT EXISTS reise(
  travelID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  date DATE NOT NULL,
  destination VARCHAR(20) NOT NULL,
  description VARCHAR(50) NOT NULL,
  cost INT NOT NULL,
  payed BOOLEAN NOT NULL);

INSERT INTO reise (`travelID`, `date`, `destination`, `description`, `cost`, `payed`) VALUES
  (1, '2018-12-11', 'Fornebu', 'Lorem Ipsum', 1235, 1),
  (2, '2018-12-10', 'Kongsberg', 'Lorem Ipsum', 1056, 0),
  (3, '2018-12-05', 'Notodden', 'Lorem Ipsum', 468, 0),
  (4, '2018-11-27', 'Drammen', 'Lorem Ipsum', 1016, 1),
  (5, '2018-11-26', 'Tønsberg', 'Lorem Ipsum', 977, 0),
  (6, '2018-11-14', 'Porsgrunn', 'Lorem Ipsum', 810, 1),
  (7, '2018-11-08', 'Porsgrunn', 'Lorem Ipsum', 897, 1);
