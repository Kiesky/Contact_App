CREATE TABLE IF NOT EXISTS people (
    idpeople INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    lastname VARCHAR(45) NOT NULL,
    firstname VARCHAR(45) NOT NULL,
    nickname VARCHAR(45) NOT NULL,
    phoneNumber VARCHAR(15) NULL,
    address VARCHAR(200) NULL,
    emailAddress VARCHAR(150) NULL,
    birthDate DATE NULL);