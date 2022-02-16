package de.htw_berlin.nguembawrterbuch

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import de.htw_berlin.nguembawrterbuch.adapter.CommunicationRecyclerAdapter
import de.htw_berlin.nguembawrterbuch.databinding.FragmentForumA1Binding
import de.htw_berlin.nguembawrterbuch.databinding.FragmentResultBinding
import de.htw_berlin.nguembawrterbuch.model.MessageList
import de.htw_berlin.nguembawrterbuch.model.Nachrichten
import de.htw_berlin.nguembawrterbuch.model.NachrichtenPost
import de.htw_berlin.nguembawrterbuch.model.data_communication
import de.htw_berlin.nguembawrterbuch.session.preferences
import kotlinx.android.synthetic.main.fragment_forum_a1.*
import kotlinx.android.synthetic.main.fragment_konjugation.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ForumA1Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ForumA1Fragment : Fragment() {

    internal lateinit var binding : FragmentForumA1Binding
    var database: DatabaseReference = FirebaseDatabase.getInstance("https://guemba-bb3ff-default-rtdb.europe-west1.firebasedatabase.app").getReference("Nachrichten")
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null
    var  usernameRead: String = ""
     var zeitRead: String =""
     var datumRead: String = ""
     var nachrichtRead: String = ""

    private lateinit var messageArrayList: ArrayList<MessageList>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity?)?.setActionBarTitle("Nguemba Wörterbuch")
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_forum_a1,container,false)


        messageArrayList = arrayListOf<MessageList>()
        layoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager

        binding.fabSend.setOnClickListener{

            if (binding.sendMessage.text.toString() != ""){

                val message: String = binding.sendMessage.text.toString()
                val sdf = SimpleDateFormat("dd/M/yyyy")
                val currentDate = sdf.format(Date())
                val sdf1 = SimpleDateFormat("hh:mm:ss")
                val currentDate1 = sdf1.format(Date())
                val sdf2 = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
                val currentDate2 = sdf2.format(Date())
                val username =  (activity as MainActivity?)?.userNameHeader?.text

//Send Message in the firebase
                val nachricht = MessageList( currentDate1, currentDate, message, username.toString() )
                database.child(currentDate2).setValue(nachricht).addOnSuccessListener {
                    val a1Fragment = ForumA1Fragment()
                    (activity as MainActivity?)?.makeCurrentFragment(a1Fragment)
                    binding.sendMessage.text.clear()
                    Toast.makeText(requireActivity(), "Nachricht erfolgreich gesendet.", Toast.LENGTH_SHORT).show()

//Show message from the firebase

                    database.child(currentDate2).get().addOnSuccessListener {

                        if (it.exists()){
                            usernameRead = it.child("username").value.toString()
                            zeitRead = it.child("zeit").value.toString()
                            datumRead = it.child("datum").value.toString()
                            nachrichtRead = it.child("nachricht").value.toString()
                            val mess: MessageList = MessageList(zeitRead, datumRead, nachrichtRead, usernameRead)
                            messageArrayList.add(mess)
                            adapter = RecyclerAdapter(messageArrayList)
                            binding.recyclerView.adapter = adapter
                            val a1Fragment = ForumA1Fragment()
                            (activity as MainActivity?)?.makeCurrentFragment(a1Fragment)
                        }else{
                            Toast.makeText(requireActivity(), "Fehler bei Anzeige der Nachrichten.", Toast.LENGTH_SHORT).show()
                        }
                    }.addOnFailureListener {
                        Toast.makeText(requireActivity(), "Fehler bei Anzeige der Nachrichten.", Toast.LENGTH_SHORT).show()
                    }


                    }.addOnFailureListener {

                    Toast.makeText(requireActivity(), "Nachricht konnte nicht gesendet werden.", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(requireActivity(), "Das Eingabefeld ist leer.", Toast.LENGTH_SHORT).show()
            }


        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val sdf = SimpleDateFormat("dd/M")
        val currentDate = sdf.format(Date())
        database.child(currentDate).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){

                    for (messSnapshot in snapshot.children){
                        val mess = messSnapshot.getValue(MessageList::class.java)
                        messageArrayList.add(mess!!)

                        adapter = RecyclerAdapter(messageArrayList)
                        binding.recyclerView.adapter = adapter
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }


}