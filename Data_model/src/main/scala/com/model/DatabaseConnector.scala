package com.model

import com.datastax.driver.core.{HostDistance, PoolingOptions, SocketOptions}
import com.outworkers.phantom.database.{Database, DatabaseProvider}
import com.outworkers.phantom.dsl.CassandraConnection

object DatabaseConnector {

  import com.outworkers.phantom.dsl._

  val poolingOptions = new PoolingOptions()
    .setConnectionsPerHost(HostDistance.LOCAL, 1, 200)
    .setMaxRequestsPerConnection(HostDistance.LOCAL, 256)
    .setNewConnectionThreshold(HostDistance.LOCAL, 100)
    .setCoreConnectionsPerHost(HostDistance.LOCAL, 200)


  val default = ContactPoint.local
    .noHeartbeat()
    .withClusterBuilder(_.withoutJMXReporting()
      .withoutMetrics()
      .withPoolingOptions(poolingOptions)
    ).keySpace(
    KeySpace("immobilier").ifNotExists().`with`(
      replication eqs SimpleStrategy.replication_factor(1)
    ))

  default.session.execute("CREATE TABLE IF NOT EXISTS immobilier.valeursfonciereselem (" +
    "id UUID PRIMARY KEY" +
    ", id_mutation VARCHAR" +
    ", date_mutation VARCHAR, " +
    "numero_disposition VARCHAR" +
    ", valeur_fonciere VARCHAR" +
    ", adresse_numero VARCHAR" +
    ", adresse_suffixe VARCHAR," +
    " adresse_code_voie VARCHAR " +
    ",adresse_nom_voie VARCHAR" +
    ",code_postal VARCHAR" +
    ",code_commune VARCHAR" +
    ",nom_commune VARCHAR" +
    ",ancien_code_commune VARCHAR" +
    ",ancien_nom_commune VARCHAR" +
    ",code_departement VARCHAR" +
    ",id_parcelle VARCHAR" +
    ",ancien_id_parcelle VARCHAR" +
    ",numero_volume VARCHAR" +
    ",lot_1_numero VARCHAR" +
    ",lot_1_surface_carrez VARCHAR" +
    ",lot_2_numero VARCHAR" +
    ",lot_2_surface_carrez VARCHAR" +
    ",lot_3_numero VARCHAR" +
    ",lot_3_surface_carrez VARCHAR" +
    ",lot_4_numero VARCHAR" +
    ",lot_4_surface_carrez VARCHAR" +
    ",lot_5_numero VARCHAR" +
    ",lot_5_surface_carrez VARCHAR" +
    ",nombre_lots VARCHAR" +
    ",code_type_local VARCHAR" +
    ",type_local VARCHAR" +
    ",surface_reelle_bati VARCHAR" +
    ",nombre_pieces_principales VARCHAR" +
    ",code_nature_culture VARCHAR" +
    ",nature_culture VARCHAR" +
    ",code_nature_culture_speciale VARCHAR" +
    ",nature_culture_speciale VARCHAR" +
    ",surface_terrain VARCHAR" +
    ",longitude VARCHAR" +
    ",latitude VARCHAR);")

  val res = default.session.execute("select * from immobilier.valeursfonciereselem;")
}

class TestDatabase(
                    override val connector: CassandraConnection
                  ) extends Database[TestDatabase](connector) {

  object TmaxConnector extends ValeursFoncieresElem with Connector

}

object TestDatabase extends TestDatabase(DatabaseConnector.default)

trait TestDbProvider extends DatabaseProvider[TestDatabase] {
  override val database = TestDatabase
}
