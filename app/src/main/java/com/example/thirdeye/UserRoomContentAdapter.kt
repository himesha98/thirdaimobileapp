package com.example.thirdeye

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserRoomContentAdapter(val context: Context, val contentList:ArrayList<UerRoomEduContentModel>):
    RecyclerView.Adapter<UserRoomContentAdapter.UserRoomViewHolder>() {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserRoomViewHolder {

            val view: View =
                LayoutInflater.from(context).inflate(R.layout.userroomcontentlayout, parent, false)
            return UserRoomViewHolder(view)
        }

        override fun onBindViewHolder(holder: UserRoomViewHolder, position: Int) {

            val currentContent = contentList[position]
            holder.lessonName.text = currentContent.columnone
            holder.subjectName.text = currentContent.columntwo
            holder.itemView.setOnClickListener {
                //start play
            }
        }

        override fun getItemCount(): Int {

            return contentList.size
        }

        class UserRoomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            val lessonName = itemView.findViewById<TextView>(R.id.lesson)
            val subjectName = itemView.findViewById<TextView>(R.id.subjectName)
        }

}