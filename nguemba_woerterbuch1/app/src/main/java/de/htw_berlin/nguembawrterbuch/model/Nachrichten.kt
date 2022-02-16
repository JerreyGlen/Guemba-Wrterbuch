package de.htw_berlin.nguembawrterbuch.model

class Nachrichten {

    private var zeit: String = ""
    private var datum: String = ""
    private var username: String = ""
    private var nachricht: String = ""

    constructor(zeit: String, datum: String, username: String, nachricht: String) {
        this.zeit = zeit
        this.datum = datum
        this.username = username
        this.nachricht = nachricht
    }

    fun setZeit(zeit: String){
        this.zeit = zeit
    }
    fun getZeit(): String?{
        return zeit
    }

    fun setDatum(datum: String){
        this.datum = datum
    }
    fun getDatum(): String?{
        return datum
    }

    fun setUsername(username: String){
        this.username = username
    }
    fun getUsername(): String?{
        return username
    }

    fun setNachricht(nachricht: String){
        this.nachricht = nachricht
    }
    fun getNachricht(): String?{
        return nachricht
    }
}