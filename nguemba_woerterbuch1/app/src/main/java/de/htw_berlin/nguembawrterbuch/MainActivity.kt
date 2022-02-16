package de.htw_berlin.nguembawrterbuch

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import de.htw_berlin.nguembawrterbuch.databinding.ActivityMainBinding.inflate
import kotlinx.android.synthetic.main.fragment_home.view.*
import de.htw_berlin.nguembawrterbuch.model.Users
import kotlinx.android.synthetic.main.fragment_home.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var toggle : ActionBarDrawerToggle
    lateinit var copyrightFragment : CopyrightFragment
    lateinit var datenschutzerklaerungFragment : DatenschutzerklaerungFragment
    lateinit var favoritenFragment : FavoritenFragment
    lateinit var forumFragment : ForumFragment
    lateinit var hilfeFragment : HilfeFragment
    lateinit var homeFragment : HomeFragment
    lateinit var impressumFragment : ImpressumFragment
    lateinit var kommentareFragment : KommentareFragment
    lateinit var konjugationFragment : KonjugationFragment
    lateinit var loginFragment : LoginFragment
  //  lateinit var neuPasswortFragment : NeuPasswortFragment
    lateinit var nutzungsbedingungFragment :  NutzungsbedingungFragment
   // lateinit var passvergessenFragment : PassvergessenFragment
    lateinit var personalpronomenFragment : PersonalpronomenFragment
 //   lateinit var resultFragment : ResultFragment
    lateinit var  drawerLayout : DrawerLayout
   var refUsers: DatabaseReference? =null
    lateinit var profileImageHeader: CircleImageView
    lateinit var userNameHeader: TextView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var firebaseUser: FirebaseUser?=null
        drawerLayout = findViewById(R.id.drawer_layout)
        val navView : NavigationView = findViewById(R.id.nav_view)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)


        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        toggle.isDrawerIndicatorEnabled = true
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
        //When we start the app, we see this fragment first
        homeFragment = HomeFragment()
        makeCurrentFragment(homeFragment)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val view: View = navView.inflateHeaderView(R.layout.header)
        profileImageHeader = view.findViewById(R.id.profilImage)
        userNameHeader = view.findViewById(R.id.textNav)
        profileImageHeader.setOnClickListener{

            firebaseUser = FirebaseAuth.getInstance().currentUser

            if (firebaseUser != null){
                val profilSeiteFragment = ProfilSeiteFragment()
                makeCurrentFragment(profilSeiteFragment)
            }else{
                Toast.makeText(this, "Loggen Sie sich bitte ein!", Toast.LENGTH_LONG).show()

            }

        }
//Bottom Navigation
        bottomNavigationView.setOnItemSelectedListener{ item ->
            when (item.itemId){
                R.id.forume -> {
                    forumFragment = ForumFragment()
                    makeCurrentFragment(forumFragment)
                }

                R.id.home1 -> {
                    makeCurrentFragment(homeFragment)
                }
                R.id.suche1 ->{
                    searchEditText?.requestFocus()
                    val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.showSoftInput(searchEditText, InputMethodManager.SHOW_FORCED)
                    makeCurrentFragment(homeFragment)
                }

            }
            true

        }



    }



   /* override fun onStart() {
        super.onStart()

        }

    }*/
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //Put a menu to the ActionBar
        menuInflater.inflate(R.menu.ober_menu, menu)
    /*   var logInItem = menu?.findItem(R.id.login)
       var logOutItem = menu?.findItem(R.id.loOut)
       val mUser = FirebaseAuth.getInstance().currentUser
       if (mUser == null){
           logInItem?.isVisible = true
           logOutItem?.isVisible = false
       }else{
           logInItem?.isVisible = false
           logOutItem?.isVisible = true
       }*/
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)){
            return true
        }else if(item.itemId == R.id.loOut){
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(this, "Das Ausloggen war erfolgreich", Toast.LENGTH_SHORT).show()
            userNameHeader.text = "Benutzername"
            val homeFragment = HomeFragment()
            makeCurrentFragment(homeFragment)

            return true
        }else if(item.itemId == R.id.login){
            val loginFragment = LoginFragment()
            makeCurrentFragment(loginFragment)
            return true
        }
        return super.onOptionsItemSelected(item)
    }



     fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {

        when(menuItem.itemId){

            R.id.favoritenFragment -> {
                favoritenFragment = FavoritenFragment()
                makeCurrentFragment(favoritenFragment)
            }
            R.id.kommentareFragment -> {
                kommentareFragment = KommentareFragment()
                makeCurrentFragment(kommentareFragment)
            }
            R.id.appTeilen -> {
                sendApp("https://play.google.com/store")
            }
            R.id.appBewerten -> {
                goToURL("https://play.google.com/store")
                Toast.makeText(applicationContext, "Wir freuen uns auf Ihre tolle Bewertung." + getEmoji(0x1F60A) , Toast.LENGTH_SHORT).show()
            }
            R.id.spenden -> {
                goToURL("https://www.paypal.me/fortune1976")
                Toast.makeText(applicationContext, "Danke für Ihre Unterstützung!" + getEmoji(0x1F60A), Toast.LENGTH_SHORT).show()
            }
            R.id.hilfeFragment -> {
                hilfeFragment  = HilfeFragment()
                makeCurrentFragment(hilfeFragment)
            }
            R.id.personalpronomenFragment -> {
                personalpronomenFragment  = PersonalpronomenFragment ()
                makeCurrentFragment(personalpronomenFragment)
            }
            R.id.konjugationFragment -> {
                konjugationFragment = KonjugationFragment()
                makeCurrentFragment(konjugationFragment)
            }
            R.id.datenschutzerklaerungFragment -> {
                datenschutzerklaerungFragment  = DatenschutzerklaerungFragment()
                makeCurrentFragment(datenschutzerklaerungFragment)
            }
            R.id.nutzungsbedingungFragment -> {
                nutzungsbedingungFragment = NutzungsbedingungFragment ()
                makeCurrentFragment(nutzungsbedingungFragment)
            }
            R.id.impressumFragment -> {
                impressumFragment  = ImpressumFragment ()
                makeCurrentFragment(impressumFragment)
            }
            R.id.copyrightFragment -> {
                copyrightFragment = CopyrightFragment()
                makeCurrentFragment(copyrightFragment)
            }


        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true

    }

    //Convert a character to an emoji
    fun getEmoji(uni : Int): String = String(Character.toChars(uni))

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }else {
            super.onBackPressed()
        }

    }

    //Open an URL using a button
    fun goToURL(url:String){
        val uri : Uri = Uri.parse(url)
        val intent  = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    //Send the app
    fun sendApp(url: String){
        val intent = Intent(Intent.ACTION_SEND)
        intent.setType("text/plain")
        intent.putExtra(Intent.EXTRA_TEXT, "Hi! Ich würde gerne mit dir, dieses schönes und praktisches Wörterbuch teilen.\n" + url)
        startActivity(Intent.createChooser(intent, "Via"))
    }


     fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.framelayout, fragment)
            commit()
        }






}


