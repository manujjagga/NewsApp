package com.example.news.main.owner.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.DiffUtil
import com.example.news.R
import com.example.news.databinding.LayoutNewsItemBinding
import com.example.news.model.ArticlesItem
import com.example.news.util.helperUtils.AppExecutors
import com.example.news.util.viewUtils.DataBoundListAdapter

class NewsListAdapter(
    appExecutors: AppExecutors,
    val dataBindingComponent: DataBindingComponent,
    val callback: (ArticlesItem, Int, FragmentNavigator.Extras) -> Unit
) : DataBoundListAdapter<ArticlesItem, LayoutNewsItemBinding>(

    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<ArticlesItem>() {
        override fun areItemsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
            return oldItem == newItem
        }
    }
) {
    override fun createBinding(parent: ViewGroup): LayoutNewsItemBinding {
        val binding = DataBindingUtil
            .inflate<LayoutNewsItemBinding>(
                LayoutInflater.from(parent.context),
                R.layout.layout_news_item,
                parent,
                false,
                dataBindingComponent
            )

        return binding
    }

    override fun bind(
        binding: LayoutNewsItemBinding,
        item: ArticlesItem,
        position: Int
    ) {
        binding.item = item
        binding.imageView.transitionName = "imageView$position"
        binding.title.transitionName = "title$position"
        binding.source.transitionName = "source$position"
        binding.date.transitionName = "date$position"
        binding.root.setOnClickListener {
            val extras = FragmentNavigatorExtras(
                binding.imageView to "imageView$position",
                binding.title to "title$position",
                binding.source to "source$position",
                binding.date to "date$position"
            )
            callback.invoke(item, position, extras)
        }
    }

}