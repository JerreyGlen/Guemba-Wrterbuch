package de.htw_berlin.nguembawrterbuch.model

class NachrichtenPost {

    private var zeit: String = ""
    private var datum: String = ""
    private var nachricht: String = ""
    private var username: String = ""

    constructor(
        zeit: String,
        datum: String,
        nachricht: String,
        username: String
    ) {
        this.zeit = zeit
        this.datum = datum
        this.nachricht = nachricht
        this.username = username
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

    fun setNachricht(nachricht: String){
        this.nachricht = nachricht
    }
    fun getNachricht(): String?{
        return nachricht
    }

    fun setUsername(username: String){
        this.username = username
    }
    fun getUsername(): String?{
        return username
    }


}