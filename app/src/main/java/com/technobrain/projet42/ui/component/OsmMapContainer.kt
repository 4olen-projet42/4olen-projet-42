package com.technobrain.projet42.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    parcoursJSON: String,
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

    // geoJson from the API
    val geoJson = parcoursJSON

    AndroidView(
        modifier = modifier.fillMaxWidth().height(350.dp),
        factory = { mapView },
        update = {
            it.setTileSource(TileSourceFactory.MAPNIK)
            it.setMultiTouchControls(true)

            val mapController = it.controller
            mapController.setZoom(16.0)

            val geoPoints = ArrayList<GeoPoint>()

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
    OsmMap(parcoursJSON = "")
}