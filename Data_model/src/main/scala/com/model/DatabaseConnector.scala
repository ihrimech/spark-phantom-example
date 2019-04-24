package com.model

import com.datastax.driver.core.{HostDistance, PoolingOptions, SocketOptions}
import com.model.TmaxElements
import com.outworkers.phantom.connectors.CassandraConnection
import com.outworkers.phantom.database.{Database, DatabaseProvider}
import com.outworkers.phantom.dsl.CassandraConnection

object DatabaseConnector {

  import com.outworkers.phantom.dsl._

  val poolingOptions = new PoolingOptions()
    .setConnectionsPerHost(HostDistance.LOCAL, 1, 200)
    .setMaxRequestsPerConnection(HostDistance.LOCAL, 256)
    .setNewConnectionThreshold(HostDistance.LOCAL, 100).setCoreConnectionsPerHost(HostDistance.LOCAL, 200)

  val default = ContactPoint.local
    .noHeartbeat()
    .withClusterBuilder(_.withoutJMXReporting()
      .withoutMetrics().withPoolingOptions(poolingOptions)).keySpace(
    KeySpace("foot").ifNotExists().`with`(
      replication eqs SimpleStrategy.replication_factor(1)
    ))

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
