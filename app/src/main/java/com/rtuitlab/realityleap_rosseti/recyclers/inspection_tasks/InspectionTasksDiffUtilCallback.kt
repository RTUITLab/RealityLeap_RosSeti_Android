package com.rtuitlab.realityleap_rosseti.recyclers.inspection_tasks

import androidx.recyclerview.widget.DiffUtil
import com.rtuitlab.realityleap_rosseti.server.models.InspectionTask

class InspectionTasksDiffUtilCallback (
    private val oldList: List<InspectionTask>,
    private val newList: List<InspectionTask>
): DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldList[oldItemPosition]
        val new = newList[newItemPosition]
        return old.id == new.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldList[oldItemPosition]
        val new = newList[newItemPosition]
        return old.place == new.place &&
                old.creator == new.creator &&
                old.executor == new.executor &&
                old.safetyEvent == new.safetyEvent
    }
}