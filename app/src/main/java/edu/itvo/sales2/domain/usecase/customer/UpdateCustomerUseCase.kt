package edu.itvo.sales2.domain.usecase.customer

import edu.itvo.sales2.domain.model.Customer
import edu.itvo.sales2.domain.repository.CustomerRepository
import javax.inject.Inject

class UpdateCustomerUseCase @Inject constructor(
    private val repository: CustomerRepository){

    suspend fun invoke(customer: Customer) = repository.saveCustomer(customer)
}