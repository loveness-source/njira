package zm.njira.app

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import zm.njira.app.data.TransportRepository
import zm.njira.app.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val routeId = intent.getStringExtra("routeId") ?: return finish()
        val r = TransportRepository.route(routeId)
        binding.toolbar.setNavigationOnClickListener { finish() }

        binding.summary.text = "${r.minutes + 7} min   ·   K${r.fare}   ·   " +
            (if (r.transfer) "1 transfer" else "1 minibus") + " · ${r.stops} stops"

        val steps = listOf(
            "Walk to ${TransportRepository.stop(r.boardStopId).name}" to r.walkText,
            "Minibus: Town – ${r.dest}" to "Board and pay K${r.fare} · ~${r.minutes} min · ${r.stops} stops",
            "Walk to your destination" to "4 min",
            "Arrive: ${r.dest}" to "Estimated arrival"
        )
        steps.forEach { (title, sub) -> binding.steps.addView(stepView(title, sub)) }

        binding.trackBtn.setOnClickListener {
            startActivity(Intent(this, LiveActivity::class.java).putExtra("routeId", r.id))
        }
    }

    private fun stepView(title: String, sub: String): LinearLayout {
        val pad = (12 * resources.displayMetrics.density).toInt()
        return LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(0, pad, 0, pad)
            addView(TextView(this@DetailsActivity).apply {
                text = title
                setTypeface(typeface, Typeface.BOLD)
                textSize = 15f
                setTextColor(ContextCompat.getColor(context, R.color.ink))
            })
            addView(TextView(this@DetailsActivity).apply {
                text = sub
                textSize = 13f
                setTextColor(ContextCompat.getColor(context, R.color.muted))
            })
        }
    }
}
