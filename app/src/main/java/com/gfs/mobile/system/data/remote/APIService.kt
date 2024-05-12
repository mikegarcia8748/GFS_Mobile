package com.gfs.mobile.system.data.remote

import com.gfs.mobile.system.base.BaseResponse
import com.gfs.mobile.system.data.model.authentication.AuthenticationMPINModel
import com.gfs.mobile.system.data.model.authorizeusers.AuthorizeUsers
import com.gfs.mobile.system.data.model.customer.CustomerModel
import com.gfs.mobile.system.data.param.AddCustomerParams
import com.gfs.mobile.system.data.remote.param.AuthenticateMPINParams
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface APIService {

    // region Authentication

    /**
     *
     * Get Authorize Users...
     *
     */
    @GET("/account/get_mill_users")
    suspend fun getAuthorizeUsers() : Response<BaseResponse<List<AuthorizeUsers>>>

    /**
     *
     * Authenticate MPIN
     *
     */
    @POST("/account/authenticate_mpin")
    suspend fun authenticateMPIN(
        @Body body: AuthenticateMPINParams
    ): Response<BaseResponse<AuthenticationMPINModel>>

    // endregion Authentication


    // region Customer

    /**
     *
     * Search Customer
     *
     */
    @GET("/customers/search/{name}")
    suspend fun searchCustomer(
        @Path("name") name: String
    ): Response<BaseResponse<List<CustomerModel>>>

    /**
     *
     * Search Customer
     *
     */
    @POST("/customers/add_customer")
    suspend fun addCustomer(
        @Body param: AddCustomerParams
    ): Response<BaseResponse<CustomerModel>>
}