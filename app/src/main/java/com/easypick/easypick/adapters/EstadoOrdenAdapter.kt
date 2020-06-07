package com.easypick.easypick.adapters

import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.easypick.easypick.R
import com.easypick.easypick.model.OrderStatus
import com.easypick.easypick.model.OrderEvent
import com.github.vipulasri.timelineview.TimelineView
import kotlinx.android.synthetic.main.item_orden_status.view.*

class EstadoOrdenAdapter(private val mFeedList: List<OrderEvent>) :
    RecyclerView.Adapter<EstadoOrdenAdapter.TimeLineViewHolder>() {

    private lateinit var mLayoutInflater: LayoutInflater

    override fun getItemViewType(position: Int): Int {
        return TimelineView.getTimeLineViewType(position, itemCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeLineViewHolder {

        if (!::mLayoutInflater.isInitialized) {
            mLayoutInflater = LayoutInflater.from(parent.context)
        }

        return TimeLineViewHolder(
            mLayoutInflater.inflate(R.layout.item_orden_status, parent, false),
            viewType
        )

    }

    override fun onBindViewHolder(holder: TimeLineViewHolder, position: Int) {

        val timeLineModel = mFeedList[position]

        when (timeLineModel.status) {
            OrderStatus.INACTIVE -> {
                setMarker(holder, R.drawable.ic_marker_inactive, R.color.toolbarRed)
            }
            OrderStatus.ACTIVE -> {
                setMarker(holder, R.drawable.ic_marker_active, R.color.toolbarRed)
            }
            else -> {
                setMarker(holder, R.drawable.ic_marker, R.color.toolbarRed)
            }
        }

        if (timeLineModel.date.isNotEmpty()) {
            holder.date.visibility = View.VISIBLE
            holder.date.text = timeLineModel.date.toString()
        } else
            holder.date.visibility = View.GONE

        holder.message.text = timeLineModel.message

    }

    private fun setMarker(holder: TimeLineViewHolder, drawableResId: Int, colorFilter: Int) {
        val drawable = VectorDrawableCompat.create(holder.itemView.context.resources, drawableResId,
            holder.itemView.context.theme)
        drawable!!.setColorFilter(ContextCompat.getColor(holder.itemView.context, colorFilter), PorterDuff.Mode.SRC_IN)
        holder.timeline.marker = drawable
    }

    override fun getItemCount() = mFeedList.size

    inner class TimeLineViewHolder(itemView: View, viewType: Int) : RecyclerView.ViewHolder(itemView) {

        val date = itemView.fecha_actualizacion
        val message = itemView.mensaje_actualizacion
        val timeline = itemView.orden_timeline

        init {
            timeline.initLine(viewType)
        }
    }
}