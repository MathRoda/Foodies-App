package com.example.foodies.fragments.bottomsheet

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import coil.load
import com.example.foodies.activities.MealActivity
import com.example.foodies.databinding.FragmentMealBottomSheetBinding
import com.example.foodies.ui.fragments.home.HomeFragment
import com.example.foodies.viewmodel.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

private const val MEAL_ID = "param1"

class MealBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentMealBottomSheetBinding
    private val viewModel: HomeViewModel by activityViewModels()
    private var mealId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealId = it.getString(MEAL_ID)

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding =  FragmentMealBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mealId?.let {
            viewModel.getMealById(it)
        }
        observeBottomSheetMeal()

        onBottomSheetDialogClick()
    }

    private fun onBottomSheetDialogClick() {
        binding.parentBottomSheetLayout.setOnClickListener {
            if (mealName != null && mealThumb != null) {
                val intent = Intent(activity, MealActivity::class.java)
                intent.apply {
                    putExtra(HomeFragment.MEAL_ID, mealId)
                    putExtra(HomeFragment.MEAL_NAME, mealName)
                    putExtra(HomeFragment.MEAL_THUMB, mealThumb)
                }
                startActivity(intent)
            }
        }
    }

    private var mealName: String? = null
    private var mealThumb: String? = null
    private fun observeBottomSheetMeal() {
        viewModel.bottomSheetMeal.observe(viewLifecycleOwner, Observer {
            binding.imgBottomSheet.load(it.strMealThumb)
            binding.tvBottomSheetCategory.text = it.strCategory
            binding.tvMealName.text = it.strMeal
            binding.tvBottomSheetLocation.text = it.strArea

            mealName = it.strMeal
            mealThumb = it.strMealThumb
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
                MealBottomSheetFragment().apply {
                    arguments = Bundle().apply {
                        putString(MEAL_ID, param1)
                    }
                }
    }
}