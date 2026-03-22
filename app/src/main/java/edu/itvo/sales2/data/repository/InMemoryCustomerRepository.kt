package edu.itvo.sales2.data.repository

import edu.itvo.sales2.domain.model.Customer
import edu.itvo.sales2.domain.repository.CustomerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InMemoryCustomerRepository @Inject constructor():
        BaseInMemoryRepository<Customer,String>(
            initialData = listOf(
                Customer("1","Diego Hernandez","diego@gmaiñ.com"),
                Customer("2","Pedro Sanchez","pedro@gmail.com"),
                Customer("3","Alejandra Lopez","alejandra@gmail.com")
            )
        ),
        CustomerRepository{

            override fun getId(item: Customer): String = item.code

            override fun observeAll(): Flow<List<Customer>> = state

            override suspend fun findCustomerByCode(customerCode: String): Customer? {
                return findById(customerCode)
            }

            override suspend fun saveCustomer(customer: Customer) {
                save(customer)
            }

            override suspend fun deleteCustomer(customerCode: String) {
                deleteById(customerCode)
            }

            override fun getCustomers(): Flow<List<Customer>> = observeAll()
        }
