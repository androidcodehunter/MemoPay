package com.memo.pay.ui.sendmoney

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.memo.pay.R
import kotlinx.android.synthetic.main.fragment_send_money.*

class SendMoneyFragment: Fragment() {

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
        btnNext.setOnClickListener {
            vavController.navigate(R.id.action_sendMoneyFragment_to_ContactSelectionFragment)
        }
    }

}