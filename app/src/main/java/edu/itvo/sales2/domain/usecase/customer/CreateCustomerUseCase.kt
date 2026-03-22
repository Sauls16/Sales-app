package edu.itvo.sales2.domain.usecase.customer

import edu.itvo.sales2.domain.model.Customer
import edu.itvo.sales2.domain.repository.CustomerRepository
import javax.inject.Inject

class CreateCustomerUseCase @Inject constructor(
    private val repository: CustomerRepository
){
    suspend operator fun invoke(customer: Customer){
        val existing=repository.findCustomerByCode(customer.code)

        require (existing==null){
            "Customer with id: ${customer.code} already exists"
        }
        repository.saveCustomer(customer)
    }

}