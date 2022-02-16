package de.htw_berlin.nguembawrterbuch

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import de.htw_berlin.nguembawrterbuch.databinding.FragmentCopyrightBinding
import de.htw_berlin.nguembawrterbuch.databinding.FragmentForumBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ForumFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ForumFragment : Fragment() {

    var firebaseUser: FirebaseUser?=null
    internal lateinit var binding : FragmentForumBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_forum,container,false)
        (activity as MainActivity?)?.setActionBarTitle("Forum")

        binding.eilogenForum.setOnClickListener {
            val loginFragment = LoginFragment()
            (activity as MainActivity?)?.makeCurrentFragment(loginFragment)
        }

        binding.registrierenForum.setOnClickListener {
            val neuanmeldenFragment = NeuanmeldenFragment()
            (activity as MainActivity?)?.makeCurrentFragment(neuanmeldenFragment)
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        firebaseUser = FirebaseAuth.getInstance().currentUser

        if (firebaseUser != null){

            val forumaFragment = ForumaFragment()
            (activity as MainActivity?)?.makeCurrentFragment(forumaFragment)

        }
    }
}