package com.udacity.asteroidradar.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.AsteroidItemBinding
import com.udacity.asteroidradar.domain.Asteroid

class AsteroidAdapter(val callback: AsteroidClick) :
    RecyclerView.Adapter<AsteroidAdapter.AsteroidHolder>() {
    var _asteroids = emptyList<Asteroid>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidHolder {
        val dataBinding: AsteroidItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            AsteroidHolder.LAYOUT,
            parent,
            false
        )
        return AsteroidHolder(dataBinding)
    }

    override fun onBindViewHolder(holder: AsteroidHolder, position: Int) {
        holder.view.also {
            it.asteroid = _asteroids[position]
            it.asteroidCallback = callback
            if (_asteroids[position].isPotentiallyHazardous) {
                holder.view.icStatus.setImageResource(R.drawable.ic_status_potentially_hazardous)
            } else {
                holder.view.icStatus.setImageResource(R.drawable.ic_status_normal)
            }
        }
    }

    override fun getItemCount(): Int = _asteroids.size


    class AsteroidHolder(val view: AsteroidItemBinding) : RecyclerView.ViewHolder(view.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.asteroid_item
        }
    }
}

class AsteroidClick(val click: (Asteroid) -> Unit) {
    fun onClick(asteroid: Asteroid) = click(asteroid)
}