package com.memo.pay.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.memo.pay.R
import com.memo.pay.data.db.table.Transaction
import com.memo.pay.utils.Constants.ACCOUNT_TYPE_SENT
import kotlinx.android.synthetic.main.list_item_transaction_history.view.*
import java.util.*

const val ITEM_TYPE_TRANSACTION = 1
const val ITEM_TYPE_DATE = 2

class TransactionHistoryAdapter(val onTransactionItemClickListener: (transaction: Transaction) -> Unit) : ListAdapter<Any, RecyclerView.ViewHolder>(TRANSACTION_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_TYPE_DATE){
            ViewHolderDate(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_transaction_date, parent, false)
            )
        }else{
            ViewHolderTransaction(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_transaction_history, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (holder is ViewHolderTransaction){
            holder.bindTo(item as Transaction)
        }else{
            (holder as ViewHolderDate).bind(item as String)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val transactionItem = getItem(position)
        return if (transactionItem is Transaction){
            ITEM_TYPE_TRANSACTION
        }else ITEM_TYPE_DATE
    }

    inner class ViewHolderTransaction(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
               /* getItem(adapterPosition).html_url?.let{
                    onTransactionItemClickListener(it)
                }*/
            }
        }

        fun bindTo(transaction: Transaction) {
            itemView.tvTransactorName.text = transaction.name
            itemView.tvSendReceiveStatus.text = transaction.type
            itemView.tvTransactionAmount.text = "${transaction.currency} ${transaction.transactionAmount}"
            if (transaction.type == ACCOUNT_TYPE_SENT){
                itemView.imgTransactionType.setImageResource(R.drawable.avatar_default)
            }else{
                itemView.imgTransactionType.setImageResource(R.drawable.avatar_default)
            }
        }
    }

    inner class ViewHolderDate(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(date: String) {
            val dateText = itemView as AppCompatTextView
            dateText.text = date
        }

    }

    companion object {
        /**
         * Transaction comparator to check for new data updates only, it will ignore duplicate data update.
         * This technique is very effective when you update data continuously.
         */
        private val TRANSACTION_COMPARATOR = object : DiffUtil.ItemCallback<Any>() {
            override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
                return if (oldItem is Transaction && newItem is Transaction){
                    oldItem.id == newItem.id
                }else oldItem == newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
                return oldItem == newItem
            }
        }

    }



}