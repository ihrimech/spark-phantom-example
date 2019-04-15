package soccer

import com.datastax.driver.core.SocketOptions
import com.outworkers.phantom.connectors.CassandraConnection
import com.outworkers.phantom.database.{Database, DatabaseProvider}

object DatabaseConnector {
  import com.outworkers.phantom.dsl._

  val default: CassandraConnection = ContactPoint.local
    .withClusterBuilder(_.withSocketOptions(
      new SocketOptions()
        .setConnectTimeoutMillis(20000)
        .setReadTimeoutMillis(20000)
    )).noHeartbeat().keySpace(
    KeySpace("foot").ifNotExists().`with`(
      replication eqs SimpleStrategy.replication_factor(1)
    )
  )
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
