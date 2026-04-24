CREATE TABLE Prisoners (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    birth_date DATE NOT NULL,
    cpf CHAR(11) NOT NULL,
    arrival_date DATE NOT NULL,
    original_release_date DATE NOT NULL,
    updated_release_date DATE NOT NULL,
    books_counter INT NOT NULL,
    current_year INT NOT NULL
);