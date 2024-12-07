package com.example.flipsheifseller

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var auth:FirebaseAuth
    private lateinit var emailLoginEditText: EditText
    private lateinit var passwordLoginEditText: EditText
    private lateinit var loginButton: AppCompatButton
    private lateinit var signUpTextView: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth=FirebaseAuth.getInstance()

        emailLoginEditText = findViewById(R.id.emailLogin_EditText)
        passwordLoginEditText = findViewById(R.id.passwordLogin_EditText)
        signUpTextView = findViewById(R.id.signUp_TextView)
        loginButton = findViewById(R.id.login_Button)


        loginButton.setOnClickListener {
            val email = emailLoginEditText.text.toString()
            val password =passwordLoginEditText.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                return@setOnClickListener
            }
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    if (it.user != null) {
                        Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }

                }
                .addOnFailureListener {
                    Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
                }
            startActivity(Intent(this,MainActivity::class.java)).apply {
                finish()
            }


        }
        signUpTextView.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
        }


    }
}