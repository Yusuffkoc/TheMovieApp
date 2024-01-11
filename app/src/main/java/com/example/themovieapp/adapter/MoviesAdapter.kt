package com.example.themovieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.themovieapp.R
import com.example.themovieapp.databinding.MovieRowBinding
import com.example.themovieapp.models.Movie2
import com.example.themovieapp.util.Constants.BASE_IMAGE_URL

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    inner class MoviesViewHolder(private val binding: MovieRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie2) {
            Glide.with(binding.root).load(BASE_IMAGE_URL + movie.posterPath).into(binding.movieId)
            binding.movieNameId.text = movie.originalTitle
            binding.yearId.text = parseYear(movie.releaseDate)
            movie.voteAverage?.let {
                binding.ratingId.text = parseRating(it) + "/10"
                if (it >= 9.0) {
                    setColor(binding, ContextCompat.getColor(binding.root.context, R.color.green))
                } else if (it >= 7.0) {
                    setColor(
                        binding,
                        ContextCompat.getColor(binding.root.context, R.color.yellow)
                    )
                } else {
                    setColor(
                        binding,
                        ContextCompat.getColor(binding.root.context, R.color.red)
                    )
                }
            }
            binding.root.setOnClickListener {
                onItemClickListener?.let { it(movie) }
            }
        }
    }

    private fun parseYear(releaseDate: String?): CharSequence? {
        val date = releaseDate?.split("-")
        return date?.get(0)
    }

    private fun parseRating(it: Double): String {
        val number = it.toString().split(".")
        if (!number.isEmpty()) {
            if (number.get(1).isEmpty()) {
                return number.get(0)
            } else
                return number.get(0) + "." + number.get(0).first()
        } else {
            return it.toString()
        }

    }

    private val differCallback = object : DiffUtil.ItemCallback<Movie2>() {
        override fun areItemsTheSame(oldItem: Movie2, newItem: Movie2): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie2, newItem: Movie2): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return MoviesViewHolder(
            MovieRowBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Movie2) -> Unit)? = null

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movie = differ.currentList[position]
        holder.bind(movie = movie)
    }

    private fun setColor(view: MovieRowBinding, color: Int) {
        view.ratingId.setTextColor(color)
        view.starId.drawable.setTint(color)
    }

    fun setOnItemClickListener(listener: (Movie2) -> Unit) {
        onItemClickListener = listener
    }

}