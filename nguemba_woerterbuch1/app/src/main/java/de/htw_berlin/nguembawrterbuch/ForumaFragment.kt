package de.htw_berlin.nguembawrterbuch

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.firebase.database.*
import de.htw_berlin.nguembawrterbuch.databinding.*
import de.htw_berlin.nguembawrterbuch.model.MessageList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ForumaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ForumaFragment : Fragment() {

    internal lateinit var binding : FragmentForumaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (activity as MainActivity?)?.setActionBarTitle("Forum")
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_foruma,container,false)
        binding.a1.setOnClickListener {

            val a1Fragment = ForumA1Fragment()
            (activity as MainActivity?)?.makeCurrentFragment(a1Fragment)
        }
        return binding.root
    }
}