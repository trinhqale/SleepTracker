package com.example.sleeptracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts

private const val TAG = "DetailActivity"

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        var button = findViewById<Button>(R.id.button)
        var check = findViewById<CheckBox>(R.id.isNap)
        var napInput = false
        check.setOnCheckedChangeListener{
            _, isChecked->
            napInput = isChecked
        }

        button.setOnClickListener {
            var dayInput = findViewById<EditText>(R.id.dayInput) as EditText
            var durationInput = findViewById<EditText>(R.id.durationInput) as EditText

            val intent = Intent(it.context, MainActivity::class.java)
            intent.putExtra("day", dayInput.text.toString().toInt())
            intent.putExtra("duration", durationInput.text.toString().toDouble())
            intent.putExtra("isNap", napInput)
            this.startActivity(intent)
            finish()
//            Toast.makeText(this, "day: ${dayInput.text.toString()}, duration: ${durationInput.text.toString()}, nap is $napInput", Toast.LENGTH_LONG).show()
        }
    }
}