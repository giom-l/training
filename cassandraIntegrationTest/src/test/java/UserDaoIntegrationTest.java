import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import org.apache.thrift.transport.TTransportException;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static junit.framework.TestCase.assertEquals;


public class UserDaoIntegrationTest {

    private Session session;
    private Cluster cluster;

    @Before
    public void setUp() throws IOException, TTransportException {
        EmbeddedCassandraServerHelper.startEmbeddedCassandra("another-cassandra.yaml");

        cluster = Cluster.builder()
                .addContactPoints("localhost")
                .withPort(9142)
                .build();

        session = cluster.connect();

        session.execute("create KEYSPACE test WITH replication = {'class': 'SimpleStrategy' , 'replication_factor': '1' }");
        session.execute("CREATE TABLE test.user (lastName text, firstName text, PRIMARY KEY (lastName))");
    }

    @After
    public void teardown() {
        session.close();
        EmbeddedCassandraServerHelper.cleanEmbeddedCassandra();
    }

//    @Test
//    public void execute_should_run_properly(){
//       session.execute("create KEYSPACE test WITH replication = {'class': 'SimpleStrategy' , 'replication_factor': '1' }");
//    }


    @Test
    public void user_should_be_returned() {
        session.execute("insert into test.user(lastName, firstName) values('lastName1', 'firstName1')");

        UserDao cassandraJob = new UserDao("localhost", 9142);

        List<User> users = cassandraJob.getUsers();

        System.out.println(users.get(0).getFirstName() + " " + users.get(0).getLastName());
        assertEquals(users.size(), 1);
        assertEquals(users.get(0).getFirstName(), "firstName1");
        assertEquals(users.get(0).getLastName(), "lastName1");
    }



}
