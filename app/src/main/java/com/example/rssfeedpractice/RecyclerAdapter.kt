package com.example.rssfeedpractice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.question_item.view.*

class RecyclerAdapter (val QuestionsList: ArrayList<Question>): RecyclerView.Adapter<RecyclerAdapter.itemViewHolder>() {
    class itemViewHolder (itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itemViewHolder {
        return itemViewHolder(
            LayoutInflater.from(parent.context).inflate(
            R.layout.question_item,
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: itemViewHolder, position: Int) {
        val question = QuestionsList[position].qestion
        holder.itemView.apply {
            tvQuestion.text=question
        }
    }

    override fun getItemCount(): Int = QuestionsList.size
}