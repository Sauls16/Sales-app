package edu.itvo.sales2.data.remote.datasource

import edu.itvo.sales2.data.remote.api.ApiResponse
import edu.itvo.sales2.data.remote.api.CustomerApiService
import edu.itvo.sales2.data.remote.dto.CustomerDto
import javax.inject.Inject

class CustomerRemoteDatasource @Inject constructor(
    private val api: CustomerApiService
){
    suspend fun findCustomerByID(code: String): CustomerDto {
        return api.findCustomerById(code)
    }

    suspend fun saveCustomer(customer: CustomerDto): CustomerDto{
        return api.saveCustomer(customer)
    }
                                        //ApiResponse
    suspend fun getCustomers(): ApiResponse<List<CustomerDto>> {
        return api.getCustomers()
    }
}

