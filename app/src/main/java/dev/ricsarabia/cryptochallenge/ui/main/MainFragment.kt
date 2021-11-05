package dev.ricsarabia.cryptochallenge.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.ricsarabia.cryptochallenge.R

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        // Retrieving books data
        viewModel.getAvailableBooks()

        // Observers
        viewModel.books.observe(viewLifecycleOwner, {
            Log.wtf("books",it.toString())
        })
        viewModel.loading.observe(viewLifecycleOwner, {
            Log.wtf("loading", it.toString())
        })
        viewModel.errorMessage.observe(viewLifecycleOwner, {
            Log.wtf("errorMessage", it)
        })
    }

}