package com.udacity.asteroidradar.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.PictureOfDay
import com.udacity.asteroidradar.viewmodel.Factory
import com.udacity.asteroidradar.viewmodel.MainViewModel

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, Factory(requireNotNull(this.activity?.application)))
            .get(MainViewModel::class.java)
    }

    private lateinit var pictureOfDay: ImageView
    private lateinit var asteroidAdapter: AsteroidAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        asteroidAdapter = AsteroidAdapter(AsteroidClick {
            this.findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
            Toast.makeText(context, "Open detail asteroid  ${it.codename}", Toast.LENGTH_SHORT)
                .show()
        })

        binding.root.findViewById<RecyclerView>(R.id.asteroid_recycler).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = asteroidAdapter
        }

        setHasOptionsMenu(true)

        pictureOfDay = binding.activityMainImageOfTheDay

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.pictureOfDay.observe(viewLifecycleOwner, ::showPictureOfDay)
        viewModel.asteroidsSaved.observe(viewLifecycleOwner, ::showAsteroidSaved)
    }

    private fun showPictureOfDay(pictureOfDay: PictureOfDay?) {
        pictureOfDay?.let {
            Picasso.with(context).load(pictureOfDay.url)
                .placeholder(R.drawable.placeholder_picture_of_day)
                .fit()
                .into(this.pictureOfDay)
        }
    }

    private fun showAsteroidSaved(asteroids: List<Asteroid>) {
        asteroids.apply {
            asteroidAdapter._asteroids = asteroids
            Toast.makeText(requireContext(), "Count: ${asteroids.size}", Toast.LENGTH_SHORT).show()

        }
    }

    private fun showAsteroidWeek(asteroids: List<Asteroid>) {
        asteroids.apply {
            asteroidAdapter._asteroids = asteroids
            Toast.makeText(requireContext(), "Count: ${asteroids.size}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showAsteroidToday(asteroids: List<Asteroid>) {
        asteroids.apply {
            asteroidAdapter._asteroids = asteroids
            Toast.makeText(requireContext(), "Count: ${asteroids.size}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.show_asteroid_saved -> {
                viewModel.asteroidsSaved.observe(viewLifecycleOwner, ::showAsteroidSaved)

            }
            R.id.show_asteroid_today -> {
                viewModel.asteroidsToday.observe(viewLifecycleOwner, ::showAsteroidToday)
            }
            R.id.show_asteroid_week -> {
                viewModel.asteroidsWeek.observe(viewLifecycleOwner, ::showAsteroidWeek)
            }
            else -> {
                Toast.makeText(requireContext(), "Select wrong", Toast.LENGTH_SHORT).show()
            }

        }
        return true
    }
}
