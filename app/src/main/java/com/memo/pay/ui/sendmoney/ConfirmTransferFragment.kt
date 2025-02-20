package com.memo.pay.ui.sendmoney

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.memo.pay.R
import com.memo.pay.data.Result
import com.memo.pay.data.db.table.Account
import com.memo.pay.data.db.table.Transaction
import com.memo.pay.ui.viewmodel.HomeViewModel
import com.memo.pay.ui.MainActivity
import com.memo.pay.ui.sendmoney.ContactSelectionFragment.Companion.KEY_AMOUNT
import com.memo.pay.utils.Constants
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_confirm_transfer.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class ConfirmTransferFragment: Fragment() {
    private var amount: Double = 0.0
    private lateinit var account: Account
    private val homeViewModel: HomeViewModel by viewModel()

    private val sendMoneyResponse = androidx.lifecycle.Observer<Result<Transaction>>{ result ->

        Timber.d("send money response ${result}")

        when (result) {
            is Result.Loading -> {
                showProcessing()
            }
            is Result.Success -> {
                hideProcessing()
                fundTransferSuccess(result.data)
            }
            is Result.Error -> {
                hideProcessing()
                fundTransferError()
            }
        }
    }

    private fun fundTransferError() {
        sendMoneyErrorContainer.visibility = VISIBLE
        sendMoneySuccessContainer.visibility = GONE
    }

    private fun fundTransferSuccess(data: Transaction) {
        sendMoneyErrorContainer.visibility = GONE
        sendMoneySuccessContainer.visibility = VISIBLE
        tvSuccess.text = getString(R.string.sent_success_message).format(data.transactionAmount.toString(), account.name)
    }

    private fun hideProcessing() {
        sendMoneyProcessingContainer.visibility = GONE
    }

    private fun showProcessing() {
        sendMoneyProcessingContainer.visibility = VISIBLE
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_confirm_transfer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).toolbar_main.apply {
            title = getString(R.string.confirm_transfer)
        }
        btnSendMoney.setOnClickListener {
            homeViewModel.sendMoney(amount, Constants.CURRENT_ACCOUNT_NUMBER, account).observe(viewLifecycleOwner, sendMoneyResponse)
        }

        btnGotoDashBoard.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_confirmTransferFragment_to_homeFragment)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()
    }

    private fun initData() {
        account = arguments?.getSerializable(KEY_ACCOUNT) as Account
        arguments?.getDouble(KEY_AMOUNT)?.let {
            amount = it
        }
        tvCreditedAmount.text = amount.toString()
        tvProfileName.text = account.name
        Timber.d("account $account amount $amount")
    }

    companion object{
        const val KEY_ACCOUNT = "key_account"
    }
}