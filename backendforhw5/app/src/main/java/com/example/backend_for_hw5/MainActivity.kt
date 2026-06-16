package com.example.backend_for_hw5

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.ChipGroup
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.slider.Slider
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    // ---------- UI (ссылки на элементы экрана) ----------
    private lateinit var chipGroupModes: ChipGroup
    private lateinit var textViewTimer: TextView
    private lateinit var sliderDuration: Slider
    private lateinit var textViewDuration: TextView
    private lateinit var progressCircular: CircularProgressIndicator
    private lateinit var buttonStart: MaterialButton
    private lateinit var buttonPause: MaterialButton
    private lateinit var buttonReset: MaterialButton

    // ---------- STATE (состояние таймера) ----------
    private var countDownTimer: CountDownTimer? = null
    private var timeLeftInMillis: Long = 20 * 60 * 1000
    private var initialDuration: Long = 20 * 60 * 1000
    private var isTimerRunning = false
    private var isPaused = false

    // ---------- ENTRY POINT ----------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        applyWindowInsets()
        bindViews()
        setupListeners()
        renderInitialState()
    }

    // ---------- INIT: отступы системы ----------
    private fun applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // ---------- INIT: связка с XML ----------
    private fun bindViews() {
        chipGroupModes = findViewById(R.id.chip_group_modes)
        textViewTimer = findViewById(R.id.text_view_timer)
        sliderDuration = findViewById(R.id.slider_duration)
        textViewDuration = findViewById(R.id.text_view_duration)
        progressCircular = findViewById(R.id.progress_circular)
        buttonStart = findViewById(R.id.button_start)
        buttonPause = findViewById(R.id.button_pause)
        buttonReset = findViewById(R.id.button_reset)
    }

    // ---------- INIT: слушатели ----------
    private fun setupListeners() {
        setupChipGroupListener()
        setupSliderListener()
        setupButtonListeners()
    }

    // ---------- INIT: первая отрисовка ----------
    private fun renderInitialState() {
        updateTimerDisplay()
        updateDurationText(sliderDuration.value.toInt())
        updateProgressBar(reset = true)
        updateButtons()
    }

    // =========================================================
    // 1) LISTENERS
    // =========================================================

    private fun setupChipGroupListener() {
        chipGroupModes.setOnCheckedStateChangeListener { _, checkedIds ->
            if (isTimerRunning) return@setOnCheckedStateChangeListener

            val checkedId = checkedIds.firstOrNull() ?: return@setOnCheckedStateChangeListener

            val durationMinutes = when (checkedId) {
                R.id.chip_work -> 20
                R.id.chip_break -> 5
                R.id.chip_recharge -> 15
                else -> 20
            }

            sliderDuration.value = durationMinutes.toFloat()
            setNewDuration(durationMinutes)
        }
    }

    private fun setupSliderListener() {
        sliderDuration.addOnChangeListener { _, value, fromUser ->
            if (!fromUser) return@addOnChangeListener

            if (isTimerRunning) {
                sliderDuration.value = TimeUnit.MILLISECONDS.toMinutes(initialDuration).toFloat()
                return@addOnChangeListener
            }

            setNewDuration(value.toInt())
        }
    }

    private fun setupButtonListeners() {
        buttonStart.setOnClickListener {
            if (!isTimerRunning) startTimer()
        }

        buttonPause.setOnClickListener {
            if (!isTimerRunning) return@setOnClickListener

            if (isPaused) {
                resumeTimer()
            } else {
                pauseTimer()
            }
        }

        buttonReset.setOnClickListener {
            resetTimer()
        }
    }

    // =========================================================
    // 2) TIMER LOGIC
    // =========================================================

    private fun setNewDuration(minutes: Int) {
        initialDuration = TimeUnit.MINUTES.toMillis(minutes.toLong())
        timeLeftInMillis = initialDuration

        updateTimerDisplay()
        updateDurationText(minutes)
        updateProgressBar(reset = true)
        updateButtons()
    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer(timeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateTimerDisplay()
                updateProgressBar()
            }

            override fun onFinish() {
                isTimerRunning = false
                isPaused = false
                updateButtons()
            }
        }.start()

        isTimerRunning = true
        isPaused = false
        updateButtons()
    }

    private fun pauseTimer() {
        countDownTimer?.cancel()
        isPaused = true
        updateButtons()
    }

    private fun resumeTimer() {
        startTimer()
    }

    private fun resetTimer() {
        countDownTimer?.cancel()
        isTimerRunning = false
        isPaused = false
        timeLeftInMillis = initialDuration

        updateTimerDisplay()
        updateProgressBar(reset = true)
        updateButtons()
    }

    // =========================================================
    // 3) UI RENDER
    // =========================================================

    private fun updateTimerDisplay() {
        val minutes = TimeUnit.MILLISECONDS.toMinutes(timeLeftInMillis)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(timeLeftInMillis) -
                TimeUnit.MINUTES.toSeconds(minutes)

        textViewTimer.text = String.format("%02d:%02d", minutes, seconds)
    }

    private fun updateDurationText(minutes: Int) {
        textViewDuration.text = minutes.toString()
    }

    private fun updateProgressBar(reset: Boolean = false) {
        progressCircular.progress = if (reset) {
            100
        } else {
            ((timeLeftInMillis.toFloat() / initialDuration.toFloat()) * 100).toInt()
        }
    }

    private fun updateButtons() {
        buttonStart.isEnabled = !isTimerRunning
        buttonPause.isEnabled = isTimerRunning
        buttonReset.isEnabled = isTimerRunning || isPaused

        buttonPause.text = if (isPaused) "Продолжить" else "Пауза"
    }
}