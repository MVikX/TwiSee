package com.example.twisee.di

import android.content.Context
import com.example.twisee.data.preferences.UserPreferences
import com.example.twisee.data.remote.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUserPreferences(@ApplicationContext context: Context): UserPreferences {
        return UserPreferences(context)
    }

    @Provides
    @Singleton
    fun provideTwitchApi(): TwitchApi {
        return Retrofit.Builder()
            .baseUrl("https://api.twitch.tv/helix/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TwitchApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTwitchAuthApi(): TwitchAuthApi {
        return Retrofit.Builder()
            .baseUrl("https://id.twitch.tv/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TwitchAuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTwitchRepository(
        api: TwitchApi,
        authApi: TwitchAuthApi,
        userPreferences: UserPreferences
    ): TwitchRepository {
        return TwitchRepository(api, authApi, userPreferences)
    }
}