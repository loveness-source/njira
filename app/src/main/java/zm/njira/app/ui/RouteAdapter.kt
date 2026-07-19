package zm.njira.app.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import zm.njira.app.data.Route
import zm.njira.app.data.TransportRepository
import zm.njira.app.databinding.ItemRouteBinding

class RouteAdapter(
    private val items: List<Route>,
    private val onClick: (Route) -> Unit
) : RecyclerView.Adapter<RouteAdapter.VH>() {

    inner class VH(val b: ItemRouteBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(ItemRouteBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(h: VH, position: Int) {
        val r = items[position]
        h.b.name.text = r.name
        h.b.meta.text = "Board at ${TransportRepository.stop(r.boardStopId).name} · ${r.walkText}"
        h.b.fare.text = "K${r.fare}"
        h.b.time.text = "~${r.minutes} min"
        h.b.badge.text = if (r.transfer) "1 transfer" else "1 minibus"
        h.b.root.setOnClickListener { onClick(r) }
    }
}
