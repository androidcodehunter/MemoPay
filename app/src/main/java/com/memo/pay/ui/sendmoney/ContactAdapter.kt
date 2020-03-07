package com.memo.pay.ui.sendmoney

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.memo.pay.R
import com.memo.pay.data.db.table.Account
import kotlinx.android.synthetic.main.list_item_frequent_contact.view.*
import kotlinx.android.synthetic.main.list_item_transaction_history.view.*

const val ITEM_TYPE_TITLE = 1
const val ITEM_TYPE_CONTACT = 2

class ContactAdapter(val onContactClickListener: (contact: Account) -> Unit) :
    ListAdapter<Any, RecyclerView.ViewHolder>(CONTACT_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == ITEM_TYPE_TITLE){
            TitleViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(android.R.layout.simple_list_item_1, parent, false)
            )
        }else{
            ContactViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_contact, parent, false)
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item is String){
            ITEM_TYPE_TITLE
        }else ITEM_TYPE_CONTACT
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == ITEM_TYPE_TITLE){
            (holder as TitleViewHolder).bindTo(getItem(position) as String)
        }else{
            (holder as ContactViewHolder).bindTo(getItem(position) as Account)
        }

    }

    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                onContactClickListener.invoke(getItem(adapterPosition) as Account)
            }
        }

        fun bindTo(account: Account) {
            itemView.tvContactName.text = account.name
        }
    }


    inner class TitleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bindTo(title: String) {
            (itemView as TextView).text = title
        }
    }


    companion object {
        private val CONTACT_COMPARATOR = object : DiffUtil.ItemCallback<Any>() {
            override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
                return if (oldItem is Account && newItem is Account){
                    oldItem.accountNumber == newItem.accountNumber
                }else oldItem == newItem
            }
            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
                return oldItem == newItem
            }
        }

    }



}