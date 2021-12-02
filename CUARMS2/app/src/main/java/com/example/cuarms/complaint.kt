package com.example.cuarms

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class complaint : AppCompatActivity() {
    lateinit var complainname: EditText
    lateinit var complinuid: EditText
    lateinit var complainphone: EditText
    lateinit var complainttext: EditText
    lateinit var send: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.complaint)

        complainname= findViewById(R.id.complainname)
        complainphone= findViewById(R.id.complainphone)
        complinuid= findViewById(R.id.complainuid)
        complainttext= findViewById(R.id.complaintext)
        send= findViewById(R.id.send)

        send.setOnClickListener{
            saveComplain()
        }
    }
    private fun saveComplain(){
        val name = complainname.text.toString().trim()
        val uid = complinuid.text.toString().trim()
        val phone = complainphone.text.toString().trim()
        val message = complainttext.text.toString().toString()
        if (name.isEmpty()){
            complainname.error="Please Enter your Complain"
            return
        }

        val ref = FirebaseDatabase.getInstance().getReference("CUARMS")

         val complainId = ref.push().key

        val complain = complainId?.let { complains(it,name,uid,phone,message) }

        complainId?.let {
            ref.child(it).setValue(complain).addOnCompleteListener{
                Toast.makeText(applicationContext,"Complain lodged successfully",Toast.LENGTH_SHORT).show()
            }
        }
    }
}