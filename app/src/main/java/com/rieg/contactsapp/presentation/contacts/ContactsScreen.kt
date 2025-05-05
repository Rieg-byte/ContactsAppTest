package com.rieg.contactsapp.presentation.contacts

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rieg.contactsapp.R
import com.rieg.contactsapp.domain.model.Contact
import com.rieg.contactsapp.ui.theme.ContactsAppTheme
import com.rieg.contactsapp.ui.theme.toHslColor


@Composable
fun ContactsScreen(
    contactsViewModel: ContactsViewModel = viewModel(),
    context: Context
) {
    val contactsUiState by contactsViewModel.contactsUiState.collectAsState()
    Scaffold(
        topBar = { ContactsTopBar() }
    ) { innerPadding ->
        ContactListScreen(
            contacts = contactsUiState.contacts,
            context = context,
            modifier = Modifier.padding(innerPadding)
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactsTopBar(
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier,
        title = { Text(text = stringResource(R.string.contacts))}
    )
}

@Composable
fun ContactListScreen(
    contacts: Map<Char, List<Contact>>,
    context: Context,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        contacts.forEach { (initial, contactsForInitial) ->
            stickyHeader {
                InitialHeader(initial)
            }

            items(contactsForInitial) { contact ->
                ContactItem(
                    contact,
                    onClick = {
                        val intent = Intent(Intent.ACTION_CALL, "tel:${contact.phoneNumber}".toUri())
                        context.startActivity(intent)
                    }
                )
            }

        }
    }
}

@Composable
fun InitialHeader(
    initial: Char,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(start = 12.dp, top = 6.dp, bottom = 6.dp)
    ) {
        Text(
            text = initial.toString(),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ContactItem(
    contact: Contact,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            UserAvatar(
                id = contact.id,
                name = contact.name,
            )
            Column(modifier = Modifier.padding(12.dp)) {
                Text(text = contact.name, fontSize = 18.sp)
                Text(text = contact.phoneNumber, fontSize = 14.sp, color = Color.Gray)
            }
        }
    }
}

@Composable
fun UserAvatar(
    id: String,
    name: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
) {
    Box(modifier.size(50.dp), contentAlignment = Alignment.Center) {
        val color = Color("$id / $name".toHslColor())
        val initial = name.take(1).uppercase()
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(SolidColor(color))
        }
        Text(text = initial, style = textStyle, color = Color.White)
    }

}


@Preview(showBackground = true)
@Composable
private fun ContactListScreenPreview() {
    val testContactsData = mapOf<Char, List<Contact>>(
        'A' to listOf<Contact>(
            Contact(
                id = "2",
                name = "Alexey",
                phoneNumber = "8 800 777 50 50"
            ),
            Contact(
                id = "3",
                name = "Andrey",
                phoneNumber = "8 800 090 19 50"
            ),
        ),
        'B' to listOf<Contact>(
            Contact(
                id = "5",
                name = "Boris",
                phoneNumber = "8 800 240 20 22"
            ),
        )
    )
    ContactsAppTheme {
        ContactListScreen(
            contacts = testContactsData,
            context = LocalContext.current
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ContactItemPreview() {
    ContactsAppTheme {
        ContactItem(
            contact = Contact(
                id = "id",
                name = "Ivan",
                phoneNumber = "8 800 999 50 50"
            ),
            onClick = { }
        )
    }
}