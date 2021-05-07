package com.dynamo.chiragtest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dynamo.chiragtest.R
import com.dynamo.chiragtest.model.RocketData
import kotlinx.android.synthetic.main.item_rocket_list.view.*
import java.lang.StringBuilder

class RocketListAdapter (var rocketList: List<RocketData>, context: OnClickRecipeInterface) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var interfaceRecipeClickHandler: OnClickRecipeInterface? = null

    init {
        interfaceRecipeClickHandler = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_rocket_list, parent, false)
        )

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val repoModel = rocketList.get(position)
        setRocketItemView(holder, repoModel)
    }

    private fun setRocketItemView(
        holder: RecyclerView.ViewHolder,
        repoDto: RocketData?
    ) {
        holder.itemView.setOnClickListener {
            interfaceRecipeClickHandler?.onClick(repoDto)
        }
        repoDto?.apply {
           holder.itemView.txtHeading.text=name
            holder.itemView.txtSubHeading.text=companyName
            rocketImageData.let {
                Glide.with(holder.itemView)
                    .load(it?.get(0))
                    .centerCrop()
                    .into(holder.itemView.recipeImage)
            }



        }

    }

    override fun getItemCount() = rocketList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun dataSetChanged(newValues: List<RocketData>) {
        rocketList = newValues
        notifyDataSetChanged()
    }



    interface OnClickRecipeInterface {
        fun onClick(repoDto: RocketData?)
    }

}