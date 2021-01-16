package pl.client.tests;

import org.junit.Test;
import pl.server.Move;
import pl.server.MoveDAO;
import static org.junit.Assert.assertTrue;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DBTest {

    @Test
    public void testMoveToDB() {
        System.out.println("-------Reading Beans---------");
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        Move m = new Move(1,1,0,2);
        System.out.println("-------Creating MoveDAO---------");
        MoveDAO md = (MoveDAO)context.getBean("jdbcTemplate");
        System.out.println("------Records Creation--------" );
        md.saveMove(m);
        assertTrue(true);
    }
}
