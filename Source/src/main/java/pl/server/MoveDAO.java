package pl.server;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class MoveDAO {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int putGame(int players) {
        String query = "INSERT INTO games(players, saveDate) VALUES ("+players+", NOW());";
        return jdbcTemplate.update(query);
    }

    public int saveMove(Move m, int gameId){
        String query="INSERT INTO moves (gameId, moveFrom, moveTo, moveNr) VALUES ("+
                gameId +","+m.getFrom()+","+ m.getTo()+","+m.getMoveNr()+")";
        return jdbcTemplate.update(query);
    }

    public int getGameId() {
        String SQL = "SELECT MAX(id) FROM games";
        return jdbcTemplate.queryForObject(SQL, Integer.class);
    }

    public List<Move> getMoves(int gameId) {
        String SQL = "SELECT * FROM moves WHERE gameId = "+gameId;
        List<Move> game = jdbcTemplate.query(SQL, new MoveMapper());
        return game;
    }

}
