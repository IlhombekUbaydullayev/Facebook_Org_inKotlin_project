package com.example.facebook_org_inkotlin_project.model

class Feed {
    var isHeader:Boolean = false
    var post : Post? = null
    var postSecond : PostSecond? = null
    var postMoreImages : PostMoreImages? = null
    var urlPost : URLPost? = null
    var stories: ArrayList<Story> = ArrayList()

    constructor() {
        this.isHeader = true
    }

    constructor(post: Post) {
        this.post = post
        this.isHeader = false
    }
    constructor(urlPost: URLPost) {
        this.urlPost = urlPost
        this.isHeader = false
    }

    constructor(postSecond: PostSecond) {
        this.postSecond = postSecond
        this.isHeader = false
    }

    constructor(postMoreImages: PostMoreImages) {
        this.postMoreImages = postMoreImages
        this.isHeader = false
    }

    constructor(stories : ArrayList<Story>) {
        this.stories = stories
        this.isHeader = false
    }
}