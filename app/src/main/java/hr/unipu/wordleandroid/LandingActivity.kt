package hr.unipu.wordleandroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class LandingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        val startButton = findViewById<Button>(R.id.start_button)
        val helpButton = findViewById<Button>(R.id.help_button)

        startButton.setOnClickListener{
            Log.i("Landing", "start")
            startActivity(Intent(this, GameActivity::class.java))
        }

        helpButton.setOnClickListener{
            startActivity(Intent(this, HelpActivity::class.java))
        }
    }
}