package com.technobrain.projet42.ui.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
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
    val mockedCoordinates = listOf(
        GeoPoint(31.8, -5.0), // Paris, France
        GeoPoint(32.0, -5.0),
        GeoPoint(32.0, -4.7),
        GeoPoint(31.8, -4.7),
    )

    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { mapView },
        update = {
            it.setTileSource(TileSourceFactory.MAPNIK)
            it.setMultiTouchControls(true)

            val mapController = it.controller
            mapController.setZoom(15.0)

            val geoPoints = ArrayList<GeoPoint>()

            for (geoPoint in mockedCoordinates) {
                geoPoints.add(geoPoint)
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
    )
}



@Preview
@Composable
fun DefaultPreview() {
    OsmMap()
}