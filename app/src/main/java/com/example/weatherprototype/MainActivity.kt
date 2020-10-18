package com.example.weatherprototype

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dropbox.android.external.store4.fresh
import com.example.weatherprototype.databinding.ActivityMainBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    @ExperimentalCoroutinesApi
    @FlowPreview
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewModel().result.observe(this) { binding.textView.text = it }
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    class ViewModel : androidx.lifecycle.ViewModel() {
        private val _result = MutableLiveData<String>()
        val result: LiveData<String> get() = _result

        init {
            viewModelScope.launch {
                val current = currentWeatherStore.fresh("London")
                val sevenDays = sevenDaysForecastStore.fresh(current.coord ?: Coord(0.0, 0.0))
                _result.value = "$current\n\n\n$sevenDays"
            }
        }
    }
}