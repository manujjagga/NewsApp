package com.example.news.binding

import androidx.databinding.DataBindingComponent
import androidx.fragment.app.Fragment

class FragmentDataBindingComponent(var fragment: Fragment) : DataBindingComponent {

    private val adapter = FragmentBindingAdapter(fragment)

    override fun getFragmentBindingAdapter() = adapter

}