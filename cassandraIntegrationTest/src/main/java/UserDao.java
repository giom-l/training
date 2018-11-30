import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

import java.util.List;
import java.util.stream.Collectors;

public class UserDao {

    private Cluster cluster;
    private Session session;

    public UserDao(String localhost, int port) {
        cluster = Cluster.builder()
                .addContactPoints(localhost)
                .withPort(port)
                .build();

        session = cluster.connect();
    }

    public List<User> getUsers() {
        ResultSet execute = session.execute("select * from test.user");

        List<Row> res = execute.all();

        return res.stream().map(r -> new User(r.getString("lastName"), r.getString("firstName"))).collect(Collectors.toList());
    }
}


