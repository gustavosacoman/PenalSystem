CREATE TABLE DaysOfWork (
    id VARCHAR(36) PRIMARY KEY,
    prisoner_id VARCHAR(36) NOT NULL,
    date DATETIME NOT NULL,
    description TEXT NOT NULL,
    FOREIGN KEY (prisoner_id) REFERENCES Prisoners(id)
);