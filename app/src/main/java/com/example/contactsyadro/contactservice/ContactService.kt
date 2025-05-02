package com.example.contactsyadro.contactservice

import android.content.ContentResolver
import android.database.Cursor
import android.provider.ContactsContract
import com.example.contactsyadro.Contact

class ContactService(private val resolver: ContentResolver) {
    fun getContacts() : List<Contact> {
        val lst = mutableListOf<Contact>()
        contactsIds()?.use { contactCursor ->
            val idIdx = contactCursor.getColumnIndex(ContactsContract.RawContacts._ID)
            val nameIdx = contactCursor.getColumnIndex(ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY)
            while (contactCursor.moveToNext()) {
                val id = contactCursor.getString(idIdx)
                val name = contactCursor.getString(nameIdx)
                contactsPhones(id)?.use { phoneCursor ->
                    val phoneIdx = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                    while (phoneCursor.moveToNext()) {
                        val number = phoneCursor.getString(phoneIdx)
                        lst.add(Contact(id, name, number))
                    }
                }
            }
        }
        return lst
    }
    private fun contactsIds() : Cursor? {
        val idUri = ContactsContract.RawContacts.CONTENT_URI
        val projection = arrayOf(
            ContactsContract.RawContacts._ID,
            ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY,
        )
        val sortOrder = "${ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY} ASC"
        return resolver.query(
            idUri,
            projection,
            null,
            null,
            sortOrder,
        )
    }
    private fun contactsPhones(id: String) : Cursor? {
        val phonesUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val projection = arrayOf(
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )
        val selection = "${ContactsContract.CommonDataKinds.Phone.RAW_CONTACT_ID} = ?"
        val selectionArg = arrayOf(id)
        val cursor = resolver.query(
            phonesUri,
            projection,
            selection,
            selectionArg,
            null,
        )
        return cursor
    }
}