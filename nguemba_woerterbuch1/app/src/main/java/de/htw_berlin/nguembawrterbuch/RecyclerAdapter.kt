package de.htw_berlin.nguembawrterbuch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import de.htw_berlin.nguembawrterbuch.model.MessageList


class RecyclerAdapter (private val messageList: ArrayList<MessageList>): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_communication, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        val currentItem = messageList[position]
       /* var names: ArrayList<String?> = arrayListOf<String?>()
        var zeits: ArrayList<String?> = arrayListOf<String?>()
        var nachrichts: ArrayList<String?> = arrayListOf<String?>()
        var datums: ArrayList<String?> = arrayListOf<String?>()

        names.add(currentItem.username)
        zeits.add(currentItem.zeit)
        nachrichts.add(currentItem.nachricht)
        datums.add(currentItem.datum)

        for(i in names ){
            holder.name.text = i
        }
        for(i in zeits ){
            holder.zeit.text = i
        }
        for(i in nachrichts ){
            holder.nachricht.text = i
        }
        for(i in datums ){
            holder.textDatum.text = i
        }*/
       holder.name.text = currentItem.username
        holder.zeit.text = currentItem.zeit
        holder.nachricht.text = currentItem.nachricht
        holder.textDatum.text =currentItem.datum

    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var textDatum: TextView
        var name: TextView
        var nachricht: TextView
        var zeit: TextView

        init {
            textDatum = itemView.findViewById(R.id.textDatum)
            name = itemView.findViewById(R.id.name)
            nachricht = itemView.findViewById(R.id.nachricht)
            zeit = itemView.findViewById(R.id.zeit)

            itemView.setOnClickListener{
                val position: Int = adapterPosition

                Toast.makeText(itemView.context, "Nachricht von ${messageList[position].username}", Toast.LENGTH_LONG).show()

            }
        }

    }

}