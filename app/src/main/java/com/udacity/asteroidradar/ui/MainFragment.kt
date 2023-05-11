package com.udacity.asteroidradar.ui

import android.content.Intent
import android.net.Uri
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
import androidx.lifecycle.Observer
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
            Toast.makeText(context, "Open detail asteroid  ${it.codename}", Toast.LENGTH_SHORT).show()
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
        viewModel.asteroidsWeek.observe(viewLifecycleOwner, Observer<List<Asteroid>> { asteroids ->
            asteroids.apply {
                asteroidAdapter._asteroids = asteroids
            }
        })
    }

    private fun showPictureOfDay(pictureOfDay: PictureOfDay?) {
        Picasso.with(context).load(pictureOfDay?.url).into(this.pictureOfDay)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}
