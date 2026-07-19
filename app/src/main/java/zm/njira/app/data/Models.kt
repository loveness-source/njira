package zm.njira.app.data

/** Simple latitude/longitude pair. */
data class LatLng(val lat: Double, val lng: Double)

/** A boarding point (minibus stop). */
data class Stop(
    val id: String,
    val name: String,
    val lat: Double,
    val lng: Double,
    val routes: List<String>,
    var distanceMeters: Int = 0
)

/** A minibus route option between the user and a destination. */
data class Route(
    val id: String,
    val dest: String,
    val name: String,
    val boardStopId: String,
    val walkText: String,
    val fare: Int,
    val minutes: Int,
    val stops: Int,
    val transfer: Boolean
)

/** A live minibus running on a route. */
data class Minibus(
    val id: String,
    val label: String,
    var etaMinutes: Int,
    var seats: Int,
    var progress: Int // 0..100 along the route
)
