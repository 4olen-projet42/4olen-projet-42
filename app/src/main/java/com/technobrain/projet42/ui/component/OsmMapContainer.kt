package com.technobrain.projet42.ui.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.technobrain.projet42.R
import org.json.JSONObject
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Polyline

@Composable
fun OsmMap(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val mapView = MapView(context)

    // Load the configuration
    DisposableEffect(Unit) {
        val config = Configuration.getInstance()
        config.load(context, context.getSharedPreferences("osmdroid", 0))
        onDispose {}
    }

    // Mock coordinates
    // Use API to get the GeoJson of the Event
    val geoJson = """
        {
          "type": "FeatureCollection",
          "features": [
            {
              "type": "Feature",
              "properties": {},
              "geometry": {
                "coordinates": [
                  [
                    2.3477743849163346,
                    48.85165601314378
                  ],
                  [
                    2.3508829308872237,
                    48.84756479262086
                  ],
                  [
                    2.3517009065518835,
                    48.846524050251304
                  ],
                  [
                    2.352436841377852,
                    48.84446062980035
                  ],
                  [
                    2.3523277071493283,
                    48.84388646051568
                  ],
                  [
                    2.350716772214696,
                    48.838553405775286
                  ],
                  [
                    2.3436890348217503,
                    48.84122265521805
                  ],
                  [
                    2.34064021056102,
                    48.84696654070078
                  ],
                  [
                    2.3426579503622236,
                    48.85031484191316
                  ],
                  [
                    2.3431819617853478,
                    48.85140204222495
                  ],
                  [
                    2.3454831089699155,
                    48.850847547967675
                  ]
                ],
                "type": "LineString"
              }
            },
            {
              "type": "Feature",
              "properties": {},
              "geometry": {
                "coordinates": [
                  2.3477194682318157,
                  48.85171080120671
                ],
                "type": "Point"
              }
            },
            {
              "type": "Feature",
              "properties": {},
              "geometry": {
                "coordinates": [
                  2.345503341293579,
                  48.85084410748823
                ],
                "type": "Point"
              }
            }
          ]
        }
    """.trimIndent()

    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { mapView },
        update = {
            it.setTileSource(TileSourceFactory.MAPNIK)
            it.setMultiTouchControls(true)

            val mapController = it.controller
            mapController.setZoom(16.0)

            val geoPoints = ArrayList<GeoPoint>()

            // Mock parsing of the GeoJson
            val features = JSONObject(geoJson).getJSONArray("features")
            for (i in 0 until features.length()) {
                val feature = features.getJSONObject(i)
                val geometry = feature.getJSONObject("geometry")
                val geometryType = geometry.getString("type")

                when (geometryType) {
                    "LineString" -> {
                        val coordinates = geometry.getJSONArray("coordinates")
                        for (j in 0 until coordinates.length()) {
                            val coord = coordinates.getJSONArray(j)
                            geoPoints.add(GeoPoint(coord.getDouble(1), coord.getDouble(0)))
                        }
                        val polyline = Polyline().apply {
                            setPoints(geoPoints)
                            outlinePaint.color = android.graphics.Color.RED
                            outlinePaint.strokeWidth = 5f
                        }
                        it.overlays.add(polyline)
                        if (geoPoints.isNotEmpty()) {
                            mapController.setCenter(geoPoints[0])
                        }
                    }
                    "Point" -> {
                        val coordinates = geometry.getJSONArray("coordinates")
                        val geoPoint = GeoPoint(coordinates.getDouble(1), coordinates.getDouble(0))
                        val marker = org.osmdroid.views.overlay.Marker(it).apply {
                            position = geoPoint
                            setAnchor(org.osmdroid.views.overlay.Marker.ANCHOR_CENTER, org.osmdroid.views.overlay.Marker.ANCHOR_BOTTOM)
                            icon = context.getDrawable(R.drawable.baseline_location_pin_24)
                        }
                        it.overlays.add(marker)
                        mapController.setCenter(geoPoint)
                    }
                    else -> {

                    }
                }
            }
        }
    )
}


@Preview
@Composable
fun DefaultPreview() {
    OsmMap()
}