package com.example.facebook_org_inkotlin_project.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.facebook_org_inkotlin_project.R
import com.example.facebook_org_inkotlin_project.adapter.FeedAdapter
import com.example.facebook_org_inkotlin_project.model.*

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FeedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        refreshAdapter(getAllFeeds())
    }


    private fun refreshAdapter(feeds: ArrayList<Feed>) {
        adapter = FeedAdapter(this,feeds)
        recyclerView.adapter = adapter

    }

    fun openCreatePostActivity() {
        val intent = Intent(this, CreatePostActivity::class.java)
        launcher.launch(intent)
    }


    private val launcher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback<ActivityResult> {
            if (it.resultCode == 10) {
                val link = it.data!!.getSerializableExtra("linkResult") as URLPost
                adapter.addItem(link)
            }
        }
    )

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
        feeds.add(Feed(PostSecond(R.drawable.livingroom9, "Saieddw", R.drawable.livingroom9)))

        feeds.add(Feed(PostMoreImages(R.drawable.img9,"Alex Brefich 98969406",
            R.drawable.livingroom1,
            R.drawable.livingroom3,
            R.drawable.livingroom4,
            R.drawable.livingroom6,
            R.drawable.livin7)))
        feeds.add(Feed(Post(R.drawable.img1,"Matilda", R.drawable.img11)))
        feeds.add(Feed(Post(R.drawable.arab1,"Izolda", R.drawable.arab1)))
        feeds.add(Feed(Post(R.drawable.arab2,"Fabrigo", R.drawable.arab2)))

        return feeds
    }
}

