-- Employees
CREATE TABLE Employees (
    Id CHAR(36) PRIMARY KEY,
    Email VARCHAR(100) NOT NULL,
    Role INT NOT NULL,
    Password VARCHAR(50) NOT NULL,
    Name VARCHAR(100) NOT NULL,
    BirthDate VARCHAR(50) NOT NULL,
    CPF CHAR(11) NOT NULL
);

-- Prisoners
CREATE TABLE Prisoners (
    Id CHAR(36) PRIMARY KEY,
    ArrivalDate DATETIME NOT NULL,
    OriginalReleaseDate DATETIME NOT NULL,
    UpdatedReleaseDate DATETIME NOT NULL,
    BooksCounter INT NOT NULL,
    CurrentYear INT NOT NULL,
    Name VARCHAR(100) NOT NULL,
    BirthDate VARCHAR(50) NOT NULL,
    CPF CHAR(11) NOT NULL
);

-- Books
CREATE TABLE Books (
    Id CHAR(36) PRIMARY KEY,
    Isbn CHAR(11) NOT NULL,
    PrisonerId CHAR(36) NOT NULL,
    Date DATETIME NOT NULL,
    CONSTRAINT FK_Books_Prisoners
        FOREIGN KEY (PrisonerId)
        REFERENCES Prisoners(Id)
        ON DELETE CASCADE
);

CREATE INDEX IX_Books_PrisonerId ON Books(PrisonerId);

-- DaysOfWork
CREATE TABLE DaysOfWork (
    Id CHAR(36) PRIMARY KEY,
    Description TEXT NOT NULL,
    PrisonerId CHAR(36) NOT NULL,
    Date DATETIME NOT NULL,
    CONSTRAINT FK_DaysOfWork_Prisoners
        FOREIGN KEY (PrisonerId)
        REFERENCES Prisoners(Id)
        ON DELETE CASCADE
);

CREATE INDEX IX_DaysOfWork_PrisonerId ON DaysOfWork(PrisonerId);

-- Studies
CREATE TABLE Studies (
    Id CHAR(36) PRIMARY KEY,
    Subject TEXT NOT NULL,
    PrisonerId CHAR(36) NOT NULL,
    Date DATETIME NOT NULL,
    CONSTRAINT FK_Studies_Prisoners
        FOREIGN KEY (PrisonerId)
        REFERENCES Prisoners(Id)
        ON DELETE CASCADE
);

CREATE INDEX IX_Studies_PrisonerId ON Studies(PrisonerId);