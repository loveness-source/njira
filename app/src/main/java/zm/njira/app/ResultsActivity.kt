package zm.njira.app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import zm.njira.app.data.TransportRepository
import zm.njira.app.databinding.ActivityResultsBinding
import zm.njira.app.ui.RouteAdapter

class ResultsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dest = intent.getStringExtra("dest") ?: "Destination"
        binding.toolbar.title = "To $dest"
        binding.toolbar.setNavigationOnClickListener { finish() }
        binding.toLabel.text = dest

        val options = TransportRepository.journeyOptions(dest)
        binding.count.text = "${options.size} route option" + if (options.size > 1) "s" else ""
        binding.options.layoutManager = LinearLayoutManager(this)
        binding.options.adapter = RouteAdapter(options) { route ->
            startActivity(Intent(this, DetailsActivity::class.java).putExtra("routeId", route.id))
        }
    }
}
