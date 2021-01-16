package pl.server;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class MoveDAO {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int saveMove(Move m){
        String query="INSERT INTO moves (gameId, moveFrom, moveTo, moveNr) VALUES ("+
                m.getGameId() +","+m.getFrom()+","+ m.getTo()+","+m.getMoveNr()+")";
        return jdbcTemplate.update(query);
    }

}
