package de.htw_berlin.nguembawrterbuch

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import de.htw_berlin.nguembawrterbuch.databinding.FragmentLoginBinding
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import de.htw_berlin.nguembawrterbuch.model.Users


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {

    private lateinit var mAuth: FirebaseAuth
    internal lateinit var binding : FragmentLoginBinding
  //  private lateinit var refUsers: DatabaseReference
    internal var user: Users? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (activity as MainActivity?)?.setActionBarTitle("Login")
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login,container,false)
        binding.passVergessen.setOnClickListener {
            val passvergessenFragment = PassvergessenFragment()
            (activity as MainActivity?)?.makeCurrentFragment(passvergessenFragment)
        }
        binding.neuAmeldenButton.setOnClickListener {
            val neuanmeldenFragment = NeuanmeldenFragment()
            (activity as MainActivity?)?.makeCurrentFragment(neuanmeldenFragment)
        }

        mAuth=FirebaseAuth.getInstance()
        binding.registrierenButton.setOnClickListener {
            loginUser()
        }
        return binding.root
    }

    private fun loginUser() {

        val email: String = binding.editTextTextEmailAddress.text.toString()
        val password: String = binding.editTextTextPassword.text.toString()

        if (email== ""){
            Toast.makeText(requireActivity(), "Geben Sie Ihre Mailadresse ein.", Toast.LENGTH_LONG).show()

        }else if (password== ""){
            Toast.makeText(requireActivity(), "Geben Sie Ihr Passwort ein.", Toast.LENGTH_LONG).show()
        }else{

            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener{ task ->
                    if (task.isSuccessful){

                         verifyEmail()
                    }else{
                        Toast.makeText(requireActivity(), "Error Message: "+ task.exception!!.message.toString(), Toast.LENGTH_LONG).show()
                    }

                }
        }
    }

    fun verifyEmail (){
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        val vemail = firebaseUser?.isEmailVerified
        var isOk = true

        if (vemail!!){
            Toast.makeText(requireActivity(), "Das Einloggen war erfolgreich.", Toast.LENGTH_LONG).show()
            val homeFragment = HomeFragment()
            (activity as MainActivity?)?.makeCurrentFragment(homeFragment)
        }else{
            Toast.makeText(requireActivity(), "Validieren Sie bitte Ihre E-Mail Adresse.", Toast.LENGTH_LONG).show()
            mAuth.signOut()
        }
    }
}