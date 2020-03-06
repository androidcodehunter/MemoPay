package com.memo.pay.ui.sendmoney

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.memo.pay.R
import kotlinx.android.synthetic.main.fragment_contact_selection.*

class ContactSelectionFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contact_selection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val vavController = Navigation.findNavController(view)
        btnSendViaOtp.setOnClickListener {
            vavController.navigate(R.id.action_contactSelectionFragment_to_confirmTransferFragment)
        }
    }
}