package com.example.pravoedeloapp.di

import com.example.data.di.DataModule
import com.example.domain.di.DomainModule
import com.example.pravoedeloapp.presentation.di.PresentationModule
import com.example.pravoedeloapp.presentation.login.LoginFragment
import dagger.Component

@Component(modules = [PresentationModule::class, DataModule::class, DomainModule::class])
interface AppComponent {
    fun inject(fragment: LoginFragment)
}