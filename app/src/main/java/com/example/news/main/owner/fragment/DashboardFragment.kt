package com.example.news.main.owner.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news.R
import com.example.news.binding.FragmentDataBindingComponent
import com.example.news.databinding.FragmentDashboardBinding
import com.example.news.di.Injectable
import com.example.news.main.observer.DashboardViewModel
import com.example.news.main.owner.adapter.NewsListAdapter
import com.example.news.util.helperUtils.AppExecutors
import com.example.news.util.remoteUtils.Status
import javax.inject.Inject

class DashboardFragment : Fragment(), Injectable {

    lateinit var binding: FragmentDashboardBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors

    val dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    lateinit var viewModel: DashboardViewModel
    lateinit var adapter: NewsListAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[DashboardViewModel::class.java]
        viewModel.init()

        adapter = NewsListAdapter(appExecutors, dataBindingComponent) {

        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        viewModel.news.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.ERROR -> {
                }
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    it.data?.apply {
                        adapter.submitList(this)
                    }
                }
            }
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_dashboard,
            container,
            false,
            dataBindingComponent
        )
        return binding.root
    }
}