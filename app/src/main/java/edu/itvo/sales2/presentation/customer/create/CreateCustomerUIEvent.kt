package edu.itvo.sales2.presentation.customer.create

sealed interface CreateCustomerUIEvent {
    data class CodeChanged(val value: String) : CreateCustomerUIEvent
    data class NameChanged(val value: String) : CreateCustomerUIEvent
    data class EmailChanged(val value: String) : CreateCustomerUIEvent
    object SaveClicked : CreateCustomerUIEvent
}

