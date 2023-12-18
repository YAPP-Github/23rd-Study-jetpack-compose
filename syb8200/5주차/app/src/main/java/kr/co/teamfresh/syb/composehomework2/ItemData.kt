package kr.co.teamfresh.syb.composehomework2

import androidx.annotation.DrawableRes

data class ItemData(
    @DrawableRes val imageId: Int,
    val type: String,
    val usage: String,
)

val itemList = listOf(
    ItemData(R.drawable.img_item_1, "상품", "싱크대"),
    ItemData(R.drawable.img_item_2, "상품", "컵"),
    ItemData(R.drawable.img_item_3, "상품", "조리용품"),
    ItemData(R.drawable.img_item_4, "상품", "조리용품"),
    ItemData(R.drawable.img_item_5, "상품", "싱크대"),
    ItemData(R.drawable.img_item_6, "상품", "컵"),
)