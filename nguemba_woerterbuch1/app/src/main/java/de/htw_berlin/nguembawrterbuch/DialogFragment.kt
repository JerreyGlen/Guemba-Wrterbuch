package de.htw_berlin.nguembawrterbuch

import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import coil.load
import coil.transform.CircleCropTransformation
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_dialog.*
//Gibt Zugriff auf alle Views Elemente der Datei XML
import kotlinx.android.synthetic.main.fragment_dialog.view.*
import kotlinx.android.synthetic.main.fragment_profil_seite.view.*
import java.lang.Exception
import java.util.jar.Manifest



class DialogFragment: DialogFragment() {


    private val CameraRequestCode =1
    private val GalleryRequestCode=2
    lateinit var  rootView: View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

         rootView = inflater.inflate(R.layout.fragment_dialog, container, false)

        rootView.cancelButton.setOnClickListener {
            dismiss()
        }
        rootView.submitButton.setOnClickListener {
            val selectID = ratingRadioGroup.checkedRadioButtonId
            val radio = rootView.findViewById<RadioButton>(selectID)

            val ratingResult = radio.text.toString()



            if (radio == rootView.ratingRadioButton1){

                Toast.makeText(context, "Ihre Auswahl: $ratingResult ", Toast.LENGTH_LONG).show()
                galleryCheckPermission()
            }else if(radio == rootView.ratingRadioButton2){
                Toast.makeText(context, "Ihre Auswahl: $ratingResult \n\n" +
                        "Stellen Sie sicher, dass Sie die erforderlichen Berechtigungen aktiviert haben.", Toast.LENGTH_LONG).show()
//Es wird nach einer Erlaubnis zur Nutzung der Kamera gefragt.
                cameraCheckPermission()
            }else{

                Toast.makeText(context, "Ihre Auswahl: $ratingResult ", Toast.LENGTH_LONG).show()
            }

            dismiss()

        }

        return rootView
    }

    private fun galleryCheckPermission(){
        Dexter.withContext(activity).withPermission(
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        ).withListener(object : PermissionListener{

            override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
               gallery()
            }

            override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                Toast.makeText(context, "Sie haben die Berechtigung für die Auswahl eines Bildes deaktiviert.", Toast.LENGTH_LONG).show()
                showRorationalDialogForPermission()
            }

            override fun onPermissionRationaleShouldBeShown(
                p0: PermissionRequest?,
                p1: PermissionToken?
            ) {
                showRorationalDialogForPermission()
            }

        }).onSameThread().check()
    }



    private fun gallery(){
        val intent = Intent (Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, GalleryRequestCode)
    }
    //Es wird überprüft, die Erlaubnis, gegeben wurde
    fun  cameraCheckPermission(){

        Dexter.withContext(activity)
            .withPermissions(android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA).withListener(

                object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {

                        report?.let {

                            if (report.areAllPermissionsGranted()){
                                camera()
                            }



                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        p0: MutableList<PermissionRequest>?,
                        p1: PermissionToken?) {

                        showRorationalDialogForPermission()


                    }

                }
                ).onSameThread().check()

    }

    private fun camera(){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CameraRequestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            when(requestCode){

                CameraRequestCode->{
                    val bitmap = data?.extras?.get("data") as Bitmap

                    //we are using coroutine image loader (coil)
                   // rootView.profilImage.setImageBitmap(bitmap)
                    rootView.profilImage.load(bitmap){
                        crossfade(true)
                        crossfade(1000)
                   //     transformations(CircleCropTransformation())
                    }

                }
                GalleryRequestCode->{
                    rootView.profilImage.load(data?.data){
                        crossfade(true)
                        crossfade(1000)
                    }
                }

            }
        }
    }

    private fun showRorationalDialogForPermission(){

       /*   AlertDialog.Builder(activity)

           .setMessage("Es sieht so aus, als hätten Sie die Berechtigungen\n" +
                    "\n" +
                    "die für diese Funktion erforderlich sind. Sie kann in den App-Einstellungen aktiviert werden!!!")

            .setPositiveButton("Gehen Sie bitte zur Einstellung"){_,_->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package",  (activity as MainActivity?)?.packageName, null)
                    intent.data = uri
                    startActivity(intent)
                }catch (e: ActivityNotFoundException){
                    e.printStackTrace()
                }

            }
            .setNegativeButton("CANCELN"){dialog, _->
                dialog.dismiss()

            }.show()*/
    }



}