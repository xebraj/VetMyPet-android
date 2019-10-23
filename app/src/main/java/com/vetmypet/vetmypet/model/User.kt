package com.vetmypet.vetmypet.model

/*
        "id": 2,
        "name": "Patient",
        "last_name": "Test",
        "email": "patient@gmail.com",
        "ci": "28434234",
        "address": "",
        "phone_number": "",
        "role": "patient"
 */

data class User(
    val id: Int,
    val name: String,
    val last_name: String,
    val email: String,
    val ci: String,
    val address: String,
    val phone_number: String,
    val role: String
)