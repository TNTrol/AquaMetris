package org.vsu.pt.team2.utilitatemmetrisapp.ui.adapters.metersList

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.vsu.pt.team2.utilitatemmetrisapp.R
import org.vsu.pt.team2.utilitatemmetrisapp.databinding.ItemMeterWithCheckboxBinding
import org.vsu.pt.team2.utilitatemmetrisapp.ui.setFromVM
import org.vsu.pt.team2.utilitatemmetrisapp.viewmodels.MeterItemViewModel

class MetersWithCheckboxListAdapter :
    ListAdapter<MeterItemViewModel, MetersWithCheckboxListAdapter.MeterViewHolder>(
        MeterDiffCallback()
    ) {

    var callbackOnItemLongClick: ((MeterItemViewModel) -> Unit) = {}

    var callbackOnCheckedItemsChanged: ((List<MeterItemViewModel>) -> Unit) = {}

    private val checkedItems: MutableList<MeterItemViewModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeterViewHolder {
        return MeterViewHolder(
            ItemMeterWithCheckboxBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            { meter: MeterItemViewModel, checked: Boolean ->
                if (checked)
                    checkedItems.add(meter)
                else
                    checkedItems.remove(meter)
                callbackOnCheckedItemsChanged.invoke(checkedItems)
            },
            callbackOnItemLongClick,
            checkedItems
        )
    }

    override fun onBindViewHolder(holder: MeterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun getChecked(): List<MeterItemViewModel> {
        return checkedItems.toList()
    }

    class MeterViewHolder(
        val binding: ItemMeterWithCheckboxBinding,
        private val onMeterClickCallback:
            (MeterItemViewModel, isChecked: Boolean) -> Unit = { meter, checked -> },
        private val onMeterLongClickCallback: (MeterItemViewModel) -> Unit = {},
        private val checkedItems: List<MeterItemViewModel>,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onClick(item: MeterItemViewModel, context: Context) {
            binding.checkbox.let {
                if (item.backlog > 0.0) {
                    it.isChecked = !it.isChecked
                    onMeterClickCallback.invoke(item, it.isChecked)
                } else {
                    Toast.makeText(
                        context,
                        context.getString(R.string.meter_dont_has_backlog),
                        Toast.LENGTH_SHORT
                    ).show()
                    it.isChecked = false
                    onMeterClickCallback.invoke(item, it.isChecked)
                }
            }
        }

        fun bind(item: MeterItemViewModel) = with(itemView) {
            binding.apply {
                setFromVM(item, context)
                checkbox.isChecked = checkedItems.contains(item)
            }

            binding.checkbox.isClickable = false

            setOnClickListener {
                onClick(item, context)
            }

            setOnLongClickListener {
                onMeterLongClickCallback.invoke(item)
                true
            }
        }
    }

    class MeterDiffCallback : DiffUtil.ItemCallback<MeterItemViewModel>() {
        override fun areItemsTheSame(
            oldItem: MeterItemViewModel,
            newItem: MeterItemViewModel
        ): Boolean {
            return oldItem.identifier === newItem.identifier
        }

        override fun areContentsTheSame(
            oldItem: MeterItemViewModel,
            newItem: MeterItemViewModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}