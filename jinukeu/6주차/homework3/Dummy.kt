package com.jinukeu.playground.homework3

import java.util.UUID

val dummyList = listOf<Dummy>()

data class Dummy(
    val id: UUID,
    val thumbnailUrl: String,
    val title: String,
    val address: String,
    val price: String,
)
