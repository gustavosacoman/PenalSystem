-- PRISONERS
INSERT INTO Prisoners (
    Id, ArrivalDate, OriginalReleaseDate, UpdatedReleaseDate,
    BooksCounter, CurrentYear, Name, BirthDate, CPF
) VALUES
(
    '11111111-1111-1111-1111-111111111111',
    NOW(),
    DATE_ADD(NOW(), INTERVAL 5 YEAR),
    DATE_ADD(NOW(), INTERVAL 5 YEAR),
    2,
    YEAR(NOW()),
    'João Silva',
    '1990-01-01',
    '12345678901'
),
(
    '22222222-2222-2222-2222-222222222222',
    NOW(),
    DATE_ADD(NOW(), INTERVAL 8 YEAR),
    DATE_ADD(NOW(), INTERVAL 8 YEAR),
    1,
    YEAR(NOW()),
    'Maria Souza',
    '1988-05-10',
    '23456789012'
),
(
    '33333333-3333-3333-3333-333333333333',
    NOW(),
    DATE_ADD(NOW(), INTERVAL 3 YEAR),
    DATE_ADD(NOW(), INTERVAL 3 YEAR),
    0,
    YEAR(NOW()),
    'Carlos Pereira',
    '1995-09-20',
    '34567890123'
);

-- EMPLOYEES
INSERT INTO Employees (
    Id, Email, Role, Password, Name, BirthDate, CPF
) VALUES
(
    'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
    'admin@sistema.com',
    1,
    '123456',
    'Administrador',
    '1985-01-01',
    '99999999999'
),
(
    'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb',
    'guarda@sistema.com',
    2,
    '123456',
    'Guarda',
    '1990-06-15',
    '88888888888'
);

-- BOOKS
INSERT INTO Books (
    Id, Isbn, Title, Author, PrisonerId, Date
) VALUES
(
    'b1b1b1b1-b1b1-b1b1-b1b1-b1b1b1b1b1b1',
    '12345678901',
    'Teaching to Trangress',
    'bell hooks',
    '11111111-1111-1111-1111-111111111111',
    NOW()
),
(
    'b2b2b2b2-b2b2-b2b2-b2b2-b2b2b2b2b2b2',
    '12345678902',
    'Crônica de Uma Morte Anunciada',
    'Gabriel García Márquez',
    '11111111-1111-1111-1111-111111111111',
    NOW()
),
(
    'b3b3b3b3-b3b3-b3b3-b3b3-b3b3b3b3b3b3',
    '12345678903',
    '2666',
    'Roberto Bolaño',
    '22222222-2222-2222-2222-222222222222',
    NOW()
),
(
    'b4b4b4b4-b4b4-b4b4-b4b4-b4b4b4b4b4b4',
    '12345678904',
    'Empire of AI',
    'Karen Hao',
    '33333333-3333-3333-3333-333333333333',
    NOW()
);

-- DAYS OF WORK
INSERT INTO DaysOfWork (
    Id, Description, PrisonerId, Date
) VALUES
(
    'd1d1d1d1-d1d1-d1d1-d1d1-d1d1d1d1d1d1',
    'Auxiliar de Cozinha',
    '11111111-1111-1111-1111-111111111111',
    NOW()
),
(
    'd2d2d2d2-d2d2-d2d2-d2d2-d2d2d2d2d2d2',
    'Lavanderia',
    '22222222-2222-2222-2222-222222222222',
    NOW()
);

-- STUDIES
INSERT INTO Studies (
    Id, Subject, PrisonerId, Date
) VALUES
(
    's1s1s1s1-s1s1-s1s1-s1s1-s1s1s1s1s1s1',
    'Matemática',
    '11111111-1111-1111-1111-111111111111',
    NOW()
),
(
    's2s2s2s2-s2s2-s2s2-s2s2-s2s2s2s2s2s2',
    'História',
    '33333333-3333-3333-3333-333333333333',
    NOW()
);