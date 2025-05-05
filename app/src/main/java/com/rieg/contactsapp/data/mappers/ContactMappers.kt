package com.rieg.contactsapp.data.mappers

import com.rieg.contactsapp.data.dto.ContactDto
import com.rieg.contactsapp.domain.model.Contact

fun ContactDto.toContact(): Contact {
    return Contact(
        id = id,
        name = name,
        phoneNumber = phoneNumber
    )
}