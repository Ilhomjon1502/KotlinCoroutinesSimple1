package uz.ilhomjon.kotlincoroutines1.retrofit

import retrofit2.http.GET
import retrofit2.http.Path
import uz.ilhomjon.kotlincoroutines1.models.User

interface ApiService {

    @GET("users/{id}")
    suspend fun getUserById(@Path("id") id:Int):User
}