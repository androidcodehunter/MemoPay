package com.memo.pay.ui.sendmoney

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.memo.pay.R
import com.memo.pay.data.db.table.Account
import com.memo.pay.data.db.table.Transaction
import com.memo.pay.ui.home.HomeViewModel
import com.memo.pay.ui.sendmoney.ContactSelectionFragment.Companion.KEY_AMOUNT
import com.memo.pay.utils.Constants
import kotlinx.android.synthetic.main.fragment_confirm_transfer.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.util.*

class ConfirmTransferFragment: Fragment() {
    private var amount: Double = 0.0
    private lateinit var account: Account
    private val homeViewModel: HomeViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_confirm_transfer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSendMoney.setOnClickListener {
            val transaction = Transaction("1",
                "Sharifur",
                "sent",
                10.00,
                "AED",
                "",
                Date(),
                Constants.CURRENT_ACCOUNT_NUMBER,
                "1111111111")

           /// homeViewModel.sendMoney("", "", "")

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
        Timber.d("account $account amount $amount")
    }

    companion object{
        const val KEY_ACCOUNT = "key_account"
    }
}