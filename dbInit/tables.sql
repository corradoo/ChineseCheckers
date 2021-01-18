CREATE TABLE games(
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      players INT,
                      startDate DATETIME
);

CREATE TABLE moves(
                      gameId INT,
                      moveFrom INT,
                      moveTo INT,
                      constraint moves_gameId_fk
                          foreign key (gameId) references games (id)
)