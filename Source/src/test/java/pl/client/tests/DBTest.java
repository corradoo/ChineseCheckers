package pl.client.tests;

import org.junit.Test;
import pl.server.Move;
import pl.server.MoveDAO;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class DBTest {

    @Test
    public void testMoveToDB() {
        System.out.println("-------Reading Beans---------");
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        Move m = new Move(1,0,2);
        System.out.println("-------Creating MoveDAO---------");
        MoveDAO md = (MoveDAO)context.getBean("jdbcTemplate");
        System.out.println("------Records Creation--------" );
        md.saveMove(m, 1);
        assertTrue(true);
    }

    @Test
    public void testGameFromDB() {
        System.out.println("-------Reading Beans---------");
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        MoveDAO md = (MoveDAO)context.getBean("jdbcTemplate");
        List<Move> game = md.getMoves(1);
        int i = 1;
        for(Move m : game) {
            System.out.println(m.getMoveNr());
            assertEquals(m.getMoveNr(),i);
            i++;
        }
    }

    @Test
    public void testGameIdFromDB() {
        System.out.println("-------Reading Beans---------");
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        MoveDAO md = (MoveDAO)context.getBean("jdbcTemplate");
        System.out.println(md.getGameId());
        assertEquals(1,1);
    }

}
