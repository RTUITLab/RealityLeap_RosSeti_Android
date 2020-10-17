package com.rtuitlab.realityleap_rosseti.recyclers.defects

import androidx.recyclerview.widget.DiffUtil
import com.rtuitlab.realityleap_rosseti.server.models.Defect

class DefectsDiffUtilCallback (
		private val oldList: List<Defect>,
		private val newList: List<Defect>
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
		return old.location == new.location &&
				old.equipmentType == new.equipmentType &&
				old.description == new.description
	}
}