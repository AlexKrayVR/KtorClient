package alex.com.ktorclient.di

import alex.com.ktorclient.UsersRepository
import org.koin.dsl.module

val appModule  = module {

    single<UsersRepository> { UsersRepository() }


}