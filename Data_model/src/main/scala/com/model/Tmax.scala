
package com.model
import java.util.UUID

import com.outworkers.phantom.keys.PartitionKey
import com.outworkers.phantom.{ResultSet, Table}

import scala.concurrent.Future


case class Tmax(id: UUID, elapsed: String, event_incident_typefk: String, match_id: String, player1: String, subtype: String, team: String, type1: String)


abstract class TmaxElements extends Table[TmaxElements, Tmax] {

  object id extends UUIDColumn with PartitionKey

  object elapsed extends StringColumn

  object event_incident_typefk extends StringColumn

  object match_id extends StringColumn

  object player1 extends StringColumn

  object subtype extends StringColumn

  object team extends StringColumn

  object type1 extends StringColumn

  def myStore(tmax: Tmax): Future[ResultSet] = {
    import com.outworkers.phantom.dsl._
    insert
      .value(_.id, tmax.id)
      .value(_.elapsed, tmax.elapsed)
      .value(_.event_incident_typefk, tmax.event_incident_typefk)
      .value(_.match_id, tmax.match_id)
      .value(_.player1, tmax.player1)
      .value(_.subtype, tmax.subtype)
      .value(_.team, tmax.team)
      .value(_.type1, tmax.type1)
      .future()
  }
}