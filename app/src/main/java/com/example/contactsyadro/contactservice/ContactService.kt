package com.example.contactsyadro.contactservice

import android.content.Context
import android.net.Uri
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.Phone
import com.example.contactsyadro.Contact

class ContactService {
    fun getContacts(context: Context) : List<Contact> {
        val contentUri = Uri.withAppendedPath(
            ContactsContract.Contacts.CONTENT_URI,
            ContactsContract.Contacts.Entity.CONTENT_DIRECTORY
        )
        val resolver = context.contentResolver
        val projection = arrayOf(
            ContactsContract.Contacts.Entity._ID,
            ContactsContract.Contacts.Entity.DISPLAY_NAME,
            Phone.NUMBER,
        )
        val selectionClause = "${ContactsContract.Data.MIMETYPE} = '${Phone.CONTENT_ITEM_TYPE}'"
        val sortOrder = "${ContactsContract.Contacts.DISPLAY_NAME} ASC"

        val cursor = resolver.query(
            contentUri,
            projection,
            selectionClause,
            null,
            sortOrder,
        )
        val lst = mutableListOf<Contact>()
        cursor?.use {
            val (idIdx, nameIdx, numberIdx) = projection.map { name -> it.getColumnIndex(name) }
            while (it.moveToNext()) {
                lst.add(Contact(it.getString(idIdx), it.getString(nameIdx), it.getString(numberIdx)))
            }

        }
        return lst
    }
}