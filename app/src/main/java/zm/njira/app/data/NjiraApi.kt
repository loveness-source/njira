package zm.njira.app.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * REST interface for a live Njira backend (Retrofit). The prototype serves
 * data locally from [TransportRepository]; switching to the network is a
 * matter of calling these endpoints instead of the in-memory lists.
 */
interface NjiraApi {

    @GET("stops/nearby")
    suspend fun nearbyStops(
        @Query("lat") lat: Double,
        @Query("lng") lng: Double,
        @Query("radius") radiusMeters: Int = 1500
    ): List<Stop>

    @GET("routes/{routeId}/minibuses")
    suspend fun liveMinibuses(@Path("routeId") routeId: String): List<Minibus>

    companion object {
        fun create(baseUrl: String = "https://api.njira.zm/v1/"): NjiraApi =
            Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NjiraApi::class.java)
    }
}
