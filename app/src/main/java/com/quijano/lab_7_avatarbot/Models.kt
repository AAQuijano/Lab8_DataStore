package com.quijano.lab_7_avatarbot

data class ProfileInfo(
    val name: String,
    val role: String,
    val year: Int
)

data class ContactInfo(
    val phone: String,
    val handle: String,
    val email: String,
    val showContactInfo: Boolean
)