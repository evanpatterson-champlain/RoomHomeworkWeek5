package com.example.roomhomeworkweek5

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.roomhomeworkweek5.data.User
import com.example.roomhomeworkweek5.data.UserViewModel
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel

    private fun debugLog(str: String) {
        Log.d("Debug", str)
    }

    private fun dateToDays(date: Date): Long {
        var t = date.time
        return t / MILLIS_IN_A_DAY
    }

    private fun daysToDate(t: Long): Date {
        return Date(t * MILLIS_IN_A_DAY)
    }

    private fun showDate(date: Date): String {
        val sdf = SimpleDateFormat("EEEE, dd MM yyyy", Locale.US)
        return sdf.format(date)
    }

    private fun showDate(t: Long): String {
        return showDate(daysToDate(t))
    }

    private fun displayUser(user: User): String {
        val numStr = "Number of donuts: ${user.numberDonuts}"
        val dateStr = "Day: ${showDate(user.day)}"
        return "$numStr; $dateStr"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userViewModel = UserViewModel(this.application)

        userViewModel.readAllData.observe(this, Observer { users ->
            val n = users.size

            if (n > 0) {
                val textToShow = displayUser(users[n - 1])
                findViewById<TextView>(R.id.textView_last).text = textToShow
            }

        })

        val submitButton = findViewById<Button>(R.id.btn_submit)
        submitButton.setOnClickListener {
            insertDataToDatabase()
        }

    }

    private fun insertDataToDatabase() {
        val etDonuts = findViewById<EditText>(R.id.et_numberDonuts)
        val numDonutsText: Editable = etDonuts.text
        if (!numDonutsText.equals("")) {
            val numDonuts = Integer.parseInt(numDonutsText.toString())
            var dt = Date()
            val c = Calendar.getInstance()
            c.time = dt
            c.add(Calendar.DATE, 1)
            dt = c.time
            val t = dateToDays(dt)
            val user = User(t - 1, numDonuts)
            userViewModel.addRecord(user)
        }
        etDonuts.text.clear()
    }

    companion object {
        const val MILLIS_IN_A_DAY = (1000 * 86400)
    }

}