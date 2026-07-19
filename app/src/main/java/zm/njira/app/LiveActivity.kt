package zm.njira.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.random.Random
import zm.njira.app.data.TransportRepository
import zm.njira.app.databinding.ActivityLiveBinding
import zm.njira.app.ui.MinibusAdapter

class LiveActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLiveBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLiveBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val routeId = intent.getStringExtra("routeId") ?: return finish()
        val r = TransportRepository.route(routeId)
        binding.toolbar.title = "Town – ${r.dest}"
        binding.toolbar.setNavigationOnClickListener { finish() }
        binding.fare.text = "K${r.fare}"

        val buses = TransportRepository.liveMinibuses(routeId).toMutableList()
        val adapter = MinibusAdapter(buses)
        binding.live.layoutManager = LinearLayoutManager(this)
        binding.live.adapter = adapter

        // Simulate live updates every 1.5 seconds.
        lifecycleScope.launch {
            while (isActive) {
                delay(1500)
                buses.forEach { b ->
                    if (b.etaMinutes > 1) b.etaMinutes -= 1
                    b.progress = minOf(98, b.progress + 5)
                    if (Random.nextDouble() < 0.2 && b.seats > 0) b.seats -= 1
                }
                adapter.notifyDataSetChanged()
                binding.updated.text = "updated just now"
            }
        }
    }
}
