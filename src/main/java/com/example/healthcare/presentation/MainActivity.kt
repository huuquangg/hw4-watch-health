package com.example.healthcare.presentation

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import androidx.wear.ambient.AmbientModeSupport
import androidx.wear.widget.BoxInsetLayout
import androidx.health.services.client.ExerciseClient
import androidx.health.services.client.HealthServices
import androidx.health.services.client.data.ExerciseConfig
import androidx.health.services.client.data.ExerciseType
import androidx.health.services.client.data.DataType
import androidx.health.services.client.pauseExercise
import androidx.health.services.client.startExercise
import com.example.healthcare.R
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity(), AmbientModeSupport.AmbientCallbackProvider {

    private lateinit var exerciseClient: ExerciseClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Khởi tạo ExerciseClient
        exerciseClient = HealthServices.getClient(this).exerciseClient

        val startButton: Button = findViewById(R.id.startButton)
        val pauseButton: Button = findViewById(R.id.pauseButton)

        startButton.setOnClickListener {
            startExercise()
        }

        pauseButton.setOnClickListener {
            pauseExercise()
        }
    }

    private fun startExercise() {
        val config = ExerciseConfig.Builder(ExerciseType.RUNNING)
            .setDataTypes(setOf(DataType.HEART_RATE_BPM))
            .build()

        lifecycleScope.launch {
            exerciseClient.startExercise(config)
        }
    }


    private fun pauseExercise() {
        lifecycleScope.launch {
            exerciseClient.pauseExercise()
        }
    }

    override fun getAmbientCallback(): AmbientModeSupport.AmbientCallback {
        return object : AmbientModeSupport.AmbientCallback() {}
    }
}
