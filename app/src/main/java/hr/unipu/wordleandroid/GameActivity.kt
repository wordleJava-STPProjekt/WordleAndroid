package hr.unipu.wordleandroid

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
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
    lateinit var guessButton: Button
    lateinit var guessText: EditText
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

    @SuppressLint("DiscouragedApi")
    private fun guessWord(word: String, winningWord: String, currentRow: Int) {

        for (i in 1..5) {
            val currentWordChar: Char = word[i - 1].uppercaseChar()
            val winningWordChar: Char = winningWord[i - 1].uppercaseChar()

            if (currentWordChar == winningWordChar) {
                changeKeyColor(
                    keyId = resources.getIdentifier("key$currentWordChar", "id", packageName),
                    colorId = R.color.correct
                )
                changeCellText(
                    rowPosition = currentRow,
                    textViewId = resources.getIdentifier("answer_col$i", "id", packageName),
                    text = currentWordChar.toString()
                )
                changeCellColor(
                    rowPosition = currentRow,
                    textViewId = resources.getIdentifier("answer_col$i", "id", packageName),
                    colorId = R.color.correct
                )
            } else if (winningWord.contains(currentWordChar, ignoreCase = true)) {
                changeKeyColor(
                    keyId = resources.getIdentifier("key$currentWordChar", "id", packageName),
                    colorId = R.color.contains
                )
                changeCellText(
                    rowPosition = currentRow,
                    textViewId = resources.getIdentifier("answer_col$i", "id", packageName),
                    text = currentWordChar.toString()
                )
                changeCellColor(
                    rowPosition = currentRow,
                    textViewId = resources.getIdentifier("answer_col$i", "id", packageName),
                    colorId = R.color.contains
                )
            } else {
                changeKeyColor(
                    keyId = resources.getIdentifier("key$currentWordChar", "id", packageName),
                    colorId = R.color.not_contain
                )
                changeCellText(
                    rowPosition = currentRow,
                    textViewId = resources.getIdentifier("answer_col$i", "id", packageName),
                    text = currentWordChar.toString()
                )
                changeCellColor(
                    rowPosition = currentRow,
                    textViewId = resources.getIdentifier("answer_col$i", "id", packageName),
                    colorId = R.color.not_contain
                )
            }
        }
    }

    private fun changeKeyColor(keyId: Int, colorId: Int) {
        keyboardLayout.findViewById<TextView>(keyId)
            .setBackgroundColor(ResourcesCompat.getColor(resources, colorId, null))
    }

    private fun getCellReference(rowPosition: Int, textViewId: Int): TextView {
        return answerRows[rowPosition - 1].findViewById(textViewId)
    }

    fun changeCellText(rowPosition: Int, textViewId: Int, text: String) {
        // changing text
        // changeCellText(2,R.id.answer_col3, "Q")
        val column: TextView = getCellReference(rowPosition, textViewId)
        column.text = text
    }

    fun changeCellColor(rowPosition: Int, textViewId: Int, colorId: Int) {
        // changing colors
        // changeCellColor(2, R.id.answer_col3, R.color.purple_200)
        // changeKeyColor(R.id.keyB, R.color.black)
        val column: TextView = getCellReference(rowPosition, textViewId)
        column.setBackgroundColor(ResourcesCompat.getColor(resources, colorId, null))
    }

    private fun binarySearch(list: MutableList<String>, string: String): Boolean {
        var low = 0
        var high: Int = list.size - 1

        while (low <= high) {
            val mid: Int = low + (high - low) / 2
            val comparison: Int = string.compareTo(list[mid])

            if (comparison == 0) return true
            if (comparison > 0) low = mid + 1
            else high = mid - 1
        }
        return false
    }

    fun isValidGuess(
        guess: String,
        winningWordsList: MutableList<String>,
        dictionaryWords: MutableList<String>
    ): Boolean {
        return binarySearch(winningWordsList, guess) || binarySearch(dictionaryWords, guess)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        keyboardLayout = findViewById(R.id.keyboard_include)
        helpButton = findViewById(R.id.icon_help)
        restartButton = findViewById(R.id.icon_restart)
        answerGrid = findViewById(R.id.answer_grid)
        guessButton = findViewById(R.id.guess_button)
        guessText = findViewById(R.id.guess_text)

        var CURRENT_ROW = 1

        val winningWordsList: MutableList<String> = initiateWinningWords()
        val dictionaryWords: MutableList<String> = initiateDictionaryWords()

        val winningWord: String = getRandomWord(winningWordsList)
        Log.i("WINNINGWORD", winningWord)

        for (i in 1..6) {
            val answerRowId = resources.getIdentifier("answer_row$i", "id", packageName)
            val answerRow: LinearLayout = answerGrid.findViewById(answerRowId)
            answerRows += answerRow
        }

        helpButton.setOnClickListener {
            intent = Intent(this, HelpActivity::class.java)
            startActivity(intent)
        }

        restartButton.setOnClickListener {
            finish()
            startActivity(intent)
        }

        guessButton.setOnClickListener {
            val guessedText = guessText.text

            if (guessedText.length != 5 || !isValidGuess(
                    guess = guessedText.toString(),
                    winningWordsList = winningWordsList,
                    dictionaryWords = dictionaryWords
                )
            ) {
                Toast.makeText(
                    this,
                    "Guessed word is either invalid or not 5 letters.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                if (guessedText.toString().lowercase() == winningWord.lowercase()) {
                    intent = Intent(this, ScoreActivity::class.java)
                    intent.putExtra("winningWord", winningWord)
                    intent.putExtra("isWon", true)
                    startActivity(intent)
                } else {
                    guessWord(
                        word = guessedText.toString(),
                        winningWord = winningWord,
                        currentRow = CURRENT_ROW
                    )
                    if (CURRENT_ROW == 6) {
                        intent = Intent(this, ScoreActivity::class.java)
                        intent.putExtra("winningWord", winningWord)
                        intent.putExtra("isWon", false)
                        startActivity(intent)
                    }
                    CURRENT_ROW++
                }
            }
            guessText.text.clear()
        }
    }
}