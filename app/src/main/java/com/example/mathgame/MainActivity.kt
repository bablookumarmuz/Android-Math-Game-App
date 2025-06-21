package com.example.mathgame

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mathgame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAddition.setOnClickListener {
            startGame("Addition")
        }

        binding.btnSubtraction.setOnClickListener {
            startGame("Subtraction")
        }

        binding.btnMultiplication.setOnClickListener {
            startGame("Multiplication")
        }

        binding.btnDivision.setOnClickListener {
            startGame("Division")
        }
    }

    private fun startGame(gameType: String) {
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("gameType", gameType)
        startActivity(intent)
    }
}
