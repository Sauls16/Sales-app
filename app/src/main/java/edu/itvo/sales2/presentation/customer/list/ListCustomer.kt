package edu.itvo.sales2.presentation.customer.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.itvo.sales2.domain.model.Customer

@Composable
fun ListCustomer(
    customers: List<Customer>,
    onEditCustomer: (Customer) -> Unit,
    onDeleteCustomer: (Customer) -> Unit
) {

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = 16.dp,
            bottom = 80.dp
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = customers,
            key = { it.code }
        ) { customer ->
            CustomerItem(customer = customer,
                onEditCustomer = onEditCustomer,
                onDeleteCustomer = onDeleteCustomer
            )
        }
    }
}