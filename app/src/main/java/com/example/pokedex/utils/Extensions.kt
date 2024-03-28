package com.example.pokedex.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pokedex.ui.MainActivity

inline fun <reified T : ViewModel> Context.getViewModel() =
    ViewModelProvider(this as MainActivity)[T::class.java]
