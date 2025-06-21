// GameActivity.kt - Supports Addition, Subtraction, Multiplication, Division with Header and Back Button

package com.example.mathgame

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mathgame.databinding.ActivityGameBinding
import kotlin.random.Random

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding
    private var score = 0
    private var life = 3
    private var timeLeft = 60
    private lateinit var gameType: String
    private var correctAnswer = 0

    private lateinit var timer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gameType = intent.getStringExtra("gameType") ?: "Addition"
        supportActionBar?.title = gameType
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        startTimer()
        generateQuestion()

        binding.btnOk.setOnClickListener {
            val answerText = binding.etAnswer.text.toString()
            if (answerText.isNotEmpty()) {
                checkAnswer(answerText.toInt())
            }
        }

        binding.btnNext.setOnClickListener {
            binding.etAnswer.text?.clear()
            binding.tvFeedback.text = ""
            generateQuestion()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun startTimer() {
        timer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeft = (millisUntilFinished / 1000).toInt()
                binding.tvTime.text = "Time: $timeLeft"
            }

            override fun onFinish() {
                endGame()
            }
        }
        timer.start()
    }

    private fun generateQuestion() {
        val num1 = Random.nextInt(10, 100)
        val num2 = Random.nextInt(1, 100) // Avoid zero for division

        correctAnswer = when (gameType) {
            "Addition" -> num1 + num2
            "Subtraction" -> num1 - num2
            "Multiplication" -> num1 * num2
            "Division" -> num1 / num2
            else -> num1 + num2
        }

        val questionText = when (gameType) {
            "Addition" -> "$num1 + $num2 = ?"
            "Subtraction" -> "$num1 - $num2 = ?"
            "Multiplication" -> "$num1 × $num2 = ?"
            "Division" -> "$num1 ÷ $num2 = ?"
            else -> "$num1 + $num2 = ?"
        }

        binding.tvQuestion.text = questionText
    }

    private fun checkAnswer(answer: Int) {
        if (answer == correctAnswer) {
            score += 10
            Toast.makeText(this, "Correct Answer!", Toast.LENGTH_SHORT).show()
            binding.tvFeedback.text = "Congratulations, your answer is correct"
        } else {
            life -= 1
            Toast.makeText(this, "Wrong Answer!", Toast.LENGTH_SHORT).show()
            binding.tvFeedback.text = "Sorry, your answer is wrong\nCorrect answer: $correctAnswer"
        }

        binding.tvScore.text = "Score: $score"
        binding.tvLife.text = "Life: $life"

        if (life == 0) {
            endGame()
        }
    }

    private fun endGame() {
        timer.cancel()
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("score", score)
        startActivity(intent)
        finish()
    }
}
