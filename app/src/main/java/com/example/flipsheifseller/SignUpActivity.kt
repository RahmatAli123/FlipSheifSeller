package com.example.flipsheifseller

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var nameEditText: EditText
    lateinit var emailEditText: EditText
    lateinit var passwordEditText: EditText
    lateinit var signUpButton: AppCompatButton

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()
        nameEditText = findViewById(R.id.name_EditText)
        emailEditText = findViewById(R.id.email_EditText)
        passwordEditText = findViewById(R.id.password_EditText)
        signUpButton = findViewById(R.id.signUp_Button)

        signUpButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                return@setOnClickListener

            }
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    if (it.user != null) {
                        Toast.makeText(this, "Register Successfully", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java)).apply {
                            finish()
                        }

                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Register Failed", Toast.LENGTH_SHORT).show()
                }
        }


    }
}