package com.rtuitlab.realityleap_rosseti.recyclers.defects

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rtuitlab.realityleap_rosseti.R
import com.rtuitlab.realityleap_rosseti.server.models.Defect
import kotlinx.android.synthetic.main.view_defect.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DefectsAdapter: RecyclerView.Adapter<DefectsAdapter.DefectHolder>() {

	interface OnDefectClickListener {
		fun onDefectClicked(defect: Defect)
	}

	var onDefectClickListener: OnDefectClickListener? = null

	var defectsList = listOf<Defect>()
		set(value) {
			GlobalScope.launch(Dispatchers.Main) {
				val defectsDiffResult = withContext(Dispatchers.Default) {
					DiffUtil.calculateDiff(DefectsDiffUtilCallback(defectsList, value))
				}
				field = value
				defectsDiffResult.dispatchUpdatesTo(this@DefectsAdapter)
			}
		}

	override fun getItemCount() = defectsList.size

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DefectHolder(
			LayoutInflater.from(parent.context)
					.inflate(R.layout.view_defect, parent, false)
	)

	override fun onBindViewHolder(holder: DefectHolder, position: Int) {
		holder.bind(defectsList[position])
	}

	inner class DefectHolder internal constructor(view: View): RecyclerView.ViewHolder(view) {
		private val defectLocationTV = view.defectLocation

		init {
			view.setOnClickListener {
				onDefectClickListener?.onDefectClicked(defectsList[adapterPosition])
			}
		}

		fun bind(defect: Defect) {
			defectLocationTV.text = defect.location
		}
	}
}