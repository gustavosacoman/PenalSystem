CREATE TABLE Studies (
    id VARCHAR(36) PRIMARY KEY,
    prisoner_id VARCHAR(36) NOT NULL,
    date DATETIME NOT NULL,
    subject VARCHAR(255) NOT NULL,
    FOREIGN KEY (prisoner_id) REFERENCES Prisoners(id)
);