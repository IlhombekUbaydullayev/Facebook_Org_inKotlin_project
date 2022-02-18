package com.example.facebook_org_inkotlin_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.facebook_org_inkotlin_project.adapter.FeedAdapter
import com.example.facebook_org_inkotlin_project.model.Feed

import com.example.facebook_org_inkotlin_project.model.Post
import com.example.facebook_org_inkotlin_project.model.Story

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this,1)

        refreshAdapter(getAllFeeds())
    }

    private fun refreshAdapter(feeds: ArrayList<Feed>) {
        val adapter = FeedAdapter(this,feeds)
        recyclerView.adapter = adapter
    }

    private fun getAllFeeds(): ArrayList<Feed> {
        val stories: ArrayList<Story> = ArrayList()
        stories.add(Story(R.drawable.img12,"Natali Juelia",true))
        stories.add(Story(R.drawable.arab1,"Jorj Burj"))
        stories.add(Story(R.drawable.arab2,"Alex Shfirman"))
        stories.add(Story(R.drawable.img11,"Maxs ....."))
        stories.add(Story(R.drawable.arab1,"Googosha stf"))
        stories.add(Story(R.drawable.img11,"Salim Bekjanov"))
        stories.add(Story(R.drawable.arab2,"Laura Briefli"))
        stories.add(Story(R.drawable.arab1,"Mixail Steyr"))

        val feeds: ArrayList<Feed> = ArrayList()

        feeds.add(Feed())
        feeds.add(Feed(stories))


        feeds.add(Feed(Post(R.drawable.img10,"Stefan ZZZZZZZZZ she is beautiful girl",R.drawable.show,0,0,0,0,3)))
        feeds.add(Feed(Post(R.drawable.img9,"Alex Brefich 98969406",R.drawable.livingroom1,R.drawable.livingroom3,R.drawable.livingroom4,R.drawable.livingroom6,R.drawable.livin7,4)))
        feeds.add(Feed(Post(R.drawable.img11,"Laura Sea she is beautiful girl",R.drawable.livingroom8,R.drawable.livingroom9,R.drawable.livingroom10,R.drawable.livingroom11,R.drawable.livingroom12,4)))
        feeds.add(Feed(Post(R.drawable.img1,"Matilda",R.drawable.img11)))
        feeds.add(Feed(Post(R.drawable.arab1,"Izolda",R.drawable.arab1)))
        feeds.add(Feed(Post(R.drawable.arab2,"Fabrigo",R.drawable.arab2)))
        return feeds
    }
}

