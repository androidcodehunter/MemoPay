package com.memo.pay.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager

import com.memo.pay.R
import com.memo.pay.di.viewModelModule
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    private lateinit var mTransactionAdapter:TransactionHistoryAdapter

    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mTransactionAdapter = TransactionHistoryAdapter {  }
        listTransactionHistory.apply {
            hasFixedSize()
            adapter = mTransactionAdapter
        }
        mTransactionAdapter.submitList(homeViewModel.getTransactions())
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {}
    }
}
