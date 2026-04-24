CREATE TABLE Books{
    id VARCHAR(36) PRIMARY KEY,
    prisoner_id VARCHAR(36) NOT NULL,
    date DATETIME NOT NULL,
    isbn CHAR(11) NOT NULL,
    FOREIGN KEY (prisoner_id) REFERENCES Prisoners(id)
};