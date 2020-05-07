package com.example.todoapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.todoapplication.database.AppDatabase
import com.example.todoapplication.database.entity.User
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        actionBarStyle()
        registration()

    }
    private fun actionBarStyle(){
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Sign up"
    }

    @SuppressLint("WrongConstant")
    private fun registration() {
        sign_up_btn.setOnClickListener {
            if (reg_name.text!!.isEmpty()) {
                reg_name.error = "Fill it"
            }
            if (reg_username.text!!.isEmpty()) {
                reg_username.error = "Fill it"
            }
            if (reg_password_again.text!!.isEmpty()) {
                reg_password_again.error = "Fill it"
            }
            if (reg_password.text!!.isEmpty()) {
                reg_password.error = "Fill it"
            }
            if (reg_password.text!!.length >= 8) {
                if (reg_password.text.toString() == reg_password_again.text.toString()) {
                    AsyncTask.execute {
                        val user = AppDatabase.getInstance(applicationContext)?.getUserDao()
                            ?.getUserByUsername(
                                reg_username.text.toString()
                            )
                        if (user != null) {
                            if (user.username == reg_username.text.toString()) {
                                runOnUiThread {
                                    val toast = Toast.makeText(
                                        applicationContext,
                                        "Choose another username", 5
                                    )
                                    toast.show()
                                }
                            } else {
                                AppDatabase.getInstance(applicationContext)?.getUserDao()
                                    ?.insertUser(
                                        User(
                                            username = reg_username.text.toString(),
                                            name = reg_name.text.toString(),
                                            password = reg_password.text.toString()
                                        )
                                    )
                                runOnUiThread {
                                    val intent = Intent(this, ToDoListActivity::class.java)
                                    intent.putExtra(ToDoListActivity.EXTRA_USERID, user.userId)
                                    startActivity(intent)
                                    finish()
                                }
                            }
                        } else {
                            AppDatabase.getInstance(applicationContext)?.getUserDao()
                                ?.insertUser(
                                    User(
                                        username = reg_username.text.toString(),
                                        name = reg_name.text.toString(),
                                        password = reg_password.text.toString()
                                    )
                                )
                            val user = AppDatabase.getInstance(applicationContext)?.getUserDao()
                                ?.getUserByUsername(
                                    reg_username.text.toString()
                                )
                            runOnUiThread {
                                val intent = Intent(this, ToDoListActivity::class.java)
                                intent.putExtra(ToDoListActivity.EXTRA_USERID, user!!.userId)
                                startActivity(intent)
                                finish()
                            }
                        }
                    }
                } else {
                    val toast = Toast.makeText(
                        applicationContext,
                        "Not equal", 5
                    )
                    toast.show()
                }
            } else {
                reg_password.error = "length should be more than 8 or equal"
            }
        }
    }
}
