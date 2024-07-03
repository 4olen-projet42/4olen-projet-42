package com.technobrain.projet42.ui.component

import android.os.Bundle
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.events.DelayedMapListener
import org.osmdroid.events.MapListener
import org.osmdroid.events.ScrollEvent
import org.osmdroid.events.ZoomEvent
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Polyline
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

@Composable
fun OsmMap(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val mapView = MapView(context)

    val mockedCoordinates = listOf(
        GeoPoint(48.8567, 2.2943), // Paris, France
        GeoPoint(48.8632, 2.3103),
        GeoPoint(48.8695, 2.3263),
        GeoPoint(48.8758, 2.3423),
        GeoPoint(48.8821, 2.3583)
    )

    AndroidView(modifier = modifier.fillMaxSize(), factory = { mapView }) { mapView ->
        mapView.setTileSource(TileSourceFactory.MAPNIK)
        mapView.controller.setZoom(12.0)
        mapView.controller.setCenter(mockedCoordinates[0])

        val polyline = Polyline()
        polyline.setPoints(mockedCoordinates)
        mapView.overlays.add(polyline)
    }
}

@Preview
@Composable
fun DefaultPreview() {
    OsmMap()
}