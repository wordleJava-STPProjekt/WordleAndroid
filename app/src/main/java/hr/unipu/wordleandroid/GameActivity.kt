package hr.unipu.wordleandroid

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import org.w3c.dom.Text

class GameActivity : AppCompatActivity() {
    lateinit var keyboardLayout: LinearLayout
    lateinit var helpButton: ImageButton
    lateinit var restartButton: ImageButton
    lateinit var answerGrid: LinearLayout
    var answerRows = arrayOf<LinearLayout>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        keyboardLayout= findViewById(R.id.keyboard_include)
        helpButton = findViewById(R.id.icon_help)
        restartButton = findViewById(R.id.icon_restart)
        answerGrid = findViewById(R.id.answer_grid)

        for(i in 1..6){
            val answerRowId = resources.getIdentifier("answer_row$i", "id",packageName)
            val answerRow: LinearLayout = answerGrid.findViewById(answerRowId)
            answerRows += answerRow
        }

        helpButton.setOnClickListener{
            intent = Intent(this, HelpActivity::class.java)
            startActivity(intent)
        }



    }
    fun changeKeyColor(keyId: Int, colorId: Int){
        keyboardLayout.findViewById<TextView>(keyId).setBackgroundColor(ResourcesCompat.getColor(resources, colorId, null))
    }

    private fun getCellReference(rowPosition: Int, textViewId: Int): TextView {
        return answerRows[rowPosition-1].findViewById(textViewId)
    }
    fun changeCellText(rowPosition: Int, textViewId: Int, text: String){
        // changing text
//        changeCellText(2,R.id.answer_col3, "Q")
        val column: TextView = getCellReference(rowPosition,textViewId)
        column.text = text
    }
    fun changeCellColor(rowPosition: Int, textViewId: Int, colorId: Int){
        // changing colors
//        changeCellColor(2, R.id.answer_col3, R.color.purple_200)
//        changeKeyColor(R.id.keyB, R.color.black)
        val column: TextView = getCellReference(rowPosition,textViewId)
        column.setBackgroundColor(ResourcesCompat.getColor(resources, colorId,null))
    }
}