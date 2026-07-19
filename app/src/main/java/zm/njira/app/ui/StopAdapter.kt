package zm.njira.app.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import zm.njira.app.data.Stop
import zm.njira.app.databinding.ItemStopBinding

class StopAdapter(
    private val items: List<Stop>,
    private val onClick: (Stop) -> Unit
) : RecyclerView.Adapter<StopAdapter.VH>() {

    inner class VH(val b: ItemStopBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(ItemStopBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(h: VH, position: Int) {
        val s = items[position]
        h.b.name.text = s.name
        h.b.routes.text = s.routes.joinToString(" · ")
        h.b.distance.text =
            if (s.distanceMeters < 1000) "${s.distanceMeters} m"
            else String.format("%.1f km", s.distanceMeters / 1000.0)
        h.b.root.setOnClickListener { onClick(s) }
    }
}
