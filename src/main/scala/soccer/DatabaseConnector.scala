package soccer

import com.datastax.driver.core.{HostDistance, PoolingOptions, SocketOptions}
import com.outworkers.phantom.connectors.CassandraConnection
import com.outworkers.phantom.database.{Database, DatabaseProvider}
import com.outworkers.phantom.dsl.CassandraConnection

object DatabaseConnector {
  import com.outworkers.phantom.dsl._

  val default: CassandraConnection = ContactPoint.local
    .withClusterBuilder(_.withSocketOptions(
      new SocketOptions()
        .setConnectTimeoutMillis(20000)
        .setReadTimeoutMillis(20000)
    )
      .withPoolingOptions(
        new PoolingOptions()
          .setMaxConnectionsPerHost(HostDistance.LOCAL, 50)
          .setMaxRequestsPerConnection(HostDistance.LOCAL,1000)
          .setMaxQueueSize(1500)
      )
     //
    ).noHeartbeat().keySpace(
    KeySpace("foot").ifNotExists().`with`(
      replication eqs SimpleStrategy.replication_factor(1)
    )
  )
  default.session.execute("CREATE TABLE IF NOT EXISTS foot.TmaxElements (id UUID PRIMARY KEY, elapsed VARCHAR, event_incident_typefk VARCHAR, match_id VARCHAR, player1 VARCHAR, subtype VARCHAR, team VARCHAR, type1 VARCHAR );")

}
class TestDatabase(
                    override val connector: CassandraConnection
                  ) extends Database[TestDatabase](connector) {
  object TmaxConnector extends TmaxElements with Connector
}

object TestDatabase extends TestDatabase(DatabaseConnector.default)

trait TestDbProvider extends DatabaseProvider[TestDatabase] {
  override val database = TestDatabase
}
