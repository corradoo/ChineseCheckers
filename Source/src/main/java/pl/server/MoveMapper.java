package pl.server;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MoveMapper implements RowMapper<Move> {

    public Move mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Move( rs.getInt(2), rs.getInt(3), rs.getInt(4));
    }
}
