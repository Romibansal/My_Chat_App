package com.example.mychatapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.mychatapp.R
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_create_account.*

class CreateAccountActivity : AppCompatActivity() {
    var mAuth:FirebaseAuth?=null
    var mDatabase:DatabaseReference?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        mAuth=FirebaseAuth.getInstance()

        accountCreateActBtn.setOnClickListener{
            var email=accountEmail.text.toString().trim()
            var password=accountpass.text.toString().trim()
            var displayName=accountDisplayName.text.toString().trim()

            if(!TextUtils.isEmpty(email) &&!TextUtils.isEmpty(password)
                && !TextUtils.isEmpty(displayName)){
                createAccount(email,password,displayName)
            }else{
                Toast.makeText(this,"Please fill Out the fields",Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }
    fun createAccount(email:String,password:String,dissplayName:String){
        mAuth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                task: Task<AuthResult> ->
                if(task.isSuccessful){
                    var currentUser=mAuth!!.currentUser
                    var userId=currentUser!!.uid

                    mDatabase=FirebaseDatabase.getInstance().reference
                        .child("Users").child(userId)
                    var userObject=HashMap<String,String>()
                    userObject.put("display_name",dissplayName)
                    userObject.put("status","nfklnlfk")
                    userObject.put("image","nlnsgl")
                    userObject.put("thumb_image","hjj")
                    mDatabase!!.setValue(userObject).addOnCompleteListener{
                        task:Task<Void>->
                        if(task.isSuccessful){
                           // Toast.makeText(this,"User Created",Toast.LENGTH_SHORT).show()
                            var dashboardIntent=Intent(this, DashboardActivity::class.java)
                            dashboardIntent.putExtra("name",dissplayName)
                            startActivity(dashboardIntent)
                            finish()
                        }
                    }
                }else{
                    Toast.makeText(this,"User Not Created",Toast.LENGTH_SHORT).show()

                }
            }
    }
}