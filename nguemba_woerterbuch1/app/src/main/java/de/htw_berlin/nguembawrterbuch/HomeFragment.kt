package de.htw_berlin.nguembawrterbuch

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import de.htw_berlin.nguembawrterbuch.databinding.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    internal lateinit var binding : FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //Change the title bar of the app
        (activity as MainActivity?)?.setActionBarTitle("Nguemba Wörterbuch")
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home,container,false)
      /* binding.lSchVerlaufText.setOnClickListener { view : View ->
       view.findNavController().navigate(R.id.action_homeFragment_to_loginFragment2)
       }*/


        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val mUser = FirebaseAuth.getInstance().currentUser
        if (mUser == null){
            (activity as MainActivity?)?.userNameHeader?.text = "Benutzername"

        }else{

            val refUsers = FirebaseDatabase.getInstance("https://guemba-bb3ff-default-rtdb.europe-west1.firebasedatabase.app")
            refUsers.getReference("Users").child(mUser.uid).child("username").get().addOnSuccessListener {
                if (it.exists()){
                    val usernameRead = it.value
                    (activity as MainActivity?)?.userNameHeader?.text = usernameRead.toString()
                }else{
                    Toast.makeText(requireActivity(), "Fehler bei Übernahme des Benutzernamen.", Toast.LENGTH_LONG).show()
                }
            }.addOnFailureListener {
                Toast.makeText(requireActivity(), "Fehler bei Übernahme des Benutzernamen.", Toast.LENGTH_LONG).show()
            }
        }
    }
}