package com.example.foodies.fragments.categories

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodies.R
import com.example.foodies.activities.CategoriesActivity
import com.example.foodies.adapters.CategoriesAdapter
import com.example.foodies.databinding.FragmentCategoriesBinding
import com.example.foodies.fragments.home.HomeFragment
import com.example.foodies.viewmodel.HomeViewModel

class CategoriesFragment : Fragment() {
    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var adapterCategories: CategoriesAdapter
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentCategoriesBinding.inflate(inflater, container, false)
        adapterCategories = CategoriesAdapter()


        prepareCategoriesRecyclerView()


        return binding.root
    }



    private fun prepareCategoriesRecyclerView() {
       binding.rvCategoriesMeal.apply {
           layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
           adapter = adapterCategories
       }
    }
}