package hr.unipu.wordleandroid

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat

class GameActivity : AppCompatActivity() {
    lateinit var keyboardLayout: LinearLayout
    lateinit var helpButton: ImageButton
    lateinit var restartButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        keyboardLayout= findViewById(R.id.keyboard_include)
        helpButton = findViewById(R.id.icon_help)
        restartButton = findViewById(R.id.icon_restart)

        helpButton.setOnClickListener{
            intent = Intent(this, HelpActivity::class.java)
            startActivity(intent)
        }
    }
    fun changeKeyColor(keyId: Int, colorId: Int){
        keyboardLayout.findViewById<TextView>(keyId).setBackgroundColor(ResourcesCompat.getColor(resources, colorId, null))
    }
}