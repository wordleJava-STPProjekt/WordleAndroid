package hr.unipu.wordleandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class LandingActivity : AppCompatActivity() {

    fun openActivity(activityToOpen: String) {
        when(activityToOpen) {
            "HelpActivity" -> {
                intent = Intent(this, HelpActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        val startButton = findViewById<Button>(R.id.start_button)
        val helpButton = findViewById<Button>(R.id.help_button)

        startButton.setOnClickListener{
            Log.i("Landing", "start")
        }

        helpButton.setOnClickListener{
            openActivity("HelpActivity")
        }
    }
}