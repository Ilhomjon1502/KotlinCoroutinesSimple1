package uz.ilhomjon.kotlincoroutines1

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.*
import uz.ilhomjon.kotlincoroutines1.databinding.ActivityMainBinding
import uz.ilhomjon.kotlincoroutines1.models.User
import uz.ilhomjon.kotlincoroutines1.retrofit.ApiClient
import kotlin.coroutines.CoroutineContext

// documentation link : https://developer.android.com/kotlin/coroutines
class MainActivity : AppCompatActivity(), CoroutineScope {

    lateinit var binding:ActivityMainBinding
    lateinit var job: Job
    private val TAG = "MainActivity"

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        GlobalScope.launch {
//            val fetchUser = fetchUser()
//            withContext(Dispatchers.Main){
//                binding.tv.text = fetchUser.name
//            }
//        }

//        fetchUserLaunch()

//        GlobalScope.launch(Dispatchers.Main) {
//            val user1 = fetchUserAsync1()
//            val user2 = fetchUserAsync2()
//            binding.tv.text = user1.phone + "  "+ user2.phone
//        }


//        CoroutineScope dan implements qilganimizdan keyin launchni o'zini GlobalScope siz ishlatish mumkin'
        job = Job()
        launch {
            val user1 = fetchUserAsync1()
            val user2 = fetchUserAsync2()
            Log.d(TAG, "onCreate: $user1  $user2")
            binding.tv.text = user1.phone + "  "+ user2.phone
        }
    }

//    suspend fun fetchUser(): User {
//        return GlobalScope.async(Dispatchers.IO){
//            ApiClient.apiService.getUserById(1)
//        }.await()
//    }

    fun fetchUserLaunch() {
        GlobalScope.launch(Dispatchers.Main) {
            val user = ApiClient.apiService.getUserById(1)
            binding.tv.text = user.email
        }
    }
    suspend fun fetchUserAsync1() : User{
        return GlobalScope.async (Dispatchers.IO) {
            ApiClient.apiService.getUserById(1)
        }.await()
    }
    //async o'rniga withContext ishlatish mumkin. Bir xil tushuncha
    suspend fun fetchUserAsync2() : User{
        return GlobalScope.async (Dispatchers.IO) {
            ApiClient.apiService.getUserById(2)
        }.await()
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main+job // launchda main shart bo'lmaydi, chunki bu joyni o'zida mainni berib qo'yyapmiz

    override fun onDestroy() {
        Log.d(TAG, "onDestroy: ")
        job.cancel()
        super.onDestroy()
    }
}