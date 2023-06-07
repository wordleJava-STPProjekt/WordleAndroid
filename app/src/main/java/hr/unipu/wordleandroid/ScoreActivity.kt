package hr.unipu.wordleandroid

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ScoreActivity : AppCompatActivity() {
    lateinit var scoreText: TextView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)
        scoreText = findViewById(R.id.score_text)

        val winningWord = intent.getStringExtra("winningWord")
        val isWon = intent.getBooleanExtra("isWon", false)

        if (isWon) {
            scoreText.text = "Congratulations \n You won! \n The correct word was $winningWord"
        } else {
            scoreText.text = "Shame \n You lost! \n The correct word was $winningWord"
        }

        val newGameButton = findViewById<Button>(R.id.new_game_button)

        newGameButton.setOnClickListener {
            startActivity(Intent(this, GameActivity::class.java))
        }
    }
}