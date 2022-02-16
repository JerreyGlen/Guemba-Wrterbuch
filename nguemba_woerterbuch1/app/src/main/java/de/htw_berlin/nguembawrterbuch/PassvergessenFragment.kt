package de.htw_berlin.nguembawrterbuch

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import de.htw_berlin.nguembawrterbuch.databinding.FragmentCopyrightBinding
import de.htw_berlin.nguembawrterbuch.databinding.FragmentPassvergessenBinding
import com.google.firebase.auth.FirebaseAuth

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PassvergessenFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PassvergessenFragment : Fragment() {
    internal lateinit var binding : FragmentPassvergessenBinding
    private var firebaseAuth: FirebaseAuth? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (activity as MainActivity?)?.setActionBarTitle("Passwort")
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_passvergessen,container,false)
        firebaseAuth = FirebaseAuth.getInstance()
        binding.weiterPasswort.setOnClickListener {
           reset()

        }
        binding.buttonzurCk.setOnClickListener {
            val loginFragment = LoginFragment()
            (activity as MainActivity?)?.makeCurrentFragment(loginFragment)
        }
        return binding.root
    }

    fun reset (){
        var emailR = binding.resetpassword.text.toString()
        if (emailR == ""){
            Toast.makeText(requireActivity(), "Geben Sie Ihre Mailadresse ein.", Toast.LENGTH_LONG).show()
        }else{
            firebaseAuth?.sendPasswordResetEmail(emailR)?.addOnCompleteListener { task ->
                if (task.isSuccessful){
                    Toast.makeText(requireActivity(), "Die E-Mail zum Paswort Zurücksetzen wurde gesendet.", Toast.LENGTH_LONG).show()
                    val loginFragment = LoginFragment()
                    (activity as MainActivity?)?.makeCurrentFragment(loginFragment)
                } else{
                    Toast.makeText(requireActivity(), "Die E-Mail zum Paswort Zurücksetzen wurde nicht gesendet.", Toast.LENGTH_LONG).show()
                }
            }
        }

    }
}