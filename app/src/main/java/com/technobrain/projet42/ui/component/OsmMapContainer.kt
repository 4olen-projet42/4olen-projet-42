package com.technobrain.projet42.ui.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
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


    val mockedCoordinates = listOf(
        GeoPoint(31.8, -5.0), // Paris, France
        GeoPoint(32.0, -5.0),
        GeoPoint(32.0, -4.7),
        GeoPoint(31.8, -4.7),
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