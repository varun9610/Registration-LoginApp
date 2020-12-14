package com.vumehta.authwithfirebase

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.vumehta.authwithfirebase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.registrationSubmit.setOnClickListener(this)
        binding.Btnsignin.setOnClickListener(this)
        auth = Firebase.auth





    }

    // [START on_start_check_user]
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }
    // [END on_start_check_user]

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
           val intent = Intent(this,Homepage::class.java)
            startActivity(intent)
            finish()
        }
        else{
            return
        }
    }
    private fun createAccount(email: String, password: String) {
      if (!validateForm()) {
         return
   }

        // [START create_user_with_email]
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(baseContext, "Authentication successful.",
                        Toast.LENGTH_SHORT).show()
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.

                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }


            }
        // [END create_user_with_email]
    }


    private fun signIn(email: String, password: String) {

        if (!validateForm()) {
            return
        }


        // [START sign_in_with_email]
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.

                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)

                }

                // [START_EXCLUDE]
                if (!task.isSuccessful) {
                    Toast.makeText(baseContext, "Failed.",
                        Toast.LENGTH_SHORT).show()
                }
                // [END_EXCLUDE]
            }
        // [END sign_in_with_email]
    }




    private fun validateForm(): Boolean {
        val email = binding.editTextTextEmailAddress.text.toString()
        var valid = true

        if (TextUtils.isEmpty(email)) {
            binding.editTextTextEmailAddress.error = "Required."
            valid = false
        } else {
            binding.editTextTextEmailAddress.error = null
        }

        val password = binding.editTextTextPassword.text.toString()
        if (TextUtils.isEmpty(password)) {
            binding.editTextTextPassword.error = "Required."
            valid = false
        } else {
            binding.editTextTextPassword.error = null
        }

        return valid
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.registrationSubmit -> {
                createAccount(binding.editTextTextEmailAddress.text.toString(), binding.editTextTextPassword.text.toString())
            }
            R.id.Btnsignin -> signIn(binding.editTextTextEmailAddress.text.toString(), binding.editTextTextPassword.text.toString())
        }
    }
    companion object {
        private const val TAG = "Main"
    }
}