package com.example.mychatapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mychatapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {
    var mDatabase:DatabaseReference?=null
    var mCurrentUser:FirebaseUser?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        mCurrentUser=FirebaseAuth.getInstance().currentUser
        var userId=mCurrentUser!!.uid
        mDatabase=FirebaseDatabase.getInstance().reference
            .child("Users")
            .child(userId)

        mDatabase!!.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(datasnapshot: DataSnapshot) {
                var displayName=datasnapshot!!.child("display_name").value
                var image=datasnapshot!!.child("image").value
                var userStatus=datasnapshot!!.child("status").value
                var thumbimage=datasnapshot!!.child("thumb_image").value
                settingsDisplayName.text=displayName.toString()
                settingStatusText.text=userStatus.toString()

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        settingsChangeStatus.setOnClickListener{
            var intent= Intent(this,StatusActivity::class.java)
            intent.putExtra("status",settingStatusText.text.toString().trim())
            startActivity(intent)
        }
    }

}