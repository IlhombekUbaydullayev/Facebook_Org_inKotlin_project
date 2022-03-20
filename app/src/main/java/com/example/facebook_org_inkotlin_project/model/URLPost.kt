package com.example.facebook_org_inkotlin_project.model

import java.io.Serializable

data class URLPost(
    var profile: Int,
    var fullName: String,
    var link: String,
    var imageUrl: String,
    var linkName: String,
    var linkTitle: String
) : Serializable