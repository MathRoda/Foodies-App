package com.example.foodies.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.foodies.databinding.CategoryItemBinding
import com.example.foodies.module.categorymeal.Category

class CategoriesAdapter: RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

     private var categoryList = emptyList<Category>()
     var onItemClick: ((Category) -> Unit)? = null

    class ViewHolder(binding: CategoryItemBinding): RecyclerView.ViewHolder(binding.root) {
        private val imgCategory = binding.imgCategory
        private val tvCategoryTittle = binding.tvCategoryTittle

        fun bind(category: Category) {
            imgCategory.load(category.strCategoryThumb)
            tvCategoryTittle.text = category.strCategory
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(
           CategoryItemBinding
               .inflate(LayoutInflater.from(parent.context),
               parent,
               false)
       )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val currentItem = categoryList[position]
        holder.bind(currentItem)

        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    fun setData(category: List<Category>) {
        this.categoryList = category
        notifyDataSetChanged()
    }
}