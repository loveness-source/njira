package zm.njira.app.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import zm.njira.app.data.Minibus
import zm.njira.app.databinding.ItemMinibusBinding

class MinibusAdapter(
    private val items: List<Minibus>
) : RecyclerView.Adapter<MinibusAdapter.VH>() {

    inner class VH(val b: ItemMinibusBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(ItemMinibusBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(h: VH, position: Int) {
        val m = items[position]
        h.b.label.text = m.label
        h.b.sub.text = "${m.etaMinutes} min away · ${m.progress}% along route"
        h.b.seats.text = if (m.seats == 0) "Full" else "${m.seats} seats"
        h.b.seats.setTextColor(if (m.seats == 0) 0xFFB91C1C.toInt() else 0xFF12805C.toInt())
        h.b.progress.progress = m.progress
    }
}
