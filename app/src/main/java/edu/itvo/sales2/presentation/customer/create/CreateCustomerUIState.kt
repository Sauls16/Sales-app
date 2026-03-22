package edu.itvo.sales2.presentation.customer.create

data class CreateCustomerUIState(
    val code: String = "",
    val name: String = "",
    val email:String = "",
    val isLoading: Boolean = false
)