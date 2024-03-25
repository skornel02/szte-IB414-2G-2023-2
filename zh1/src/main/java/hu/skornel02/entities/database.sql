create table Bunozo
(
    Id          integer not null constraint bunozo_pk
                primary key autoincrement,
    Nev         TEXT,
    Banda       TEXT,
    Korozes     integer
);

select Id, Nev, Banda, Korozes from Bunozo;

INSERT INTO Bunozo (Nev, Banda, Korozes) VALUES (?, ?, ?)