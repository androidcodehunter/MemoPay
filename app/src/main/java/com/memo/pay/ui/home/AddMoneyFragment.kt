package com.memo.pay.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat

import com.memo.pay.R
import com.memo.pay.data.Result
import com.memo.pay.data.db.table.Account
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_add_money.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class AddMoneyFragment : Fragment() {
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
            }
        }
    }

    private fun showAccount(account: Account) {
        tvAccountBalance.text = account.getBalanceWithCurrency()
    }

    private fun hideProgressLoading() {
        progressbarAddMoney.visibility = GONE
    }

    private fun showProgressLoading() {
        progressbarAddMoney.visibility = VISIBLE
    }

    private val amountObserver = androidx.lifecycle.Observer<String>{ amount ->
       if (amount.isEmpty()){
           etEnterAmount.error = getString(R.string.amount_should_not_empty)
       }else{
           showProgressLoading()
           ///homeViewModel.addAmount(amount, accountNumber)
       }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_money, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeToolbarForAddMoney()
    }

    private fun changeToolbarForAddMoney() {
        val mainActivity = activity as MainActivity
        mainActivity.toolbar_main.apply {
            title = getString(R.string.enter_amount)
            setTitleTextColor(ContextCompat.getColor(mainActivity, R.color.primaryTextColor))
            setBackgroundColor(ContextCompat.getColor(mainActivity, R.color.colorPrimary))
            findViewById<AppCompatTextView>(R.id.tvAccountIcon).visibility = GONE
            navigationIcon?.colorFilter =
                BlendModeColorFilterCompat.createBlendModeColorFilterCompat(ContextCompat.getColor(mainActivity, R.color.darker_gray), BlendModeCompat.SRC_ATOP)
        }

        homeViewModel.getAccount(true, "1111111111").observe(viewLifecycleOwner, accountObserver)
        homeViewModel.getAmount().observe(viewLifecycleOwner, amountObserver)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btnTopUP.setOnClickListener {
            homeViewModel.setAmount(etEnterAmount.text.toString())
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddMoneyFragment().apply {}
    }
}
