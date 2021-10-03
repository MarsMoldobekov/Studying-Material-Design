package com.example.studyingmaterialdesign.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.studyingmaterialdesign.R
import com.example.studyingmaterialdesign.databinding.RecyclerViewRowItemBinding
import com.example.studyingmaterialdesign.domain.data.FLRResponse

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    var flrResponseData: List<FLRResponse>? = null
        set(value) {
            val diffResult = DiffUtil.calculateDiff(DiffUtilCallback(this.flrResponseData, value))
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_view_row_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        flrResponseData?.get(position)?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int = flrResponseData?.size ?: 0

    fun removeData() {
        flrResponseData = null
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding: RecyclerViewRowItemBinding by lazy {
            RecyclerViewRowItemBinding.bind(itemView)
        }

        fun bind(flrResponse: FLRResponse) {
            with(binding) {
                flrId.text = flrResponse.flrID
                flrStartDateValue.text = flrResponse.beginTime
                flrPeakDateValue.text = flrResponse.peakTime
                flrResponse.endTime?.let { flrEndDateValue.text = it }
                flrClassType.text = flrResponse.classType
                flrLinkValue.text = flrResponse.link
            }
        }
    }

    class DiffUtilCallback(
        private val oldList: List<FLRResponse>?,
        private val newList: List<FLRResponse>?
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList?.size ?: 0

        override fun getNewListSize(): Int = newList?.size ?: 0

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList?.get(oldItemPosition)?.flrID == newList?.get(newItemPosition)?.flrID

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList?.get(oldItemPosition) == newList?.get(newItemPosition)
    }
}