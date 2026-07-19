package zm.njira.app.data

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.tasks.await

/**
 * Wraps the fused location provider to expose the current position as a
 * suspending function, so callers can await it without blocking the UI.
 */
class LocationProvider(context: Context) {

    private val client = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission") // caller must hold ACCESS_FINE_LOCATION
    suspend fun currentLocation(): LatLng? {
        val loc = client.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null).await()
        return loc?.let { LatLng(it.latitude, it.longitude) }
    }
}
