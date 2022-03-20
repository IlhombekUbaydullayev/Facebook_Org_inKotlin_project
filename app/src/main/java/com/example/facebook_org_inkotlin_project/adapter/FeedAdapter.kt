package com.example.facebook_org_inkotlin_project.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.facebook_org_inkotlin_project.R
import com.example.facebook_org_inkotlin_project.activity.MainActivity
import com.example.facebook_org_inkotlin_project.model.Feed
import com.example.facebook_org_inkotlin_project.model.PostSecond
import com.example.facebook_org_inkotlin_project.model.Story
import com.example.facebook_org_inkotlin_project.model.URLPost
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso

class FeedAdapter(var context: MainActivity, var items : ArrayList<Feed>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var TYPE_ITEM_HEAD = 0
    private var  TYPE_ITEM_STORY = 1
    private var TYPE_ITEM_POST = 2
    private var  TYPE_ITEM_ROUNDED_STORY = 3
    private var TYPE_ITEM_POST_SECOND = 4
    private var TYPE_ITEM_CREATE_POST = 5

    fun addItem(link: URLPost) {
        items.add(2,Feed(link))
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        var feed = items[position]

        return when {
            feed.isHeader -> TYPE_ITEM_HEAD

            feed.stories.size>0 -> TYPE_ITEM_STORY

            feed.postSecond != null -> TYPE_ITEM_ROUNDED_STORY

            feed.postMoreImages != null -> TYPE_ITEM_POST_SECOND

            feed.urlPost != null -> TYPE_ITEM_CREATE_POST

            else -> TYPE_ITEM_POST
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TYPE_ITEM_HEAD -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_feed_head,parent,false)
                return HeadViewHolder(context,view)
            }
            TYPE_ITEM_STORY -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_feed_story,parent,false)
                return StoryViewHolders(context,view)
            }
            TYPE_ITEM_ROUNDED_STORY -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_feed_post_profile,parent,false)
                return StoryRoundedViewHolders(context,view)
            }
            TYPE_ITEM_CREATE_POST -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_feed_link,parent,false)
                return LinkPostViewHolder(view)
            }
            TYPE_ITEM_POST_SECOND -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_feed_post_second,parent,false)
                return StorySecondViewHolder(context,view)
            }
            else -> {
                val view =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_feed_post, parent, false)
                return PostViewHolder(context, view)
            }
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val feed = items[position]

        if (holder is HeadViewHolder) {
            var view  = holder.view
            var b_open_link = holder.b_open_link
            b_open_link.setOnClickListener {
                context.openCreatePostActivity()
            }
        }

        if (holder is StorySecondViewHolder) {
            var iv_photo_post = holder.iv_photo_post
            var iv_photo_post2 = holder.iv_photo_post2
            var iv_photo_post3 = holder.iv_photo_post3
            var iv_photo_post4 = holder.iv_photo_post4
            var iv_photo_post5 = holder.iv_photo_post5
            var iv_profile_post = holder.iv_profile_post
            var tv_fullName_post = holder.tv_fullName_post

            iv_photo_post.setImageResource(feed.postMoreImages!!.photo)
            iv_photo_post2.setImageResource(feed.postMoreImages!!.photo2)
            iv_photo_post3.setImageResource(feed.postMoreImages!!.photo3)
            iv_photo_post4.setImageResource(feed.postMoreImages!!.photo4)
            iv_photo_post5.setImageResource(feed.postMoreImages!!.photo5)
            iv_profile_post.setImageResource(feed.postMoreImages!!.profile)
            tv_fullName_post.text = feed.postMoreImages!!.fullName
        }

        if (holder is StoryRoundedViewHolders) {
            var profile = holder.iv_profile
            var iv_photo = holder.iv_photo
            var tv_fullname = holder.tv_fullName
            profile.setImageResource(feed.postSecond!!.profile)
            iv_photo.setImageResource(feed.postSecond!!.photo)
            tv_fullname.text = feed.postSecond!!.fullName
        }

        if (holder is StoryViewHolders) {
            var recyclerView = holder.recyclerView
            refreshAdapter(feed.stories,recyclerView)
        }

        if (holder is PostViewHolder) {
            var profile = holder.iv_profile
            var iv_photo = holder.iv_photo
            var tv_fullname = holder.tv_fullName
            profile.setImageResource(feed.post!!.profile)
            iv_photo.setImageResource(feed.post!!.photo)
            tv_fullname.text = feed.post!!.fullName
        }

        if (holder is LinkPostViewHolder) {
            items[position].urlPost?.let { holder.bind(it) }
        }
    }


    private fun refreshAdapter(stories: ArrayList<Story>, recyclerView: RecyclerView) {

        val adapter = StoryAdapter(context,stories)
        recyclerView.adapter = adapter
    }

    override fun getItemCount(): Int {
        return items.size
    }
    inner class LinkPostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val profile: ShapeableImageView = view.findViewById(R.id.iv_profile)
        private val fullName: TextView = view.findViewById(R.id.tv_fullName)
        private val link: TextView = view.findViewById(R.id.tv_link)
        private val ivPhoto: ImageView = view.findViewById(R.id.iv_photo)
        private val siteName: TextView = view.findViewById(R.id.tv_siteName)
        private val siteInfo: TextView = view.findViewById(R.id.tv_linkInfo)

        fun bind(item: URLPost) {
            profile.setImageResource(item.profile)
            fullName.text = item.fullName
            link.text = item.link
            if (item.imageUrl == "") {
                ivPhoto.visibility = View.GONE
            } else {
                ivPhoto.visibility = View.VISIBLE
                Picasso.get().load(item.imageUrl).into(ivPhoto)
            }

            if (item.linkName == "") {
                siteName.visibility = View.GONE
            } else {
                siteName.visibility = View.VISIBLE
                siteName.text = item.linkName
            }

            if (item.linkTitle == "") {
                siteInfo.visibility = View.GONE
            } else {
                siteInfo.visibility = View.VISIBLE
                siteInfo.text = item.linkTitle
            }

        }

    }
   inner class HeadViewHolder(context: Context,var view: View):RecyclerView.ViewHolder(view) {
        var b_open_link : Button
        init {
            b_open_link = view.findViewById(R.id.b_open_link)

        }
    }
}



class StorySecondViewHolder(context: Context, view: View) : RecyclerView.ViewHolder(view) {
    var iv_photo_post : ImageView
    var iv_photo_post2 : ImageView
    var iv_photo_post3 : ImageView
    var iv_photo_post4 : ImageView
    var iv_photo_post5 : ImageView
    var iv_profile_post : ImageView
    var tv_fullName_post : TextView

    init {
        iv_photo_post = view.findViewById(R.id.iv_photo_post)
        iv_photo_post2 = view.findViewById(R.id.iv_photo_post2)
        iv_photo_post3 = view.findViewById(R.id.iv_photo_post3)
        iv_photo_post4 = view.findViewById(R.id.iv_photo_post4)
        iv_photo_post5 = view.findViewById(R.id.iv_photo_post5)
        iv_profile_post = view.findViewById(R.id.iv_profile_post)
        tv_fullName_post = view.findViewById(R.id.tv_fullName_post)
    }
}

class StoryRoundedViewHolders(context: Context, view: View) : RecyclerView.ViewHolder(view) {
    var iv_profile:ShapeableImageView
    var iv_photo:ImageView
    var tv_fullName:TextView

    init {
        iv_profile = view.findViewById(R.id.iv_profile_post)
        iv_photo = view.findViewById(R.id.iv_photo_post)
        tv_fullName = view.findViewById(R.id.tv_fullName_post)
    }
}

class PostViewHolder(var context: Context,view: View):RecyclerView.ViewHolder(view){
    var iv_profile:ShapeableImageView
    var iv_photo:ImageView
    var tv_fullName:TextView

    init {
        iv_profile = view.findViewById(R.id.iv_profile_post)
        iv_photo = view.findViewById(R.id.iv_photo_post)
        tv_fullName = view.findViewById(R.id.tv_fullName_post)
    }

}

class StoryViewHolders(context: Context,view:View) : RecyclerView.ViewHolder(view){
    var recyclerView: RecyclerView

    init {
        recyclerView = view.findViewById(R.id.recyclerView)
        val manager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        recyclerView.layoutManager = manager
    }

}


