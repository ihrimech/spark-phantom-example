package com.model

import java.util.UUID
import com.outworkers.phantom.keys.PartitionKey
import com.outworkers.phantom.{ResultSet, Table}

import scala.concurrent.Future


case class ValeursFoncieres(id: UUID, id_mutation: String, date_mutation: String, numero_disposition: String, valeur_fonciere: String, adresse_numero: String,
                            adresse_suffixe: String, adresse_code_voie: String, adresse_nom_voie: String, code_postal: String, code_commune: String, nom_commune: String,
                            ancien_code_commune: String,
                            ancien_nom_commune: String, code_departement: String, id_parcelle: String, ancien_id_parcelle: String, numero_volume: String,
                            lot_1_numero: String, lot_1_surface_carrez: String, lot_2_numero: String,
                            lot_2_surface_carrez: String, lot_3_numero: String, lot_3_surface_carrez: String, lot_4_numero: String, lot_4_surface_carrez: String,
                            lot_5_numero: String, lot_5_surface_carrez: String,
                            nombre_lots: String, code_type_local: String, type_local: String, surface_reelle_bati: String, nombre_pieces_principales: String, code_nature_culture: String,
                            nature_culture: String, code_nature_culture_speciale: String,
                            nature_culture_speciale: String, surface_terrain: String, longitude: String, latitude: String
                           )

abstract class ValeursFoncieresElem extends Table[ValeursFoncieresElem, ValeursFoncieres] {

  object id extends UUIDColumn with PartitionKey

  object id_mutation extends StringColumn

  object date_mutation extends StringColumn

  object numero_disposition extends StringColumn

  object valeur_fonciere extends StringColumn

  object adresse_numero extends StringColumn

  object adresse_suffixe extends StringColumn

  object adresse_code_voie extends StringColumn

  object adresse_nom_voie extends StringColumn

  object code_postal extends StringColumn

  object code_commune extends StringColumn

  object nom_commune extends StringColumn

  object ancien_code_commune extends StringColumn

  object ancien_nom_commune extends StringColumn

  object code_departement extends StringColumn

  object id_parcelle extends StringColumn

  object ancien_id_parcelle extends StringColumn

  object numero_volume extends StringColumn

  object lot_1_numero extends StringColumn

  object lot_1_surface_carrez extends StringColumn

  object lot_2_numero extends StringColumn

  object lot_2_surface_carrez extends StringColumn

  object lot_3_numero extends StringColumn

  object lot_3_surface_carrez extends StringColumn

  object lot_4_numero extends StringColumn

  object lot_4_surface_carrez extends StringColumn

  object lot_5_numero extends StringColumn

  object lot_5_surface_carrez extends StringColumn

  object nombre_lots extends StringColumn

  object code_type_local extends StringColumn

  object type_local extends StringColumn

  object surface_reelle_bati extends StringColumn

  object nombre_pieces_principales extends StringColumn

  object code_nature_culture extends StringColumn

  object nature_culture extends StringColumn

  object code_nature_culture_speciale extends StringColumn

  object nature_culture_speciale extends StringColumn

  object surface_terrain extends StringColumn

  object longitude extends StringColumn

  object latitude extends StringColumn

  def myStore(foncieres: ValeursFoncieres): Future[ResultSet] = {
    import com.outworkers.phantom.dsl._
    insert
      .value(_.id, foncieres.id)
      .value(_.id_mutation, foncieres.id_mutation)
      .value(_.date_mutation, foncieres.date_mutation)
      .value(_.numero_disposition, foncieres.numero_disposition)
      .value(_.valeur_fonciere, foncieres.valeur_fonciere)
      .value(_.adresse_numero, foncieres.adresse_numero)
      .value(_.adresse_suffixe, foncieres.adresse_suffixe)
      .value(_.adresse_code_voie, foncieres.adresse_code_voie)
      .value(_.adresse_nom_voie, foncieres.adresse_nom_voie)
      .value(_.code_postal, foncieres.code_postal)
      .value(_.code_commune, foncieres.code_commune)
      .value(_.nom_commune, foncieres.nom_commune)
      .value(_.ancien_code_commune, foncieres.ancien_code_commune)
      .value(_.ancien_nom_commune, foncieres.ancien_nom_commune)
      .value(_.code_departement, foncieres.code_departement)
      .value(_.id_parcelle, foncieres.id_parcelle)
      .value(_.ancien_id_parcelle, foncieres.ancien_id_parcelle)
      .value(_.numero_volume, foncieres.numero_volume)
      .value(_.lot_1_numero, foncieres.lot_1_numero)
      .value(_.lot_1_surface_carrez, foncieres.lot_1_surface_carrez)
      .value(_.lot_2_numero, foncieres.lot_2_numero)
      .value(_.lot_2_surface_carrez, foncieres.lot_2_surface_carrez)
      .value(_.lot_3_numero, foncieres.lot_3_numero)
      .value(_.lot_3_surface_carrez, foncieres.lot_3_surface_carrez)
      .value(_.lot_4_numero, foncieres.lot_4_numero)
      .value(_.lot_4_surface_carrez, foncieres.lot_4_surface_carrez)
      .value(_.lot_5_numero, foncieres.lot_5_numero)
      .value(_.lot_5_surface_carrez, foncieres.lot_5_surface_carrez)
      .value(_.nombre_lots, foncieres.nombre_lots)
      .value(_.code_type_local, foncieres.code_type_local)
      .value(_.type_local, foncieres.type_local)
      .value(_.surface_reelle_bati, foncieres.surface_reelle_bati)
      .value(_.nombre_pieces_principales, foncieres.nombre_pieces_principales)
      .value(_.code_nature_culture, foncieres.code_nature_culture)
      .value(_.nature_culture, foncieres.nature_culture)
      .value(_.code_nature_culture_speciale, foncieres.code_nature_culture_speciale)
      .value(_.nature_culture_speciale, foncieres.nature_culture_speciale)
      .value(_.surface_terrain, foncieres.surface_terrain)
      .value(_.longitude, foncieres.longitude)
      .value(_.latitude, foncieres.latitude)
      .future()
  }


}
