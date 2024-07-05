package com.technobrain.projet42.ui.component

import android.os.AsyncTask
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
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
    val mapView = remember { MapView(context) }
    var routePoints by remember { mutableStateOf<List<GeoPoint>>(emptyList()) }

    DisposableEffect(Unit) {
        val config = Configuration.getInstance()
        config.load(context, context.getSharedPreferences("osmdroid", 0))
        onDispose {}
    }

    LaunchedEffect(Unit) {
        fetchRouteData { points ->
            routePoints = points
        }
    }

    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { mapView },
        update = {
            it.setTileSource(TileSourceFactory.MAPNIK)
            it.setMultiTouchControls(true)

            val mapController = it.controller
            mapController.setZoom(16.0)

            if (routePoints.isNotEmpty()) {
                val polyline = Polyline().apply {
                    setPoints(routePoints)
                    outlinePaint.color = android.graphics.Color.RED
                    outlinePaint.strokeWidth = 10f
                }
                it.overlays.add(polyline)
                mapController.setCenter(routePoints[0])
            }
        }
    )
}

private fun fetchRouteData(callback: (List<GeoPoint>) -> Unit) {
    val coordinates = listOf(
        GeoPoint(48.872, 2.3016),  // Champs-Élysées
        /*GeoPoint(48.8738, 2.2950), // Arc de Triomphe
        GeoPoint(48.8284, 2.4353), // Bois de Vincennes
        GeoPoint(48.853, 2.369),   // Place de la Bastille
        GeoPoint(48.8530, 2.3499), // Notre-Dame Cathedral
        GeoPoint(48.8584, 2.2945), // Eiffel Tower
        GeoPoint(48.863, 2.256),   // Bois de Boulogne*/
        GeoPoint(48.8723, 2.2857)  // Avenue Foch
    )

    val apiKey = "5b3ce3597851110001cf6248a4400ded6fe945d6947188f7575a18d7"
    val start = coordinates.first()
    val end = coordinates.last()
    val viaPoints = coordinates.drop(1).dropLast(1)

    val viaPointsString = viaPoints.joinToString("|") { "${it.longitude},${it.latitude}" }
    val url = "https://api.openrouteservice.org/v2/directions/foot-walking?api_key=$apiKey&start=${start.longitude},${start.latitude}&end=${end.longitude},${end.latitude}&via=$viaPointsString"

    AsyncTask.execute {
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()
        val response = client.newCall(request).execute()
        val body = response.body?.string()
        if (body != null) {
            val routeResponse = Gson().fromJson(body, RouteResponse::class.java)
            val routePoints = routeResponse.features[0].geometry.coordinates.map {
                GeoPoint(it[1], it[0])
            }
            callback(routePoints)
        }
    }
}

data class RouteResponse(
    val features: List<Feature>
)

data class Feature(
    val geometry: Geometry
)

data class Geometry(
    val coordinates: List<List<Double>>
)

@Preview
@Composable
fun DefaultPreview() {
    OsmMap()
}