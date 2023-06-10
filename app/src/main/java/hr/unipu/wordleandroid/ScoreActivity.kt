package hr.unipu.wordleandroid

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import nl.dionsegijn.konfetti.core.models.Shape
import nl.dionsegijn.konfetti.xml.KonfettiView
import java.util.concurrent.TimeUnit

class ScoreActivity : AppCompatActivity() {
    private lateinit var scoreText: TextView
    private lateinit var viewConfetti: KonfettiView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)
        scoreText = findViewById(R.id.score_text)
        viewConfetti = findViewById(R.id.konfettiView)

        viewConfetti.start(
            Party(
                speed = 0f,
                maxSpeed = 30f,
                damping = 0.9f,
                spread = 360,
                colors = listOf(0xfce18a, 0x8afce1, 0xff726d, 0xf4306d, 0xb48def),
                shapes = listOf(Shape.Circle),
                position = Position.Relative(0.5, 0.3),
                emitter = Emitter(duration = 100, TimeUnit.MILLISECONDS).max(100)
            )
        )

        val winningWord = intent.getStringExtra("winningWord")
        val isWon = intent.getBooleanExtra("isWon", false)

        if (isWon) {
            scoreText.text =
                "Congratulations! \n\n You won! \n\n  The correct word was $winningWord"
        } else {
            scoreText.text = "Shame \n\n You lost! \n\n The correct word was $winningWord"
        }

        val newGameButton = findViewById<Button>(R.id.new_game_button)

        newGameButton.setOnClickListener {
            startActivity(Intent(this, GameActivity::class.java))
        }
    }
}