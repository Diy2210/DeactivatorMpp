package com.rompos.deactivator.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rompos.deactivator.R
import com.rompos.deactivator.Servers
import com.rompos.deactivator.databinding.ServerListItemBinding
import com.rompos.deactivator.models.ServerViewModel

class ServersAdapter(
    var items: List<Servers>,
    val clickItemCallback: ClickCallback,
    private val clickEditItemCallback: EditClickCallback,
    private val clickDeleteItemCallback: DeleteClickCallback
) : RecyclerView.Adapter<ServersAdapter.ServerViewHolder>() {

    inner class ServerViewHolder(private val binding: ServerListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ServerViewModel) {
            binding.item = item
            binding.executePendingBindings()
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) clickItemCallback.onItemClicked(items[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServerViewHolder {
        return ServerViewHolder(ServerListItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ServerViewHolder, position: Int) {
        holder.itemView.setOnCreateContextMenuListener { contextMenu, _, _ ->
            contextMenu.add(R.string.edit_item).setOnMenuItemClickListener {
                if (position != RecyclerView.NO_POSITION) {
                    clickEditItemCallback.onEditItemClicked(items[position])
                }
                true
            }
            contextMenu.add(R.string.delete_item).setOnMenuItemClickListener {
                if (position != RecyclerView.NO_POSITION) {
                    clickDeleteItemCallback.onDeleteItemClicked(items[position])
                }
                true
            }
        }
        holder.bind(ServerViewModel().setModel(items[position]))
    }

    interface ClickCallback {
        fun onItemClicked(item: Servers)
    }

    interface EditClickCallback {
        fun onEditItemClicked(item: Servers)
    }

    interface DeleteClickCallback {
        fun onDeleteItemClicked(item: Servers)
    }
}
