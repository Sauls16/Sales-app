package edu.itvo.sales2.data.remote.mapper

import edu.itvo.sales2.data.local.entity.CustomerEntity
import edu.itvo.sales2.domain.model.Customer
import edu.itvo.sales2.data.remote.dto.CustomerDto

object CustomerRemoteMapper {

    fun CustomerDto.toDomain(): Customer = Customer(
        code,name, email
    )

    fun CustomerDto.toEntity(): CustomerEntity = CustomerEntity(
        code,name, email
    )

    fun Customer.toDto(): CustomerDto = CustomerDto(
        code,name,email
    )


}