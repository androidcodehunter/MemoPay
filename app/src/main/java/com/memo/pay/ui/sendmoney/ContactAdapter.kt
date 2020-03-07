package com.memo.pay.ui.sendmoney

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.memo.pay.R
import com.memo.pay.data.db.table.Account
import kotlinx.android.synthetic.main.list_item_frequent_contact.view.*

class ContactAdapter(val onContactClickListener: (contact: Account) -> Unit) :
    ListAdapter<Account, ContactAdapter.ContactViewHolder>(CONTACT_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactAdapter.ContactViewHolder {
        return ContactViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_contact, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ContactAdapter.ContactViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                /* getItem(adapterPosition).html_url?.let{
                     onTransactionItemClickListener(it)
                 }*/
            }
        }

        fun bindTo(account: Account) {
            itemView.tvContactName.text = account.name
            // itemView.tvTransactorName.text = transaction.name
            //  itemView.tvSendReceiveStatus.text = transaction.type
            // itemView.tvTransactionAmount.text = "${transaction.currency} ${transaction.transactionAmount}"
        }
    }

    companion object {
        /**
         * Contact Account comparator to check for new data updates only, it will ignore duplicate data update.
         * This technique is very effective when you update data continuously.
         */
        private val CONTACT_COMPARATOR = object : DiffUtil.ItemCallback<Account>() {
            override fun areItemsTheSame(oldItem: Account, newItem: Account): Boolean {
                return oldItem.accountNumber == newItem.accountNumber
            }

            override fun areContentsTheSame(oldItem: Account, newItem: Account): Boolean {
                return oldItem == newItem
            }
        }

    }



}