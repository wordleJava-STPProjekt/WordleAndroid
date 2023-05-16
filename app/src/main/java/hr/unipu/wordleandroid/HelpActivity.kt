package hr.unipu.wordleandroid

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class HelpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

        val backButton = findViewById<Button>(R.id.back_button)

        backButton.setOnClickListener {
            intent = Intent(this, LandingActivity::class.java)
            startActivity(intent)
        }
    }
}