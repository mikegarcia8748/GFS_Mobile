package com.gfs.mobile.system.data.remote

import com.gfs.mobile.system.base.BaseResponse
import com.gfs.mobile.system.data.model.authentication.AuthenticationMPINModel
import com.gfs.mobile.system.data.model.authorizeusers.AuthorizeUsers
import com.gfs.mobile.system.data.model.customer.CustomerModel
import com.gfs.mobile.system.data.model.price.ChaffPriceModel
import com.gfs.mobile.system.data.model.price.MillPriceModel
import com.gfs.mobile.system.data.param.AddChaffPriceParams
import com.gfs.mobile.system.data.param.AddCustomerParams
import com.gfs.mobile.system.data.param.AddMillPriceParams
import com.gfs.mobile.system.data.param.MillTransactionParams
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
     * Add Customer
     *
     */
    @POST("/customers/add_customer")
    suspend fun addCustomer(
        @Body param: AddCustomerParams
    ): Response<BaseResponse<CustomerModel>>

    /**
     *
     * Search Customer
     *
     */
    @GET("/customers/get_customers/{index}")
    suspend fun getCustomers(
        @Path("index") index: Int
    ): Response<BaseResponse<List<CustomerModel>>>

    // endregion Customer

    // region Pricing

    /**
     *
     * Add Mill Price
     *
     */
    @POST("/millpricing/add_price")
    suspend fun addMillPrice(
        @Body params: AddMillPriceParams
    ): Response<BaseResponse<MillPriceModel>>

    /**
     *
     * Get Mill Price
     *
     */
    @GET("/millpricing/get_mill_price")
    suspend fun getMillPrice(): Response<BaseResponse<List<MillPriceModel>>>

    /**
     *
     * Add Chaff Price
     *
     */
    @POST("/chaffpricing/add_price")
    suspend fun addChaffPrice(
        @Body params: AddChaffPriceParams
    ): Response<BaseResponse<ChaffPriceModel>>

    /**
     *
     * Get Chaff Price
     *
     */
    @GET("/chaffpricing/get_chaff_price")
    suspend fun getChaffPrice(): Response<BaseResponse<List<ChaffPriceModel>>>

    // endregion Pricing

    // region Mill Transaction

    /**
     *
     * Get Chaff Price
     *
     */
    @POST("/milltransaction/save_billing")
    suspend fun saveMillTransaction(
        @Body params: MillTransactionParams
    ): Response<BaseResponse<Unit>>

    // endregion Mill Transaction
}