package com.gfs.mobile.core.data.data.repository

import com.gfs.mobile.core.domain.model.authentication.AuthenticationMPINModel
import com.gfs.mobile.core.domain.model.authorizeusers.AuthorizeUsers
import com.gfs.mobile.core.domain.repository.AuthenticationRepository
import com.gfs.mobile.core.domain.util.BaseResponse
import com.gfs.mobile.core.domain.util.NetworkResource
import com.gfs.mobile.core.data.data.local.preferences.user.auth.AuthenticationCache
import com.gfs.mobile.core.data.data.local.preferences.user.previoususer.PreviousUserCache
import com.gfs.mobile.core.data.data.remote.APIService
import com.gfs.mobile.core.data.data.remote.networkBoundResource
import com.gfs.mobile.core.domain.model.param.AuthenticateMPINParams
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val apiService: APIService,
    private val firebaseAuth: FirebaseAuth,
    private val authenticationCache: AuthenticationCache,
    private val previousUserCache: PreviousUserCache
) : AuthenticationRepository {

    override fun getAuthorizeUsers(): Flow<NetworkResource<BaseResponse<List<AuthorizeUsers>>>> = networkBoundResource(
        fetch = {
            apiService.getAuthorizeUsers()
        }
    )

    override fun authenticateMPIN(
        userName: String,
        mpin: String
    ): Flow<NetworkResource<BaseResponse<AuthenticationMPINModel>>> = networkBoundResource(
        fetch = {

            val authenticateMPINParams = AuthenticateMPINParams(
                userName = userName,
                mpin = mpin
            )
            apiService.authenticateMPIN(authenticateMPINParams)
        }
    )

    override fun signInWithCustomToken(token: String): Flow<NetworkResource<Unit>> = flow {
        emit(NetworkResource.Loading())
        try {
            firebaseAuth.signInWithCustomToken(token).await()
            emit(NetworkResource.Success(Unit))
        } catch (e: Exception) {
            emit(NetworkResource.Error(e))
        }
    }

    override fun signInWithEmail(email: String, pass: String): Flow<NetworkResource<Unit>> = flow {
        emit(NetworkResource.Loading())
        try {
            firebaseAuth.signInWithEmailAndPassword(email, pass).await()
            emit(NetworkResource.Success(Unit))
        } catch (e: Exception) {
            emit(NetworkResource.Error(e))
        }
    }

    override fun signOut(): Flow<NetworkResource<Unit>> = flow {
        firebaseAuth.signOut()
        emit(NetworkResource.Success(Unit))
    }

    override fun isUserAuthenticated(): Flow<Boolean> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser != null)
        }
        firebaseAuth.addAuthStateListener(listener)
        awaitClose { firebaseAuth.removeAuthStateListener(listener) }
    }

    override fun getAuthenticationToken(): Flow<AuthenticationMPINModel?> = authenticationCache.getAuthenticationCache()

    override suspend fun saveAuthenticationToken(
        value: AuthenticationMPINModel
    ) = authenticationCache.saveAuthentication(value)

    override suspend fun savePreviousUsername(
        value: String
    ) = previousUserCache.savePreviousUser(value)

    override fun getPreviousUser(): Flow<String?> = previousUserCache.getPreviousUser()
}
