package com.example.proekt_razrabotka

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.proekt_razrabotka.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.counterText.text = "0"

        binding.plusButton.setOnClickListener {
            count++
            binding.counterText.text = count.toString()
        }

        binding.minusButton.setOnClickListener {
            if (count > 0) {
                count--
                binding.counterText.text = count.toString()
            }
        }

        binding.resetButton.setOnClickListener {
            count = 0
            binding.counterText.text = "0"
        }
    }
}