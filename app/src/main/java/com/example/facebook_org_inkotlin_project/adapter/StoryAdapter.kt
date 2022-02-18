package com.example.facebook_org_inkotlin_project.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.facebook_org_inkotlin_project.R
import com.example.facebook_org_inkotlin_project.model.Story
import com.google.android.material.imageview.ShapeableImageView

class StoryAdapter(var context: Context, var items : ArrayList<Story>) :
RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private var TYPE_ITEM_CREATE = 0
    private var  TYPE_ITEM_STORY = 1



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_ITEM_STORY) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_story_view,parent,false)
            return StoryViewHolder(view)
        }
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_story_create_view,parent,false)
        return CreateViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val story = items[position]

        if (holder is CreateViewHolder) {

        }

        if (holder is StoryViewHolder) {
            var iv_background = holder.iv_background
            var iv_profile = holder.iv_profile
            var tv_fullName = holder.tv_fullname

            iv_background.setImageResource(story.profile)
            iv_profile.setImageResource(story.profile)
            tv_fullName.text = story.fullname
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (items[position].isAvailable) {
           return TYPE_ITEM_CREATE
        }
        return TYPE_ITEM_STORY
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class CreateViewHolder(view: View) : RecyclerView.ViewHolder(view) {

}

class StoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var iv_background: ShapeableImageView
    var iv_profile: ShapeableImageView
    var tv_fullname:TextView

    init {
        iv_background = view.findViewById(R.id.iv_backgrounds)
        iv_profile = view.findViewById(R.id.iv_profile)
        tv_fullname = view.findViewById(R.id.tv_fullName)
    }
}
