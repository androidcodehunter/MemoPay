package com.memo.pay.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.memo.pay.R
import com.memo.pay.data.Result
import com.memo.pay.data.db.table.Account
import com.memo.pay.utils.Constants.CURRENT_ACCOUNT_NUMBER
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class HomeFragment : Fragment() {

    private lateinit var mNavController: NavController
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
        (activity as MainActivity).toolbar_main?.apply {
            visibility = VISIBLE
            findViewById<AppCompatTextView>(R.id.tvAccountIcon).visibility = VISIBLE
        }
    }

    private fun showProgressLoading() {
        progressLoadingLayout.visibility = VISIBLE
        (activity as MainActivity).toolbar_main.visibility = GONE
    }

    private val transactionsObserver = androidx.lifecycle.Observer<Result<List<Any>>>{ result ->
        Timber.d("transactions ${result}")
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

    private fun showTransactions(transactions: List<Any>) {
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
        mNavController = Navigation.findNavController(view)

        val mainActivity = (activity as MainActivity)
        mainActivity.toolbar_main.apply {
            title = getString(R.string.memo_pay_balance)
            setTitleTextColor(ContextCompat.getColor(context, android.R.color.white))
            setBackgroundColor(ContextCompat.getColor(mainActivity, R.color.windowBackgroundColor))
            findViewById<AppCompatTextView>(R.id.tvAccountIcon).visibility = VISIBLE
        }

        tvAddMoney.setOnClickListener {
            mNavController.navigate(R.id.action_homeFragment_to_AddMoneyFragment)
        }
        tvSendMoney.setOnClickListener {
            mNavController.navigate(R.id.action_homeFragment_to_sendMoneyFragment)
        }
        tvMore.setOnClickListener {  }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mTransactionAdapter = TransactionHistoryAdapter {  }
        listTransactionHistory.apply {
            hasFixedSize()
            adapter = mTransactionAdapter
        }
        homeViewModel.getTransactions(true, CURRENT_ACCOUNT_NUMBER).observe(viewLifecycleOwner, transactionsObserver)
        homeViewModel.getAccount(true, CURRENT_ACCOUNT_NUMBER).observe(viewLifecycleOwner, accountObserver)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {}
    }
}
