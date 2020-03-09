package com.memo.pay.ui.sendmoney

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.memo.pay.R
import com.memo.pay.data.Result
import com.memo.pay.data.db.table.Account
import com.memo.pay.extensions.hideKeyboard
import com.memo.pay.extensions.showSnackBar
import com.memo.pay.ui.home.HomeViewModel
import com.memo.pay.ui.home.MainActivity
import com.memo.pay.utils.Constants
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_send_money.*
import kotlinx.android.synthetic.main.fragment_send_money.tvAccountBalance
import org.koin.androidx.viewmodel.ext.android.viewModel

class SendMoneyFragment: Fragment() {
    private val homeViewModel: HomeViewModel by viewModel()

    private val accountObserver = androidx.lifecycle.Observer<Result<Account>>{ result ->
        when (result) {
            is Result.Loading -> {
                //showProgressLoading()
            }
            is Result.Success -> {
                ///hideProgressLoading()
                showAccount(result.data)
            }
            is Result.Error -> {
                ///hideProgressLoading()
            }
        }
    }

    private fun showAccount(account: Account) {
        homeViewModel.setMyAccount(account)
        tvAccountBalance.text = account.getBalanceWithCurrency()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_send_money, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val vavController = Navigation.findNavController(view)
        setToolbar()
        btnNext.setOnClickListener {
            when {
                etEnterAmount.text.toString().isEmpty() -> {
                    etEnterAmount.error = getString(R.string.amount_should_not_empty)
                }
                homeViewModel.isAmountValid(etEnterAmount.text.toString().toDouble()) -> {
                    val bundle = Bundle()
                    bundle.putDouble(KEY_AMOUNT, etEnterAmount.text.toString().toDouble())
                    vavController.navigate(R.id.action_sendMoneyFragment_to_ContactSelectionFragment, bundle)
                    hideKeyboard()
                }
                else -> {
                    it.showSnackBar(getString(R.string.error_amount_exceeded))
                }
            }
        }
    }

    private fun setToolbar() {
        val mainActivity = activity as MainActivity
        mainActivity.toolbar_main.apply {
            title = getString(R.string.enter_amount)
            setTitleTextColor(ContextCompat.getColor(mainActivity, R.color.primaryTextColor))
            setBackgroundColor(ContextCompat.getColor(mainActivity, R.color.colorPrimary))
            findViewById<AppCompatTextView>(R.id.tvAccountIcon).visibility = View.GONE
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        homeViewModel.getAccount(true, Constants.CURRENT_ACCOUNT_NUMBER).observe(viewLifecycleOwner, accountObserver)
    }

    companion object{
        const val KEY_AMOUNT = "key_amount"
    }

}