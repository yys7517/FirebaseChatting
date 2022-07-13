package com.example.firebasechatting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.firebasechatting.databinding.ActivityJoinBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class JoinActivity : AppCompatActivity() {

    private lateinit var binding : ActivityJoinBinding

    private lateinit var auth: FirebaseAuth

    private val TAG : String = "JoinActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_join)

        auth = Firebase.auth

        binding.btnSubmit.setOnClickListener {

            val nickname = binding.nicknameArea.text.toString()
            val email = binding.emailArea.text.toString()
            val password = binding.passwordArea.text.toString()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "성공")

                        binding.nicknameArea.text.clear()
                        binding.emailArea.text.clear()
                        binding.passwordArea.text.clear()

                        Toast.makeText(this, "회원가입 성공", Toast.LENGTH_SHORT).show()
                        finish()

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.d(TAG, "실패")
                        Toast.makeText(this, "회원가입 실패", Toast.LENGTH_SHORT).show()


                    }
                }
        }

    }
}