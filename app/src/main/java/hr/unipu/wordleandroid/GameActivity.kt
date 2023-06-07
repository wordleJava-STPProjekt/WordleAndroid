package hr.unipu.wordleandroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import kotlin.random.Random

class GameActivity : AppCompatActivity() {
    lateinit var keyboardLayout: LinearLayout
    lateinit var helpButton: ImageButton
    lateinit var restartButton: ImageButton
    lateinit var answerGrid: LinearLayout
    var answerRows = arrayOf<LinearLayout>()

    private fun initiateDictionaryWords(): MutableList<String> {
        val dictionaryWordsList: MutableList<String> = mutableListOf()

        val dictionaryStringBuilder = StringBuilder()
        val dictionaryInputStream: InputStream = this.resources.openRawResource(R.raw.dictionary)
        val dictionaryReader = BufferedReader(InputStreamReader(dictionaryInputStream))

        while (true) {
            var dictionaryWord: String? = null
            try {
                dictionaryWord = dictionaryReader.readLine()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            if (dictionaryWord == null) break
            dictionaryStringBuilder.append(dictionaryWord).append("\n")
        }
        dictionaryInputStream.close()

        val completeString = dictionaryStringBuilder.toString()
        dictionaryWordsList.addAll(completeString.split("\n"))
        return dictionaryWordsList
    }

    private fun initiateWinningWords(): MutableList<String> {
        val winningWordsList: MutableList<String> = mutableListOf()

        val winningStringBuilder = StringBuilder()
        val winningInputStream: InputStream = this.resources.openRawResource(R.raw.winningwords)
        val winningReader = BufferedReader(InputStreamReader(winningInputStream))

        while (true) {
            var winningWord: String? = null
            try {
                winningWord = winningReader.readLine()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            if (winningWord == null) break
            winningStringBuilder.append(winningWord).append("\n")
        }
        winningInputStream.close()

        val winningCompleteString = winningStringBuilder.toString()
        winningWordsList.addAll(winningCompleteString.split("\n"))
        return winningWordsList
    }

    private fun getRandomWord(wordsList: MutableList<String>): String {
        return wordsList[Random.nextInt(wordsList.size)]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        keyboardLayout = findViewById(R.id.keyboard_include)
        helpButton = findViewById(R.id.icon_help)
        restartButton = findViewById(R.id.icon_restart)
        answerGrid = findViewById(R.id.answer_grid)

        for (i in 1..6) {
            val answerRowId = resources.getIdentifier("answer_row$i", "id", packageName)
            val answerRow: LinearLayout = answerGrid.findViewById(answerRowId)
            answerRows += answerRow
        }

        helpButton.setOnClickListener {
            intent = Intent(this, HelpActivity::class.java)
            startActivity(intent)
        }

        val winningWordsList: MutableList<String> = initiateWinningWords()
        val dictionaryWords: MutableList<String> = initiateDictionaryWords()

        val randomWord: String = getRandomWord(winningWordsList)
    }

    fun changeKeyColor(keyId: Int, colorId: Int) {
        keyboardLayout.findViewById<TextView>(keyId)
            .setBackgroundColor(ResourcesCompat.getColor(resources, colorId, null))
    }

    private fun getCellReference(rowPosition: Int, textViewId: Int): TextView {
        return answerRows[rowPosition - 1].findViewById(textViewId)
    }

    fun changeCellText(rowPosition: Int, textViewId: Int, text: String) {
        // changing text
//        changeCellText(2,R.id.answer_col3, "Q")
        val column: TextView = getCellReference(rowPosition, textViewId)
        column.text = text
    }

    fun changeCellColor(rowPosition: Int, textViewId: Int, colorId: Int) {
        // changing colors
//        changeCellColor(2, R.id.answer_col3, R.color.purple_200)
//        changeKeyColor(R.id.keyB, R.color.black)
        val column: TextView = getCellReference(rowPosition, textViewId)
        column.setBackgroundColor(ResourcesCompat.getColor(resources, colorId, null))
    }
}