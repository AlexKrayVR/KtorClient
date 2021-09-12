package alex.com.ktorclient

import alex.com.ktorclient.di.appModule
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.builtins.ListSerializer
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MainActivity : AppCompatActivity() {

    val firstPresenter: UsersRepository by inject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startKoin {
            androidLogger()
            androidContext(this@MainActivity)
            modules(appModule)
        }


        CoroutineScope(Dispatchers.IO).launch {

            // val result = fetchSimple().execute().readText()
            //logDebug("result: $result")

            val response: HttpResponse = fetchSimple().execute()
            logDebug("response status: ${response.status}")
            logDebug("response content: ${response.readText()}")

            val listUsers = HttpKtorClient.json.decodeFromString(
                ListSerializer(TestClass.serializer()),
                response.readText()
            )
            logDebug("response content: ${listUsers.size}")


//            var list = mutableListOf<TestClass>()
//            try {
//                list = fetchData() as MutableList<TestClass>
//            } catch (ex: ClientRequestException) {
//                logDebug("exception: ${ex.localizedMessage}")
//            }
//            logDebug("list: ${list.size}")
        }

    }

    private suspend fun fetchSimple(): HttpStatement {
        //val apiKey = "key"
        return HttpKtorClient.client.get {
            url {
                path("/api/get_users.php")
                //header("ApiKey", apiKey)
            }
        }
    }

    private suspend fun fetchData(): List<TestClass> {
        val httpRequest = HttpKtorClient.client.get<HttpStatement> {
            url {
                path("/api/get_users.php")
            }
        }
        return HttpKtorClient.json.decodeFromString(
            ListSerializer(TestClass.serializer()),
            httpRequest.execute().readText()
        )
    }
}


