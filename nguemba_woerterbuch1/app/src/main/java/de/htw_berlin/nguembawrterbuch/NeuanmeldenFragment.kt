package de.htw_berlin.nguembawrterbuch


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import de.htw_berlin.nguembawrterbuch.databinding.FragmentForumBinding
import de.htw_berlin.nguembawrterbuch.databinding.FragmentNeuanmeldenBinding
import de.htw_berlin.nguembawrterbuch.model.Users
import java.util.*
import kotlin.collections.HashMap

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NeuanmeldenFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NeuanmeldenFragment : Fragment() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var refUsers: DatabaseReference
    private var firebaseUserId: String =""
    internal lateinit var binding : FragmentNeuanmeldenBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_neuanmelden,container,false)
        (activity as MainActivity?)?.setActionBarTitle("Registrieren")
     //   binding.registrierenButton.isEnabled = false

        mAuth=FirebaseAuth.getInstance()
        binding.registrierenButton.setOnClickListener{
              registerUser()
        }
        return binding.root
    }



    private fun registerUser() {

        val username: String = binding.editTextTextUsername.text.toString()
        val email: String = binding.editTextTextEmailAddress.text.toString()
        val password: String = binding.editTextTextPassword.text.toString()
        val profile = "https://firebasestorage.googleapis.com/v0/b/guemba-bb3ff.appspot.com/o/profile.png?alt=media&token=6441309a-1f45-4576-af01-69e7dc693c29"
        val passwordwieder: String = binding.editTextTextPassworde.text.toString()

        if (username==""){
            Toast.makeText(requireActivity(), "Geben Sie Ihren Benutzernamen ein.", Toast.LENGTH_LONG).show()

        }else if (email== ""){
            Toast.makeText(requireActivity(), "Geben Sie Ihre Mailadresse ein.", Toast.LENGTH_LONG).show()

        }else if (password== ""){
            Toast.makeText(requireActivity(), "Geben Sie Ihr Passwort ein.", Toast.LENGTH_LONG).show()
        }else if(passwordwieder=="")
        {
            Toast.makeText(requireActivity(), "Geben Sie Ihr Passwort wieder ein.", Toast.LENGTH_LONG).show()
        }
        else if(passwordwieder!=password)
        {
            Toast.makeText(requireActivity(), "Die Passworte stimmen nicht überein.", Toast.LENGTH_LONG).show()
        }
        else{
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener{ task ->
                    if (task.isSuccessful)
                    {

                        firebaseUserId = mAuth.currentUser!!.uid

                        refUsers = FirebaseDatabase.getInstance("https://guemba-bb3ff-default-rtdb.europe-west1.firebasedatabase.app").getReference("Users")
                        val user = Users(firebaseUserId, username, profile, email)

                        refUsers.child(firebaseUserId)
                                .setValue(user)
                                .addOnSuccessListener {
                                 //Send an email verification
                                    checkEmail()

                          binding.editTextTextUsername.text.clear()
                          binding.editTextTextEmailAddress.text.clear()
                          binding.editTextTextPassword.text.clear()
                          binding.editTextTextPassworde.text.clear()
                        }.addOnFailureListener{

                            Toast.makeText(
                                requireActivity(),
                                "Ein Fehler ist bei der Registrierung aufgetreten.",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                      /*  val userHashMap= HashMap<String, Any>()
                        userHashMap["uid"] = firebaseUserId
                        userHashMap["username"] = username
                        userHashMap["email"] = email
                        userHashMap["profile"] = "https://firebasestorage.googleapis.com/v0/b/guemba-bb3ff.appspot.com/o/profile.png?alt=media&token=6441309a-1f45-4576-af01-69e7dc693c29"
                     /*   userHashMap["status"] = "offline"
                        userHashMap["search"] = username.lowercase(Locale.getDefault())
                        userHashMap["facebook"] = "https://m.facebook.com"
                        userHashMap["instagram"] = "https://m.instagram.com"
                        userHashMap["website"] = "https://www.google.com"*/

                        refUsers.updateChildren(userHashMap)
                            .addOnCompleteListener{ task ->

                                if (task.isSuccessful) {
                                    Toast.makeText(
                                        requireActivity(),
                                        "Die Registrierung war erfolgreich.",
                                        Toast.LENGTH_LONG
                                    ).show()

                                    //
                                }

                            }*/

                    }
                    else
                    {
                        Toast.makeText(requireActivity(), "Error Message: "+ task.exception!!.message.toString(), Toast.LENGTH_LONG).show()
                    }

                }
        }

    }

    fun checkEmail(){
        val firebaseUser: FirebaseUser? = mAuth.currentUser
        firebaseUser?.sendEmailVerification()?.addOnCompleteListener { task ->
            if (task.isSuccessful){
                Toast.makeText(requireActivity(), "Die E-Mail Verifikation wurde gesendet.", Toast.LENGTH_SHORT).show()
                mAuth.signOut()
                val loginFragment = LoginFragment()
                (activity as MainActivity?)?.makeCurrentFragment(loginFragment)
            }else{
                Toast.makeText(requireActivity(), "Ein Fehler ist aufgetreten.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}