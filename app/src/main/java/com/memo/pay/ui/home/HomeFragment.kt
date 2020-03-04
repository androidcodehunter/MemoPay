package com.memo.pay.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.memo.pay.R
import com.memo.pay.data.db.table.Account
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.memo.pay.data.Result
import com.memo.pay.data.db.table.Transaction

class HomeFragment : Fragment() {

    private lateinit var mTransactionAdapter:TransactionHistoryAdapter

    private val homeViewModel: HomeViewModel by viewModel()

    private val accountObserver = androidx.lifecycle.Observer<Result<Account>>{ result ->
        when (result) {
            is Result.Loading -> {
                showProgressLoading()
            }
            is Result.Success -> {
                hideProgressLoading()
                showAccount(result.data)
            }
            is Result.Error -> {
                hideProgressLoading()
                ///showError()
            }
        }
    }

    private fun showAccount(data: Account) {
        tvAccountBalance.text = data.getBalanceWithCurrency()
    }

    private fun hideProgressLoading() {
        progressLoadingLayout.visibility = GONE

    }

    private fun showProgressLoading() {
        progressLoadingLayout.visibility = VISIBLE
    }

    private val transactionsObserver = androidx.lifecycle.Observer<Result<List<Transaction>>>{ result ->
        when (result) {
            is Result.Loading -> {
                showProgressLoading()
            }
            is Result.Success -> {
                hideProgressLoading()
                showTransactions(result.data)
            }
            is Result.Error -> {
                hideProgressLoading()
                ///showError()
            }
        }
    }

    private fun showTransactions(transactions: List<Transaction>) {
        mTransactionAdapter.submitList(transactions)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvAddMoney.setOnClickListener {  }
        tvSendMoney.setOnClickListener {  }
        tvMore.setOnClickListener {  }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mTransactionAdapter = TransactionHistoryAdapter {  }
        listTransactionHistory.apply {
            hasFixedSize()
            adapter = mTransactionAdapter
        }
        homeViewModel.getTransactions(true, "1111111111").observe(viewLifecycleOwner, transactionsObserver)
        homeViewModel.getAccount(true, "1111111112").observe(viewLifecycleOwner, accountObserver)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {}
    }
}
