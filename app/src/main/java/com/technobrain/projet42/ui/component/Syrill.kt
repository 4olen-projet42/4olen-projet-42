/*
package com.technobrain.projet42.ui.component

import android.annotation.SuppressLint
import android.view.MotionEvent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.technobrain.projet42.R
import org.json.JSONObject
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Polyline
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

@SuppressLint("ClickableViewAccessibility")
@Composable
fun Syrill() {
AndroidView(
factory = { ctx ->
    MapView(ctx).apply {
        setTileSource(TileSourceFactory.MAPNIK)
        setMultiTouchControls(true)

        val mapController = controller
        mapController.setZoom(15.0)

        val geoPoints = ArrayList<GeoPoint>()

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
                    overlays.add(polyline)
                    if (geoPoints.isNotEmpty()) {
                        mapController.setCenter(geoPoints[0])
                    }
                }
                "Point" -> {
                    val coordinates = geometry.getJSONArray("coordinates")
                    val geoPoint = GeoPoint(coordinates.getDouble(1), coordinates.getDouble(0))
                    val marker = org.osmdroid.views.overlay.Marker(this).apply {
                        position = geoPoint
                        setAnchor(org.osmdroid.views.overlay.Marker.ANCHOR_CENTER, org.osmdroid.views.overlay.Marker.ANCHOR_BOTTOM)
                        icon = ctx.getDrawable(R.drawable.baseline_run_circle_24)
                    }
                    overlays.add(marker)
                    mapController.setCenter(geoPoint)
                }
                else -> {

                }
            }
        }

        val locationOverlay = MyLocationNewOverlay(this)
        locationOverlay.enableMyLocation()
        overlays.add(locationOverlay)

        var isDragging = false


        this.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (isDragging) {
                    isDragging = false
                }
            } else if (event.action == MotionEvent.ACTION_DOWN) {
            }
            false
        }
    }
},
modifier = Modifier
.fillMaxSize()
)
}*/
