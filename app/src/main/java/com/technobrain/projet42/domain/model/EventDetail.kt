package com.technobrain.projet42.domain.model

/* the full event object looks like this:
{
  "id": 0,
  "nom": "string",
  "image": "string",
  "maxParticipants": 0,
  "dateDebut": "2024-07-06",
  "ville": "string",
  "distance": 0,
  "parcoursJSON": "string",
  "denivele": 0,
  "heure": "string",
  "sports": [
    {
      "id": 0,
      "nom": "string"
    }
  ]
}
*/


data class EventDetail(
    val id: String,
    val nom: String,
    val image: String,
    val maxParticipants: Int,
    val date: String,
    val location: String,
    val distance: Int,
    val parcoursJSON: String,
    val denivele: Int,
    val heure: String,
)