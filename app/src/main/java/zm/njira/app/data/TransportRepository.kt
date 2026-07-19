package zm.njira.app.data

import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.random.Random

/**
 * Single source of truth for transport data. For the prototype it serves
 * built-in Lusaka data so the app runs without a backend; a production build
 * would delegate to [NjiraApi].
 */
object TransportRepository {

    val stops = listOf(
        Stop("cairo", "Cairo Road (Town)", -15.4177, 28.2830, listOf("Kabwata", "Chilenje", "Matero")),
        Stop("kulima", "Kulima Tower", -15.4205, 28.2870, listOf("Chelstone", "Matero", "UNZA")),
        Stop("north", "Northmead", -15.4050, 28.3120, listOf("Chelstone", "UNZA")),
        Stop("kabwata", "Kabwata", -15.4380, 28.2980, listOf("Town", "Chilenje"))
    )

    val routes = listOf(
        Route("r1", "Chelstone", "Via Great East Road", "kulima", "150 m walk", 12, 25, 6, false),
        Route("r2", "Chelstone", "Via Chelston Road", "cairo", "2 min walk", 15, 30, 8, true),
        Route("r3", "Chelstone", "Via Ring Road", "kulima", "150 m walk", 13, 28, 7, false),
        Route("r4", "Matero", "Via Los Angeles Road", "kulima", "150 m walk", 10, 20, 5, false),
        Route("r5", "Kabwata", "Via Burma Road", "cairo", "3 min walk", 8, 15, 4, false),
        Route("r6", "Chilenje", "Via Kafue Road", "cairo", "3 min walk", 10, 18, 5, false),
        Route("r7", "UNZA", "Via Great East Road", "kulima", "150 m walk", 11, 20, 5, false)
    )

    val destinations: List<String> = routes.map { it.dest }.distinct()

    fun stop(id: String): Stop = stops.first { it.id == id }

    fun route(id: String): Route = routes.first { it.id == id }

    /** Nearby stops sorted by real distance from the given location. */
    fun nearbyStops(from: LatLng): List<Stop> =
        stops.map { it.copy(distanceMeters = haversine(from, LatLng(it.lat, it.lng)).roundToInt()) }
            .sortedBy { it.distanceMeters }

    fun journeyOptions(dest: String): List<Route> = routes.filter { it.dest == dest }

    /** Simulated live minibuses for a route. */
    fun liveMinibuses(routeId: String): List<Minibus> = listOf(
        Minibus("$routeId-a", "Minibus 1", 3 + Random.nextInt(3), 4 + Random.nextInt(8), 15 + Random.nextInt(10)),
        Minibus("$routeId-b", "Minibus 2", 9 + Random.nextInt(5), if (Random.nextDouble() < 0.4) 0 else 2 + Random.nextInt(6), 45 + Random.nextInt(15)),
        Minibus("$routeId-c", "Minibus 3", 16 + Random.nextInt(6), 5 + Random.nextInt(8), 72 + Random.nextInt(12))
    )

    private fun haversine(a: LatLng, b: LatLng): Double {
        val r = 6371000.0
        val dLat = Math.toRadians(b.lat - a.lat)
        val dLng = Math.toRadians(b.lng - a.lng)
        val h = sin(dLat / 2).pow(2) + cos(Math.toRadians(a.lat)) * cos(Math.toRadians(b.lat)) * sin(dLng / 2).pow(2)
        return 2 * r * asin(sqrt(h))
    }
}
