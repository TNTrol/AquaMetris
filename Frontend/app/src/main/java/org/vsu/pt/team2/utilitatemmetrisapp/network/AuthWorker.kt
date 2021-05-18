package org.vsu.pt.team2.utilitatemmetrisapp.network

import org.vsu.pt.team2.utilitatemmetrisapp.api.AuthAPI
import org.vsu.pt.team2.utilitatemmetrisapp.api.model.LoginUser
import org.vsu.pt.team2.utilitatemmetrisapp.api.model.QuickLoginUser
import org.vsu.pt.team2.utilitatemmetrisapp.api.model.SuccessfulLoginUser
import retrofit2.Retrofit
import javax.inject.Inject

class AuthWorker @Inject constructor(
    val retrofit: Retrofit
) : ApiWorker() {

    private val authApi = retrofit.create(AuthAPI::class.java)

    suspend fun login(loginUser: LoginUser) : ApiResult<SuccessfulLoginUser>{
        return safeApiCall { authApi.login(loginUser) }
    }

    suspend fun login(quickLoginUser: QuickLoginUser) : ApiResult<SuccessfulLoginUser>{
        return safeApiCall { authApi.login(quickLoginUser) }
    }
}