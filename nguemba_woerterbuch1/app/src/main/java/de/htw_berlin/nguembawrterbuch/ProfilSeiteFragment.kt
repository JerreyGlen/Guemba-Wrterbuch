package de.htw_berlin.nguembawrterbuch

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.DataBindingUtil
import com.firebase.ui.auth.ui.InvisibleActivityBase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import de.htw_berlin.nguembawrterbuch.databinding.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfilSeiteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfilSeiteFragment : Fragment() {

    internal lateinit var binding : FragmentProfilSeiteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        (activity as MainActivity?)?.setActionBarTitle("Profil")
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profil_seite,container,false)
        binding.canceln.visibility = View.INVISIBLE
        binding.editUserName.visibility = View.INVISIBLE
        binding.buttonEditUserName.visibility = View.INVISIBLE
       binding.passEdit.setOnClickListener {
           val passvergessenFragment = PassvergessenFragment()
           (activity as MainActivity?)?.makeCurrentFragment(passvergessenFragment)
       }
        binding.info.setOnClickListener {
            Toast.makeText(requireActivity(), "Um eine Änderung Ihr Passwort oder Nutzernamen durchführen zu können, klicken Sie bitte auf" +
                    " das betroffene Element.\n Eine Änderung der E-Mail Adresse ist hier nicht möglich. Kontaktieren Sie bitte den Support.", Toast.LENGTH_LONG).show()
        }
       binding.textNav.setOnClickListener {
           binding.canceln.visibility = View.VISIBLE
           binding.editUserName.visibility = View.VISIBLE
           binding.buttonEditUserName.visibility = View.VISIBLE
       }
        binding.canceln.setOnClickListener {
            val profilSeiteFragment = ProfilSeiteFragment()
            (activity as MainActivity?)?.makeCurrentFragment(profilSeiteFragment)
        }
        binding.buttonEditUserName.setOnClickListener {
            val newName = binding.editUserName.text.toString()
            val mUser = FirebaseAuth.getInstance().currentUser
            if(newName != ""){
                val refUsers = FirebaseDatabase.getInstance("https://guemba-bb3ff-default-rtdb.europe-west1.firebasedatabase.app").getReference("Users")
                val user = mapOf<String, String>(
                    "username" to newName
                )
                if (mUser != null){
                    refUsers.child(mUser.uid).updateChildren(user).addOnSuccessListener {
                        binding.editUserName.text.clear()
                      //  Toast.makeText(requireActivity(), "Der Benutzername wurde erfolgreich geändert.", Toast.LENGTH_LONG).show()
                    }.addOnFailureListener {
                        Toast.makeText(requireActivity(), "Ein Fehler ist bei der Änderung des Benutzernamens aufgetreten", Toast.LENGTH_SHORT).show()
                    }
                }


            }else{
                Toast.makeText(requireActivity(), "Ein leerer Benutzername wird leider nicht akzeptiert.", Toast.LENGTH_SHORT).show()
            }
            val profilSeiteFragment = ProfilSeiteFragment()
            (activity as MainActivity?)?.makeCurrentFragment(profilSeiteFragment)
        }

        binding.changePicture.setOnClickListener {

            var dialogFragment = DialogFragment()
            //Show the fragment like a dialog
          dialogFragment.show(childFragmentManager, "dialog")

        }

        return binding.root
    }



    override fun onStart() {
        super.onStart()
        val mUser = FirebaseAuth.getInstance().currentUser
        if (mUser == null){
            (activity as MainActivity?)?.userNameHeader?.text = "Benutzername"

        }else{

            val refUsers = FirebaseDatabase.getInstance("https://guemba-bb3ff-default-rtdb.europe-west1.firebasedatabase.app")
            refUsers.getReference("Users").child(mUser.uid).get().addOnSuccessListener {
                if (it.exists()){
                    val usernameRead = it.child("username").value
                    val emailRead = it.child("email").value
                   binding.textNav.text = usernameRead.toString()
                    binding.emailEdit.text = emailRead.toString()
                }else{
                    Toast.makeText(requireActivity(), "Fehler bei Übernahme des Benutzernamen und der E-Mail Adresse.", Toast.LENGTH_LONG).show()
                }
            }.addOnFailureListener {
                Toast.makeText(requireActivity(), "Fehler bei Übernahme des Benutzernamen und der E-Mail Adresse.", Toast.LENGTH_LONG).show()
            }
        }
    }
}