package com.rtuitlab.realityleap_rosseti.recyclers.inspection_tasks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rtuitlab.realityleap_rosseti.R
import com.rtuitlab.realityleap_rosseti.server.models.InspectionTask
import kotlinx.android.synthetic.main.view_inspection_task.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InspectionTasksAdapter: RecyclerView.Adapter<InspectionTasksAdapter.InspectionHolder>() {

    interface OnInspectionClickListener {
        fun onInspectionClicked(inspectionTask: InspectionTask)
    }

    var onInspectionClickListener: OnInspectionClickListener? = null

    var inspectionTasksList = listOf<InspectionTask>()
    set(value) {
        GlobalScope.launch(Dispatchers.Main) {
            val tasksDiffResult = withContext(Dispatchers.Default) {
                DiffUtil.calculateDiff(
                    InspectionTasksDiffUtilCallback(inspectionTasksList, value)
                )
            }
            field = value
            tasksDiffResult.dispatchUpdatesTo(this@InspectionTasksAdapter)
        }
    }

    override fun getItemCount() = inspectionTasksList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = InspectionHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.view_inspection_task, parent, false)
    )

    override fun onBindViewHolder(holder: InspectionHolder, position: Int) {
        holder.bind(inspectionTasksList[position])
    }

    inner class InspectionHolder internal constructor(view: View): RecyclerView.ViewHolder(view) {
        private val placeNameTV = view.placeName

        init {
            view.setOnClickListener {
                onInspectionClickListener?.onInspectionClicked(inspectionTasksList[adapterPosition])
            }
        }

        fun bind(inspectionTask: InspectionTask) {
            placeNameTV.text = inspectionTask.place.name
        }
    }
}