package com.example.facebook_org_inkotlin_project.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.*
import com.airbnb.lottie.LottieAnimationView
import com.example.facebook_org_inkotlin_project.R
import com.example.facebook_org_inkotlin_project.`object`.Utils
import com.example.facebook_org_inkotlin_project.model.URLPost
import com.squareup.picasso.Picasso
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.jsoup.nodes.Document

class CreatePostActivity : AppCompatActivity() {

    private lateinit var etNewPost: EditText
    private lateinit var btnPost: Button
    private lateinit var llPreview: LinearLayout
    private lateinit var ivPost: ImageView
    private lateinit var tvTitle: TextView
    private lateinit var tvDescription: TextView
    private lateinit var btnClose: ImageView
    private lateinit var ivRemove : ImageView
    private lateinit var isLoading: LottieAnimationView
    private var isFindLink = false
    private var postPhoto: String = ""
    private var postName: String = ""
    private var postTitle: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_feed_link_post)

        initViews()

    }

    private fun initViews() {
        etNewPost = findViewById(R.id.et_post_text)
        btnPost = findViewById(R.id.btn_post)
        btnPost.isEnabled = false
        btnClose = findViewById(R.id.iv_close)
        btnClose.setOnClickListener { finish() }
        ivPost = findViewById(R.id.iv_post)
        tvTitle = findViewById(R.id.tv_title)
        tvDescription = findViewById(R.id.tv_description)
        llPreview = findViewById(R.id.ll_preview)
        ivRemove = findViewById(R.id.ivRemove)
        isLoading = findViewById(R.id.la_loading)
        ivRemove.setOnClickListener {
            llPreview.visibility = View.GONE
        }
            btnPost.setOnClickListener{
                val link: URLPost = if (llPreview.visibility == View.GONE){
                    URLPost(R.drawable.arab2, "Irina Razmetova", etNewPost.text.toString(),"","","")
                }else{
                    URLPost(R.drawable.arab2, "Irina Razmetova", etNewPost.text.toString(),postPhoto,postTitle, postName)
                }
                sendResultSerializable(link)
        }


        etNewPost.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence, p1: Int, p2: Int, p3: Int) {
                if (!isFindLink && containsLink(s.toString())) {
                    llPreview.visibility = View.VISIBLE
                } else if (!isFindLink) {
                    llPreview.visibility = View.GONE
                }

                btnPost.isEnabled = !(s.toString().isEmpty() && llPreview.visibility == View.GONE)

            }

            override fun afterTextChanged(p0: Editable) {

            }
        })

    }

    private fun sendResultSerializable(link: URLPost) {
        val intent = Intent()
        intent.putExtra("linkResult", link)
        setResult(10, intent)
        finish()
    }

    private fun getElementsJsoup(url: String) {

        Utils.getJsoupData(url)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result: Document ->
                val metaTags = result.getElementsByTag("meta")
                for (element in metaTags) {
                    when {
                        element.attr("property").equals("og:image") -> {
                            Picasso.get().load(element.attr("content")).into(ivPost)
                            postPhoto = element.attr("content")
                        }
                        element.attr("property").equals("og:description") -> {
                            tvDescription.text = element.attr("content")
                            postName = element.attr("content")
                        }
                        element.attr("property").equals("og:title") -> {
                            tvTitle.text = element.attr("content")
                            postTitle = element.attr("content")

                        }
                    }
                }
                isLoading.visibility = View.GONE
            }
    }

    private fun containsLink(input: String): Boolean {
        val parts = input.split(" ")
        for (item in parts) {
            if (!isFindLink && Patterns.WEB_URL.matcher(item).matches()) {
                getElementsJsoup(item)
                isFindLink = true
                return true
            }
        }
        return false
    }
}