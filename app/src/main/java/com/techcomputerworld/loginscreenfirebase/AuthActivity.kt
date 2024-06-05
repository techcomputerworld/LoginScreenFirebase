package com.techcomputerworld.loginscreenfirebase

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.properties.Delegates

class AuthActivity : AppCompatActivity() {
    companion object {
        lateinit var useremail: String
        lateinit var providerSession: String
    }
    private var email by Delegates.notNull<String>()
    private var password by Delegates.notNull<String>()
    //loginButton only button to register and login user
    private lateinit var loginButton: Button
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var auth: FirebaseAuth
    private lateinit var lyTerms: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        val screenSplash = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        //variableas que voy a usar
        lyTerms = findViewById(R.id.lyTerms)
        lyTerms.visibility = View.INVISIBLE

        loginButton = findViewById(R.id.loginButton)
        etEmail = findViewById(R.id.emailEditText)
        etPassword = findViewById(R.id.passwordEditText)
        // Initialize Firebase Auth the instance
        auth = FirebaseAuth.getInstance()
        //mostrar el Splashscreen durante 1 segundo 1000 miliseconds
        //show the splashscreen 1000 miliseconds 1 second ago
        Thread.sleep(1000)
        screenSplash.setKeepOnScreenCondition { false }
        /* Codigo para pasar al otro activity
        val intent = Intent(this, DetailActivity::class.java)
        startActivity(intent)
        finish();
        */
        // Analytics Events
        val analytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message", "Complete Firebase integration")
        analytics.logEvent("Initscreen", bundle)


    }
    fun login(view: View ) {
        loginUser()
    }
    private fun loginUser() {
        email = etEmail.text.toString()
        password = etPassword.text.toString()

        //inicio de sesión
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful)
                    goHome(email, "email")
                else  {
                     if (lyTerms.visibility == View.INVISIBLE)
                         lyTerms.visibility = View.VISIBLE
                     else {
                         var cbAcept = findViewById<CheckBox>(R.id.cbAcept)
                         if (cbAcept.isChecked) {
                             register()
                         }
                     }


                }
            }
    }
    private fun goHome(email: String, provider: String) {
        useremail = email
        providerSession = provider
        val homeIntent = Intent(this, HomeActivity :: class.java)
        startActivity(intent)
    }
    private fun register() {
        etEmail = findViewById(R.id.emailEditText)
        etPassword = findViewById(R.id.passwordEditText)

        email = etEmail.text.toString()
        password = etPassword.text.toString()

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    //si se ha creado bien el usuario lo vamos a guardar en la base de datos
                    //como guardar una tupla en la base de datos
                    var dateRegister = SimpleDateFormat("dd/mm/yyyy").format(Date())
                    var dbRegister = FirebaseFirestore.getInstance()
                    dbRegister.collection("users").document(email).set(hashMapOf(
                        "user" to email,
                        "dateRegister" to dateRegister
                    ))
                    goHome(email, "email")
                }
                else {
                    Toast.makeText(this, "error ", Toast.LENGTH_SHORT).show()
                }
            }

        //FirebaseAuth.getInstance().createUserWithEmailAndPassword(etEmail, etPassword)
    }
    private fun showAlert() {
        val builder = AlertDialog.Builder (this)
        builder.setTitle("Error")
        //Se ha ocurrido un error autenticando el usuario
        builder.setMessage("An error occurred authenticating the user")
        builder.setPositiveButton("Accept ", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()


    }
    private fun showHome(email: String, provider: ProviderType) {
        val homeIntent = Intent(this, HomeActivity :: class.java).apply {
            putExtra("email",email)
            putExtra("provider", provider.name)
        }
        startActivity(homeIntent)
    }


}