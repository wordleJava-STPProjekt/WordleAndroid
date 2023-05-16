package hr.unipu.wordleandroid

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ScoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        val newGameButton = findViewById<Button>(R.id.new_game_button)

        newGameButton.setOnClickListener {
            startActivity(Intent(this, GameActivity::class.java))
        }
    }
}