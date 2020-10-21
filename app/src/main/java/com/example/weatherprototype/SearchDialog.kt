package com.example.weatherprototype

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.weatherprototype.databinding.SearchDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.lifecycle.ViewModel as AndroidxLifecycleViewModel

class SearchDialog : BottomSheetDialogFragment() {

    private val viewBinding by viewBinding(SearchDialogBinding::bind)

    private val viewModel: ViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_dialog, container, false)
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.searchButton.setOnClickListener {
            viewBinding.searchEditText.text?.let { editable ->
                val text = editable.toString()
                if (text.isNotBlank()) viewModel.search(text)
            }
        }

        viewModel.state.observe(this) { state ->
            when (state) {
                State.Input -> clearState()
                State.Loading -> loading()
                State.Error -> error()
                is State.NavigateToDetails -> toDetails(state.location)
            }
        }
    }

    private fun clearState() {
        viewBinding.apply {
            searchEditText.isEnabled = true
            progressBar.visibility = View.INVISIBLE
            searchButton.visibility = View.VISIBLE
            errorTextView.visibility = View.INVISIBLE
        }
    }

    private fun loading() {
        clearState()
        viewBinding.apply {
            searchEditText.isEnabled = false
            progressBar.visibility = View.VISIBLE
            searchButton.visibility = View.INVISIBLE
        }
    }

    private fun error() {
        clearState()
        viewBinding.errorTextView.visibility = View.VISIBLE
    }

    private fun toDetails(location: Location) {
        clearState()
        findNavController().navigate(
            SearchDialogDirections.actionSearchDialogToDetailsFragment(
                location
            )
        )
    }

    sealed class State {
        object Input : State()
        object Loading : State()
        object Error : State()
        data class NavigateToDetails(val location: Location) : State()
    }

    class ViewModel(private val store: WeatherStore) : AndroidxLifecycleViewModel() {
        private val _state = MutableLiveData<State>()
        val state: LiveData<State> get() = _state

        init {
            _state.value = State.Input
        }

        @ExperimentalCoroutinesApi
        @FlowPreview
        fun search(locationName: String) {
            _state.value = State.Loading
            viewModelScope.launch {
                try {
                    val currentWeather = store.getCurrentWeatherByName(locationName)
                    _state.value = State.NavigateToDetails(currentWeather.location)
                } catch (any: Throwable) {
                    _state.value = State.Error
                }
            }
        }
    }
}