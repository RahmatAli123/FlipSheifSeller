package com.example.flipsheifseller

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.UUID

class Upload_Product_Activity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private var db = FirebaseFirestore.getInstance()
    lateinit var uploadImageView: ImageView
    lateinit var uploadNameEditText: EditText
    lateinit var uploadPriceEditText: EditText
    lateinit var uploadDescriptionEditText: EditText
    lateinit var uploadButton: AppCompatButton
    private lateinit var updateButton: AppCompatButton
    lateinit var deleteButton: AppCompatButton
    private lateinit var mProgressDialog: ProgressDialog
    private var imageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_product)
        auth = FirebaseAuth.getInstance()
        uploadImageView = findViewById(R.id.uploadImage_ImageView)
        uploadNameEditText = findViewById(R.id.uploadName_EditText)
        uploadPriceEditText = findViewById(R.id.uploadPrice_EditText)
        uploadDescriptionEditText = findViewById(R.id.uploadDescription_EditText)
        updateButton =findViewById(R.id.updateProduct_Button)
        deleteButton=findViewById(R.id.deleteProduct_Button)
        uploadButton = findViewById(R.id.upload_Button)

        uploadImageView.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            startActivityForResult(intent, 100)

        }
        uploadButton.setOnClickListener {
            mProgressDialog = ProgressDialog(this)
            mProgressDialog.setTitle("This is TITLE")
            mProgressDialog.setMessage("This is MESSAGE")
            mProgressDialog.show()
            uploadImage()
        }
        updateButton.setOnClickListener {
            val id = intent.getStringExtra("id")
            if (id != null) {
                updateData(id)
            }

        }

    }

    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)}\n      with the appropriate {@link ActivityResultContract} and handling the result in the\n      {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 100)
            imageUri = data?.data
        if (imageUri != null) {
            uploadImageView.setImageURI(imageUri)

        }

    }

    private fun uploadImage() {
        val imageId = Timestamp.now().nanoseconds.toString()
        val st = Firebase.storage.reference
        val stRef = st.child("images/${imageId}")
        stRef.putFile(imageUri!!)
            .addOnSuccessListener {
                stRef.downloadUrl.addOnSuccessListener { uri ->
                    insertData(uri.toString())
                    mProgressDialog.dismiss()
                }
            }
    }

    private fun insertData(uri: String) {
        val id = UUID.randomUUID().toString()
        val name = uploadNameEditText.text.toString()
        val price = uploadPriceEditText.text.toString()
        val description = uploadDescriptionEditText.text.toString()
        val data = HashMap<String, Any>()
        data["id"] = id
        data["name"] = name
        data["price"] = price
        data["description"] = description
        data["image"] = uri
        db.collection("products").document(id).set(data)
            .addOnSuccessListener {
                uploadNameEditText.text.clear()
                uploadPriceEditText.text.clear()
                uploadDescriptionEditText.text.clear()
                Toast.makeText(this, "insertData successfully", Toast.LENGTH_SHORT).show()

            }
            .addOnFailureListener { exception ->
                Toast.makeText(
                    applicationContext,
                    "Error: ${exception.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }

    }

    private fun updateData(id: String) {
        val name = uploadNameEditText.text.toString()
        val price = uploadPriceEditText.text.toString()
        val description = uploadDescriptionEditText.text.toString()
        if (name.isEmpty() || price.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }
        val data = HashMap<String, Any>()
        data["id"] = id
        data["name"] = name
        data["price"] = price
        data["description"] = description
        db.collection("products").document(id).update(data)
            .addOnSuccessListener {
                uploadNameEditText.text.clear()
                uploadPriceEditText.text.clear()
                uploadDescriptionEditText.text.clear()
                Toast.makeText(this, "updateData successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(
                    applicationContext,
                    "Error: ${exception.message}",
                    Toast.LENGTH_SHORT
                ).show()


            }
    }

}