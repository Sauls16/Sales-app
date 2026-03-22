package edu.itvo.sales2.domain.validation

import edu.itvo.sales2.domain.model.Customer
import edu.itvo.sales2.presentation.product.ValidationResult

class CustomerValidator {
    operator fun invoke(customer: Customer): ValidationResult ? =
        listOfNotNull(
            "id required".takeIf { customer.code.isBlank() },
            "name required".takeIf { customer.name.isBlank() },
            "email required".takeIf { customer.email.isBlank() }
        ).firstOrNull()
            ?.let { ValidationResult.Error(it) }
            ?: ValidationResult.Success
}