package com.example.todoapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.todoapplication.database.AppDatabase
import com.example.todoapplication.database.entity.User
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        AsyncTask.execute {
//            AppDatabase.getInstance(applicationContext)?.getUserDao()?.insertUser(
//                User( username = "Alibek",name =  "Alibek",password =  "123")
//            )
//        }
        actionBarStyle()
        login()
        signUp()
    }
    private fun actionBarStyle(){
        supportActionBar?.title = "Sign in"
    }

    private fun signUp(){
        sign_up_link.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
    }

    @SuppressLint("WrongConstant")
    private fun login(){
        sign_in_btn.setOnClickListener{
            AsyncTask.execute {
                val user = AppDatabase.getInstance(applicationContext)?.getUserDao()?.getUserByUsername(
                    get_username_text.text.toString()
                )
                if(get_username_text.text!!.isEmpty() || get_password_text.text!!.isEmpty()) {
                    if (get_username_text.text!!.isEmpty()) {
                        get_username_text.error = "Fill it"
                    }
                    if (get_password_text.text!!.isEmpty()) {
                        get_password_text.error = "Fill it"
                    }
                }
                else {
                    if (user != null) {
                        if (user.password == get_password_text.text.toString()) {
                            runOnUiThread {
                                val intent = Intent(this, ToDoListActivity::class.java)
                                intent.putExtra(ToDoListActivity.EXTRA_USERID, user.userId)
                                startActivity(intent)
                                finish()
                            }
                        } else {
                            runOnUiThread {
                                val toast = Toast.makeText(
                                    applicationContext,
                                    "Wrong password", 5
                                )
                                toast.show()
                            }
                        }
                    } else {
                        runOnUiThread {
                            val toast = Toast.makeText(
                                applicationContext,
                                "There is no such user!", 5
                            )
                            toast.show()
                        }
                    }
                }
            }
        }
    }
}
