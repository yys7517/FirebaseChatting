package com.example.firebasechatting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.firebasechatting.Model.User
import com.example.firebasechatting.databinding.ActivityJoinBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
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

                        // 입력 정보 clear
                        binding.nicknameArea.text.clear()
                        binding.emailArea.text.clear()
                        binding.passwordArea.text.clear()


                        val user = User( auth.uid.toString(), nickname )
                        // 회원가입 성공 시, 유저 정보를 DB에 입력하자 -> Firestore
                        val db = Firebase.firestore

                        // 경로 - users \ uid \ User(uid,nickname)

                        db.collection( "users" )
                            .document( auth.uid.toString() )
                            .set( user )
                            .addOnSuccessListener {
                                Log.d(TAG, "유저 정보 DB 입력 성공")
                            }
                            .addOnFailureListener {
                                Log.d(TAG, "유저 정보 DB 입력 실패")
                            }

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