package com.example.foodies.fragments.favorites

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.foodies.R
import com.example.foodies.activities.MealActivity
import com.example.foodies.adapters.FavoritesMealsAdapter
import com.example.foodies.databinding.FragmentFavoritesBinding
import com.example.foodies.ui.fragments.home.HomeFragment
import com.example.foodies.viewmodel.HomeViewModel
import com.google.android.material.snackbar.Snackbar

class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapterFavorites: FavoritesMealsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)

        adapterFavorites = FavoritesMealsAdapter()

        prepareFavoritesRecyclerview()
        //observeFavorites()
        onMealClick()

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT,

        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val currentItem = adapterFavorites.differ.currentList[position]
                viewModel.delete(currentItem)

                Snackbar.make(requireView(), "Meal Deleted", Snackbar.LENGTH_LONG)
                    .setAction("Undo",
                    View.OnClickListener {
                        viewModel.insertUpdate(currentItem)
                    })
                    .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.accent))
                    .setActionTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    .show()
            }
        }

        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvFavorites)

        return binding.root
    }

    private fun onMealClick() {
        adapterFavorites.onMealClick = {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(HomeFragment.MEAL_ID, it.idMeal)
            intent.putExtra(HomeFragment.MEAL_NAME, it.strMeal)
            intent.putExtra(HomeFragment.MEAL_THUMB, it.strMealThumb)
            startActivity(intent)
        }
    }

    private fun prepareFavoritesRecyclerview() {
        binding.rvFavorites.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = adapterFavorites
        }
    }

    /*private fun observeFavorites() {
        viewModel.getAllMeals.observe(viewLifecycleOwner, Observer {
            adapterFavorites.differ.submitList(it)
        })
    } */
}