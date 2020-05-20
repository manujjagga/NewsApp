package com.example.news.main.owner.fragment

import android.os.Bundle
import android.transition.TransitionInflater
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
import com.example.news.databinding.FragmentNewsDetailBinding
import com.example.news.di.Injectable
import com.example.news.main.observer.DashboardViewModel
import com.example.news.main.owner.adapter.NewsListAdapter
import com.example.news.model.ArticlesItem
import com.example.news.util.helperUtils.AppExecutors
import com.example.news.util.remoteUtils.Status
import javax.inject.Inject

class NewsDetailFragment : Fragment(), Injectable {

    lateinit var binding: FragmentNewsDetailBinding

    val dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var newsItem: ArticlesItem? = null
    var transitionPosition: Int? = null

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("newsItem", newsItem)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsItem =
            savedInstanceState?.getParcelable("newsItem") ?: arguments?.let {
                NewsDetailFragmentArgs.fromBundle(
                    it
                ).newsItem
            }
        transitionPosition =
            savedInstanceState?.getInt("transitionPosition") ?: arguments?.let {
                NewsDetailFragmentArgs.fromBundle(
                    it
                ).transitionPosition
            }
        TransitionInflater.from(context).inflateTransition(R.transition.shared_element_transition)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.item = newsItem
        binding.imageView.transitionName = "imageView$transitionPosition"
        binding.title.transitionName = "title$transitionPosition"
        binding.source.transitionName = "source$transitionPosition"
        binding.date.transitionName = "date$transitionPosition"
        binding.back.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_news_detail,
            container,
            false,
            dataBindingComponent
        )
        return binding.root
    }
}