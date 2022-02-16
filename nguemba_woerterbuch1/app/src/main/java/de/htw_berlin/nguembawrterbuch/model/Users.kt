package de.htw_berlin.nguembawrterbuch.model

class Users {

    private var uid: String = ""
    private var username: String = ""
    private var profile: String = ""
    private var email: String = ""


    constructor(uid: String, username: String, profile: String, email: String) {
        this.uid = uid
        this.username = username
        this.profile = profile
        this.email = email
    }

    constructor()

    fun setUID(uid: String){
        this.uid = uid
    }
    fun getUID(): String?{
        return uid
    }

    fun setUsername(username: String){
        this.username = username
    }
    fun getUsername(): String?{
        return username
    }

    fun setEmail(email: String){
        this.email = email
    }
    fun getEmail(): String?{
        return email
    }

    fun setProfile(profile: String){
        this.profile = profile
    }
    fun getProfile(): String?{
        return profile
    }


}