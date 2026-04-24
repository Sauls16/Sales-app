package edu.itvo.sales2.data.repository

import android.util.Log
import edu.itvo.sales2.data.local.datasource.CustomerLocalDataSource
import edu.itvo.sales2.data.local.mapper.toDomain
import edu.itvo.sales2.data.local.mapper.toEntity
import edu.itvo.sales2.data.remote.datasource.CustomerRemoteDatasource
import edu.itvo.sales2.data.remote.mapper.CustomerRemoteMapper.toDomain
import edu.itvo.sales2.data.remote.mapper.CustomerRemoteMapper.toDto
import edu.itvo.sales2.data.remote.mapper.CustomerRemoteMapper.toEntity
import edu.itvo.sales2.domain.model.Customer
import edu.itvo.sales2.domain.repository.CustomerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CustomerRepositoryImpl @Inject constructor(
    private val remote: CustomerRemoteDatasource,
    private val local: CustomerLocalDataSource
): CustomerRepository {

    override fun getCustomers(): Flow<List<Customer>> = flow {
        //Intentar Actualizar desde Api
        try {
            val customers = remote.getCustomers().data
                .map {it.toDomain() }
            Log.d(
                "CUSTOMERS",
                customers.joinToString (separator = "\n") {customer ->
                    "code=${customer.code}, name=${customer.name}, email=${customer.email}"
                }
            )
            local.replaceAll(customers.map { it.toEntity() })
        } catch (e: Exception){
            Log.e("CUSTOMERS_ERROR", e.message ?: "Unknow error", e)
        }

        //Emitir datos locales
        emitAll(
            local.getCustomers()
                .map {list -> list.map{it.toDomain()}}
        )
    }

    override suspend fun findCustomerByCode(customerCode: String): Customer? {
        //Buscar en local primero
        val localCustomer = local.findCustomerByCode(customerCode)
        if(localCustomer != null){
            return localCustomer.toDomain()
        }
        //Si no existe, buscar remoto
        return try {
            val remoteCustomer = remote.findCustomerByID(customerCode)

            //save local
            local.saveCustomer(remoteCustomer.toEntity())
            remoteCustomer.toDomain()
        } catch (e: Exception){
            null
        }
    }

    override suspend fun saveCustomer(customer: Customer) {
        try {
            remote.saveCustomer(customer.toDto())
            local.saveCustomer(customer.toEntity())
        } catch (e: Exception){
            local.saveCustomer(customer.toEntity())
        }
    }

    override suspend fun deleteCustomer(customerCode: String) {
       try {
           //Eliminar del webservice primero
           remote.deleteCustomer(customerCode)

            // Si falla, eliminamos de room
           local.deleteCustomer(customerCode)

           Log.d("DELETE_SYNC", "Cliente $customerCode eliminado de forma remota y local")
       } catch (e: Exception){
           Log.e("DELETE_ERROR", "No se pudo eliminar del servidor: ${e.message}")
       }
    }


}
