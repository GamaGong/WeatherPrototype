package com.example.weatherprototype.app.search

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.ResultReceiver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.weatherprototype.R
import com.example.weatherprototype.app.Location
import com.example.weatherprototype.databinding.SearchDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchDialog : BottomSheetDialogFragment() {

    private val viewBinding by viewBinding(SearchDialogBinding::bind)

    private val viewModel: SearchDialogViewModel by viewModel()

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
            viewBinding.searchEditText.text?.let(::search)
        }

        viewBinding.searchEditText.setOnEditorActionListener { textView, actionId, _ ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    textView.text?.let(::search)
                    true
                }
                else -> false
            }
        }

        viewModel.state.observe(this) { state ->
            when (state) {
                SearchState.Input -> clearState()
                SearchState.Loading -> loading()
                SearchState.Error -> error()
                is SearchState.NavigateToDetails -> toDetails(state.location)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        GlobalScope.launch(Dispatchers.Default) {
            delay(200)
            withContext(Dispatchers.Main) {
                showKeyboard(requireContext(), viewBinding.searchEditText)
            }
        }
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    private fun search(charSequence: CharSequence) {
        val text = charSequence.toString()
        if (text.isNotBlank()) viewModel.search(text)
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

}

fun showKeyboard(context: Context, view: View) {
    view.requestFocus()
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT, object: ResultReceiver(Handler(Looper.getMainLooper()) {
        true
    }){})
}