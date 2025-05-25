package com.bryson.mysukiexam

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import androidx.room.Room
import com.bryson.mysukiexam.data.local.database.AppDatabase
import com.bryson.mysukiexam.domain.repository.UserRepository
import com.bryson.mysukiexam.presentation.AuthViewModel
import com.bryson.mysukiexam.data.repository.UserRepositoryImpl

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        val appModule = module {
            single {
                Room.databaseBuilder(
                    get(),
                    AppDatabase::class.java,
                    "auth-db"
                ).build()
            }
            single { get<AppDatabase>().userDao() }
            single<UserRepository> { UserRepositoryImpl(get()) }
            viewModel { AuthViewModel(get()) }
        }

        startKoin {
            androidContext(this@App)
            modules(appModule)
        }
    }
}