package com.example.foodies.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.foodies.databinding.MealsItemBinding
import com.example.foodies.module.mostpopular.MostPopularMeal
import com.example.foodies.module.randommeal.Meal

class FavoritesMealsAdapter: RecyclerView.Adapter<FavoritesMealsAdapter.ViewHolder>() {

    var onMealClick: ((Meal) -> Unit)? = null

    class ViewHolder(val binding: MealsItemBinding): RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<Meal>(){
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }

    }

     val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(
           MealsItemBinding.inflate(LayoutInflater.from(parent.context),
           parent,
           false
           )
       )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val currentItem = differ.currentList[position]
        holder.binding.imgMeal.load(currentItem.strMealThumb)
        holder.binding.tvMealName.text = currentItem.strMeal

        holder.itemView.setOnClickListener {
            with(onMealClick) {this!!.invoke(currentItem)}
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}