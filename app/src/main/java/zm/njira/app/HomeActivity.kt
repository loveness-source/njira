package zm.njira.app

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import zm.njira.app.data.LatLng
import zm.njira.app.data.LocationProvider
import zm.njira.app.data.TransportRepository
import zm.njira.app.databinding.ActivityHomeBinding
import zm.njira.app.ui.StopAdapter

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var location: LocationProvider
    private val fallback = LatLng(-15.4177, 28.2830) // Town (Cairo Road)

    private val permission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            loadNearby(granted)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        location = LocationProvider(this)
        binding.nearby.layoutManager = LinearLayoutManager(this)

        // Popular-destination buttons
        TransportRepository.destinations.forEach { dest ->
            val btn = Button(this).apply {
                text = dest
                setOnClickListener { openResults(dest) }
            }
            binding.destContainer.addView(btn)
        }

        binding.locate.setOnClickListener { requestLocation() }
        requestLocation()
    }

    private fun requestLocation() {
        val granted = ContextCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        if (granted) loadNearby(true) else permission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun loadNearby(granted: Boolean) {
        lifecycleScope.launch {
            val loc = if (granted) {
                runCatching { location.currentLocation() }.getOrNull() ?: fallback
            } else fallback
            binding.gpsStatus.text =
                if (loc == fallback) getString(R.string.gps_default) else getString(R.string.gps_using)
            val near = TransportRepository.nearbyStops(loc).take(3)
            binding.nearby.adapter = StopAdapter(near) { stop -> openResults(stop.routes.first()) }
        }
    }

    private fun openResults(dest: String) {
        startActivity(Intent(this, ResultsActivity::class.java).putExtra("dest", dest))
    }
}
