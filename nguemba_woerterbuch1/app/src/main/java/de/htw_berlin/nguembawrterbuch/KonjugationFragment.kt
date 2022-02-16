package de.htw_berlin.nguembawrterbuch

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import de.htw_berlin.nguembawrterbuch.databinding.FragmentCopyrightBinding
import de.htw_berlin.nguembawrterbuch.databinding.FragmentKonjugationBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [KonjugationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class KonjugationFragment : Fragment() {
    internal lateinit var binding : FragmentKonjugationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (activity as MainActivity?)?.setActionBarTitle("Konjugation")
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_konjugation,container,false)

        binding.upButton.setOnClickListener {
            binding.scrollkonjugation.fullScroll(5)
        }

        return binding.root
    }
}