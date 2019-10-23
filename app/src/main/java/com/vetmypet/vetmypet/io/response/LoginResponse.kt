package com.vetmypet.vetmypet.io.response

import com.vetmypet.vetmypet.model.User

data class LoginResponse(val success: Boolean, val user: User, val jwt: String)