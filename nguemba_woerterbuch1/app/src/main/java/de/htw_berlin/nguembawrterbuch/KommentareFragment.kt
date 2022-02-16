package de.htw_berlin.nguembawrterbuch

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import de.htw_berlin.nguembawrterbuch.databinding.FragmentCopyrightBinding
import de.htw_berlin.nguembawrterbuch.databinding.FragmentKommentareBinding
import android.widget.EditText
import android.widget.TextView
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import java.lang.Exception

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [KommentareFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class KommentareFragment : Fragment() {


    internal lateinit var binding : FragmentKommentareBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (activity as MainActivity?)?.setActionBarTitle("Kommentare")
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_kommentare,container,false)
        binding.sendKommentarButton.setOnClickListener {

            val message = binding.editTextTextMultiLine.text.toString().trim()
            val titel = binding.editTextTextMultiLineTitel.text.toString().trim()
            sendEmail(message, titel)
        }
        return binding.root
    }

    fun sendEmail (message: String, subject: String){

        val recipient = "orocksohlewis23@gmail.com"
        val mIntent = Intent(Intent.ACTION_SEND)
        mIntent.data = Uri.parse("mailto:")
        mIntent.type = "text/plain"
        mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
        mIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        mIntent.putExtra(Intent.EXTRA_TEXT, message)

        try {
            startActivity(Intent.createChooser(mIntent,"Choose Email Client..."))
        }catch (e: Exception){
            Toast.makeText(requireActivity(), e.message, Toast.LENGTH_LONG).show()
        }
    }
}