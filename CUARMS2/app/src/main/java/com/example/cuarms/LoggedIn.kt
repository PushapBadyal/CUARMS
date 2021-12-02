package com.example.cuarms

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_logged_in.*


class LoggedIn : AppCompatActivity() {

    val phoneNumber = "1800121288800"
    lateinit var cardcall : CardView
    lateinit var cardwritecomplain : CardView

    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logged_in)
        cardcall = findViewById(R.id.action_call_button)
        cardwritecomplain = findViewById(R.id.action_write_complain)

        cardcall.setOnClickListener {
            if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this,"Please Grant Permissions", Toast.LENGTH_SHORT).show()
                requestPermissions( )
            }
            else {
                val it = Intent(Intent.ACTION_CALL)
                it.data= Uri.parse("tel:$phoneNumber")
                startActivity(it)

            }


        }
        cardwritecomplain.setOnClickListener{
            var intent =Intent(this,complaint::class.java)
            startActivity(intent)
        }

        action_cell_information.setOnClickListener{
            var intent =Intent(this,antiraggingcell::class.java)
            startActivity(intent)
        }


        card_appinfo.setOnClickListener{
            var intent =Intent(this,antiraggingcell::class.java)
            startActivity(intent)
        }




        val sharedPref=this?.getPreferences(Context.MODE_PRIVATE)?:return
        val isLogin=sharedPref.getString("Email","1")

        logout.setOnClickListener{
            sharedPref.edit().remove("Email").apply()
            var intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }


        if(isLogin=="1") {

            var email=intent.getStringExtra("email")
            if (email!=null)
            {
                setText(email)
                with(sharedPref.edit())
                {
                    putString("Email",email)
                    apply()
                }

            }
            else
            {
                var intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
            }



        }
        else
        {
           setText(isLogin)
        }

    }


    private fun setText(email:String?)
    {
        db= FirebaseFirestore.getInstance()
        if (email != null) {
            db.collection("USERS").document(email).get()
                .addOnSuccessListener { tasks->
                    name.text=tasks.get("Name").toString()
                }
        }
    }
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this, arrayOf<String>(android.Manifest.permission.CALL_PHONE), 1)

    }

}
