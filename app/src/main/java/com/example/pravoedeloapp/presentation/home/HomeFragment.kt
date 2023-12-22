package com.example.pravoedeloapp.presentation.home

import android.opengl.Visibility
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pravoedeloapp.R
import com.example.pravoedeloapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val number = arguments?.getString("login")
        val token = arguments?.getString("token")
        if(number!=null && token!=null){
            binding.greetingTxt.text = getString(
                R.string.greeting_home_number,
                number
            )
            binding.tokenTxt.text = getString(
                R.string.home_token_txt,
                token
            )
        }
        else{
            binding.greetingTxt.text = "Произошла ошибка"
            binding.tokenTxt.visibility = View.GONE
        }
        return binding.root
    }

}