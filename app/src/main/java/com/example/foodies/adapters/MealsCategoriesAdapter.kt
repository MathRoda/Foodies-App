package com.example.foodies.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.foodies.databinding.MealsItemBinding
import com.example.foodies.module.mostpopular.MostPopularMeal

class MealsCategoriesAdapter: RecyclerView.Adapter<MealsCategoriesAdapter.ViewHolder>() {

    private var mealsByCategoryList = emptyList<MostPopularMeal>()

    class ViewHolder(binding: MealsItemBinding): RecyclerView.ViewHolder(binding.root) {
        private val imgMeal = binding.imgMeal
        private val tvMealName = binding.tvMealName

        fun bind(mostPopularMeal: MostPopularMeal) {
            imgMeal.load(mostPopularMeal.strMealThumb)
            tvMealName.text = mostPopularMeal.strMeal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            MealsItemBinding.inflate(LayoutInflater.from(parent.context)
            ,parent,
            false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = mealsByCategoryList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return mealsByCategoryList.size
    }

    fun setData(meal : List<MostPopularMeal>) {
        this.mealsByCategoryList = meal
        notifyDataSetChanged()
    }
}