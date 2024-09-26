package org.vsu.pt.team2.utilitatemmetrisapp.managers

import com.orhanobut.logger.Logger
import com.yandex.metrica.YandexMetrica
import kotlinx.coroutines.delay
import org.vsu.pt.team2.utilitatemmetrisapp.api.model.LoginUser
import org.vsu.pt.team2.utilitatemmetrisapp.api.model.QuickLoginUser
import org.vsu.pt.team2.utilitatemmetrisapp.api.model.RegisterUser
import org.vsu.pt.team2.utilitatemmetrisapp.api.model.SuccessfulLoginUser
import org.vsu.pt.team2.utilitatemmetrisapp.models.User
import org.vsu.pt.team2.utilitatemmetrisapp.network.ApiResult
import org.vsu.pt.team2.utilitatemmetrisapp.network.AuthWorker
import javax.inject.Inject

class AuthManager @Inject constructor(
    val authWorker: AuthWorker,
    val sessionManager: SessionManager
) {

    suspend fun authUser(email: String, pass: String): ApiResult<SuccessfulLoginUser> {
        YandexMetrica.reportEvent(
            "Полная авторизация, запрос",
            mapOf(
                "email" to email
            )
        )
        val result = authWorker.login(LoginUser(email, pass))
        when (result) {
            is ApiResult.NetworkError -> {
                YandexMetrica.reportEvent(
                    "Полная авторизация, ошибка",
                    mapOf(
                        "email" to email,
                        "error" to "Network error"
                    )
                );
                /*showtoast internet lost*/
            }
            is ApiResult.GenericError -> {
                YandexMetrica.reportEvent(
                    "Полная авторизация, ошибка",
                    mapOf(
                        "email" to email,
                        "error" to "Generic error",
                        "code" to result.code,
                        "GenericError" to result.error,
                    )
                )
                sessionManager.clear()
            }
            is ApiResult.Success -> {
                YandexMetrica.reportEvent(
                    "Полная авторизация, успех",
                    mapOf(
                        "email" to email
                    )
                )
                sessionManager.setSession(User(email, result.value.token), false)
            }
        }
        return result
    }

    suspend fun authUser(email: String): ApiResult<SuccessfulLoginUser> {
        YandexMetrica.reportEvent(
            "Быстрая авторизация, запрос",
            mapOf("email" to email)
        )
        val result = authWorker.login(QuickLoginUser(email))
        when (result) {
            is ApiResult.NetworkError -> {
                YandexMetrica.reportEvent(
                    "Быстрая авторизация, ошибка",
                    mapOf(
                        "email" to email,
                        "error" to "Network error"
                    )
                )
                /*showtoast internet lost*/
            }
            is ApiResult.GenericError -> {
                YandexMetrica.reportEvent(
                    "Быстрая авторизация, ошибка",
                    mapOf(
                        "email" to email,
                        "error" to "Generic error",
                        "code" to result.code,
                        "GenericError" to result.error,
                    )
                )
                sessionManager.clear()
            }
            is ApiResult.Success -> {
                YandexMetrica.reportEvent(
                    "Быстрая авторизация, успех",
                    mapOf("email" to email)
                )
                sessionManager.setSession(User(email, result.value.token), true)
            }
        }
        return result
    }

    suspend fun registerUser(email: String, pass: String): ApiResult<*> {
        YandexMetrica.reportEvent(
            "Регистрация, запрос",
            mapOf("email" to email)
        )
        val result = authWorker.register(RegisterUser(email, pass))
        when (result) {
            is ApiResult.NetworkError -> {
                /*showtoast internet lost*/
                YandexMetrica.reportEvent(
                    "Регистрация, ошибка",
                    mapOf(
                        "email" to email,
                        "error" to "Network error"
                    )
                )
            }
            is ApiResult.GenericError -> {
                YandexMetrica.reportEvent(
                    "Регистрация, ошибка",
                    mapOf(
                        "email" to email,
                        "error" to "Generic error",
                        "code" to result.code,
                        "GenericError" to result.error,
                    )
                )
            }
            is ApiResult.Success -> {
                YandexMetrica.reportEvent(
                    "Регистрация, успех",
                    mapOf("email" to email)
                )
            }
        }
        return result
    }
}
