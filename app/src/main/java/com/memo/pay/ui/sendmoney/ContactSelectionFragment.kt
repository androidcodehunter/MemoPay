package com.memo.pay.ui.sendmoney

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.memo.pay.R
import com.memo.pay.data.Result
import com.memo.pay.data.db.table.Account
import com.memo.pay.ui.home.HomeViewModel
import kotlinx.android.synthetic.main.fragment_contact_selection.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class ContactSelectionFragment: Fragment() {
    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var mFrequentAdapter: FrequentContactAdapter
    private lateinit var mContactAdapter: ContactAdapter
    private lateinit var vavController: NavController
    private val frequentContactObserver = androidx.lifecycle.Observer<Result<List<Account>>>{ result ->
        when (result) {
            is Result.Loading -> {
                ///showProgressLoading()
            }
            is Result.Success -> {
                //hideProgressLoading()
                showFrequentContacts(result.data)
            }
            is Result.Error -> {
                //hideProgressLoading()
                ///showError()
            }
        }
    }

    private fun showFrequentContacts(frequentContacts: List<Account>) {
        Timber.d("frequent contacts $frequentContacts")
        mFrequentAdapter.submitList(frequentContacts)
    }

    private val contactObserver = androidx.lifecycle.Observer<Result<List<Any>>>{ result ->
        when (result) {
            is Result.Loading -> {
               // showProgressLoading()
            }
            is Result.Success -> {
                ///hideProgressLoading()
                showContacts(result.data)
            }
            is Result.Error -> {
                ///hideProgressLoading()
                ///showError()
            }
        }
    }

    private fun showContacts(contacts: List<Any>) {
        mContactAdapter.submitList(contacts)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contact_selection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vavController = Navigation.findNavController(view)
        setFrequentListAdapter()
        setContactAdapter()
    }

    private fun setContactAdapter() {
        mContactAdapter = ContactAdapter {
            Timber.d("contact clicked $it")
            vavController.navigate(R.id.action_contactSelectionFragment_to_confirmTransferFragment)
        }
        listContact.apply {
            adapter = mContactAdapter
            addItemDecoration(DividerItemDecoration(context, (layoutManager as LinearLayoutManager).orientation))
            isNestedScrollingEnabled = true
            setHasFixedSize(true)
        }

    }

    private fun setFrequentListAdapter() {
        mFrequentAdapter = FrequentContactAdapter {
            Timber.d("frequent contact clicked $it")
            vavController.navigate(R.id.action_contactSelectionFragment_to_confirmTransferFragment)
        }
        listFrequent.apply {
            adapter = mFrequentAdapter
            PagerSnapHelper().attachToRecyclerView(this)
            val itemDecoration = DividerItemDecoration(context, (layoutManager as LinearLayoutManager).orientation)
            ContextCompat.getDrawable(context, R.drawable.divider)?.let {
                itemDecoration.setDrawable(it)
            }
            addItemDecoration(itemDecoration)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        homeViewModel.getFrequentContacts().observe(viewLifecycleOwner, frequentContactObserver)
        homeViewModel.getContacts(getString(R.string.favorites), getString(R.string.other_contacts)).observe(viewLifecycleOwner, contactObserver)
    }

}