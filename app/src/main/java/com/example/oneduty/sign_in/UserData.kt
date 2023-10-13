package com.example.oneduty.sign_in

data class UserData (
    var name: String = "",
    var role: String = "",
    var groupId: String? = "",
    var userId: String = "",
    var profilePictureUrl: String? = ""
)