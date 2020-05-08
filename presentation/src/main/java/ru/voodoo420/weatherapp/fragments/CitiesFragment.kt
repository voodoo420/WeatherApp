package ru.voodoo420.weatherapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_cities.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.voodoo420.weatherapp.R
import ru.voodoo420.weatherapp.adapters.CitiesAdapter
import ru.voodoo420.weatherapp.viewmodels.CitiesViewModel
import timber.log.Timber

class CitiesFragment : Fragment() {

    private val citiesViewModel: CitiesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cities, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapterRecycler()
    }

    private fun initAdapterRecycler() {
        val adapter = CitiesAdapter {
            lifecycleScope.launch {
                citiesViewModel.setCoordinates(it)
                Timber.d("${it.lat} ${it.lon}")
            }
        }
        citiesViewModel.viewState.observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
        })

        cities_recycler.layoutManager = LinearLayoutManager(this.context)
        cities_recycler.adapter = adapter
    }

}