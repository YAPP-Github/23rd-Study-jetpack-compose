package com.example.composehomework3

import androidx.annotation.DrawableRes

data class ItemData(
    @DrawableRes val imageId: Int,
    val title: String,
    val location: String,
    val time: String,
    val price: Int,
    val comment: Int,
    val like: Int,
)

val forSaleItemList = listOf(
    ItemData(
        imageId = R.drawable.img_item_1,
        title = "빈폴 남성 긴팔 폴로셔츠 105 사이즈",
        location = "신정4동",
        time = "Just now",
        price = 12000,
        comment = 0,
        like = 0,
    ),
    ItemData(
        imageId = R.drawable.img_item_2,
        title = "스파오 포맨 패딩 남자패딩 블루종",
        location = "신정4동",
        time = "Boosted Just now",
        price = 25000,
        comment = 1,
        like = 5,
    ),
    ItemData(
        imageId = R.drawable.img_item_3,
        title = "몽크로스 카드/명함 지갑 새제품",
        location = "신정2동",
        time = "6 sec ago",
        price = 20000,
        comment = 0,
        like = 0,
    ),
    ItemData(
        imageId = R.drawable.img_item_4,
        title = "가포 휴대용 칫솔 살균기 gapo",
        location = "신정2동",
        time = "Boosted 6 sec ago",
        price = 4000,
        comment = 0,
        like = 13,
    ),
)
