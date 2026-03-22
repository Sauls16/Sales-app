package edu.itvo.sales2.presentation.customer.create

sealed interface CreateCustomerUIEffect {
    data class ShowError(val message: String) : CreateCustomerUIEffect
    data class ShowSuccess(val message: String) : CreateCustomerUIEffect
    object NavigateBack : CreateCustomerUIEffect
}
