package com.example.a6_2.data.remote.mapper

import com.example.a6_2.data.remote.dto.UserDto
import com.example.a6_2.domain.entity.User

fun UserDto.toEntity(): User = User(
    id = id,
    username = username,
    role = role
)
