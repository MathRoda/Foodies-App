package com.example.foodies.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.foodies.activities.CategoriesActivity
import com.example.foodies.databinding.PopularItemBinding
import com.example.foodies.fragments.categories.CategoriesFragment
import com.example.foodies.module.categorymeal.Category
import com.example.foodies.module.categorymeal.CategoryList
import com.example.foodies.module.mostpopular.MostPopularMeal

class MostPopularMealAdapter: RecyclerView.Adapter<MostPopularMealAdapter.PopularMealViewHolder>() {

    private var mealList = emptyList<MostPopularMeal>()
     var onMealClick: ((MostPopularMeal) -> Unit)? = null
     var onLongMealClick: ((MostPopularMeal) -> Unit)? = null

    class PopularMealViewHolder(binding: PopularItemBinding):RecyclerView.ViewHolder(binding.root) {
         private val imageMostPopularMeal = binding.imgPopularMealItem


        fun bind(mostPopularMeal: MostPopularMeal) {
            imageMostPopularMeal.load(mostPopularMeal.strMealThumb)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
       return PopularMealViewHolder(
           PopularItemBinding
               .inflate(LayoutInflater.from(parent.context),
               parent,
               false)
       )
    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
        val currentItem  =mealList[position]
        holder.bind(currentItem)

      holder.itemView.setOnClickListener {
          with(onMealClick) { this!!.invoke(currentItem) }
      }

        holder.itemView.setOnLongClickListener {
            onLongMealClick?.invoke(currentItem)
            true
        }


    }

    override fun getItemCount(): Int {
       return mealList.size
    }

    fun setData(meal: List<MostPopularMeal>) {
        this.mealList = meal
        notifyDataSetChanged()
    }
}