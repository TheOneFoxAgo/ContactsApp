package com.example.contactsyadro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.contactsyadro.contactservice.ContactService
import com.example.contactsyadro.ui.theme.ContactsYadroTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val service = ContactService()
        val contacts = service.getContacts(this)
//            listOf(
//            Contact("1", "first", "+777777777"),
//            Contact("2", "second", "+888888888"),
//            Contact("3", "third", "+999999999"),
//        )
        setContent {
            ContactsYadroTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text("Ядерные контакты")
                            }
                        )
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Contacts(
                        contacts,
                        Modifier.padding(innerPadding)
                    ) { }

                }
            }
        }
    }
}

data class Contact(val id: String, val name: String, val number: String)

@Composable
fun Contacts(
    contacts: List<Contact>,
    modifier: Modifier = Modifier,
    onTap: (Contact) -> Unit,
) {
    LazyColumn(modifier) {
        items(contacts) { contact ->
            ContactBox(contact, Modifier.padding(8.dp), onTap)
            HorizontalDivider()
        }
    }
}

@Composable
fun ContactBox(
    contact: Contact,
    modifier: Modifier = Modifier,
    onTap: (Contact) -> Unit,
) {
    Column(
        modifier
            .fillMaxWidth()
            .clickable { onTap(contact) }) {
        Text(contact.name, fontWeight = FontWeight.Bold)
        Text(contact.number)
    }
}