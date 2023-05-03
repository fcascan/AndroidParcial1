package com.fcascan.parcial1.enums

enum class Permissions {
    ADMIN,
    USER,
    GUEST,
    ERROR
}

fun getPermissionsFromString(permissions: String): Permissions {
    return when (permissions) {
        "ADMIN" -> Permissions.ADMIN
        "USER" -> Permissions.USER
        "GUEST" -> Permissions.GUEST
        else -> Permissions.ERROR
    }
}