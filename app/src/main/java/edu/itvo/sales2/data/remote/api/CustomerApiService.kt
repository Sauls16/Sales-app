package edu.itvo.sales2.data.remote.api

import edu.itvo.sales2.data.remote.dto.CustomerDto
import edu.itvo.sales2.data.remote.dto.ProductDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CustomerApiService {
    @GET("customers")
    suspend fun getCustomers(): ApiResponse<List<CustomerDto>> //apiResponse para .data

    @GET("customers/{id}")
    suspend fun findCustomerById(
        @Path("code") code: String
    ): CustomerDto

    @POST("customers")
    suspend fun saveCustomer(
        @Body customer: CustomerDto
    ): CustomerDto

    @PUT("customers/{id}")
    suspend fun updateCustomer (  @Body customer: CustomerDto): CustomerDto

    @DELETE("customers/{id}")
    suspend fun deleteCustomer( @Path("id") id: String)


}
