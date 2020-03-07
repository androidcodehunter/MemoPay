package com.memo.pay.ui.addmoney

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import com.memo.pay.R
import com.memo.pay.data.Result
import com.memo.pay.data.db.table.Account
import com.memo.pay.extensions.hideKeyboard
import com.memo.pay.notification.BasicNotification
import com.memo.pay.notification.NotificationChannelFactory
import com.memo.pay.notification.NotificationFactory
import com.memo.pay.notification.NotificationType
import com.memo.pay.ui.home.HomeViewModel
import com.memo.pay.ui.home.MainActivity
import com.memo.pay.utils.Constants.CURRENT_ACCOUNT_NUMBER
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_add_money.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class AddMoneyFragment : Fragment() {
    private val homeViewModel: HomeViewModel by viewModel()
    private val notificationFactory: NotificationFactory by inject()
    private val notificationChannelFactory: NotificationChannelFactory by inject()

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

    private val amountObserver = androidx.lifecycle.Observer<Pair<Double, String>>{ pair ->
       if (pair.second.isEmpty()){
           etEnterAmount.error = getString(R.string.amount_should_not_empty)
       }else{
           hideKeyboard()
           showProgressLoading()
           addMoney(pair.first, pair.second)
       }
    }

    private val addMoneyObserver = androidx.lifecycle.Observer<Result<HomeViewModel.AddMoney>>{ result ->
        when (result) {
            is Result.Loading -> {
                showProgressLoading()
            }
            is Result.Success -> {
                hideProgressLoading()
                showAccount(result.data.account)
                showAddMoneyNotification(result.data.amount)
            }
            is Result.Error -> {
                hideProgressLoading()
            }
        }
    }

    private fun addMoney(amount: Double, accountNumber: String) {
        homeViewModel.addMoney(amount, accountNumber).observe(viewLifecycleOwner, addMoneyObserver)
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

        homeViewModel.getAccount(true, CURRENT_ACCOUNT_NUMBER).observe(viewLifecycleOwner, accountObserver)
        homeViewModel.getAmount().observe(viewLifecycleOwner, amountObserver)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btnTopUP.setOnClickListener {
            homeViewModel.setAmount(etEnterAmount.text.toString(), CURRENT_ACCOUNT_NUMBER)
        }
    }


    /*TODO show basic notification when add money is successful. Check {@link NotificationFactory} class for more details.*/
    private fun showAddMoneyNotification(amount: Double) {
        val notification = BasicNotification(getString(R.string.notifications_general_channel_id),
            getString(R.string.notifications_title_add_money),
            String.format(getString(R.string.notifications_content_add_money), amount.toString()),
            NotificationCompat.PRIORITY_HIGH
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationFactory.showNotification(NotificationType.BASIC.ordinal, notification, notificationChannelFactory.getDefaultChannel())
        }else{
            notificationFactory.showNotification(NotificationType.BASIC.ordinal, notification)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddMoneyFragment().apply {}
    }
}
