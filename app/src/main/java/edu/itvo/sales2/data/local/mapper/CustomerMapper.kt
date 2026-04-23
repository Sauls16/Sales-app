package edu.itvo.sales2.data.local.mapper

import edu.itvo.sales2.data.local.entity.CustomerEntity
import edu.itvo.sales2.domain.model.Customer


fun CustomerEntity.toDomain(): Customer {
    return Customer(
        code,
        name,
        email
    )
}

fun Customer.toEntity():CustomerEntity{
    return CustomerEntity(
        code,
        name,
        email
    )
}
