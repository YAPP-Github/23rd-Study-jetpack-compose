package com.example.composehomework3

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composehomework3.ui.theme.ComposeHomework3Theme
import java.text.DecimalFormat

@Composable
fun ForSaleScreen(
    itemList: List<ItemData>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 2.dp),
    ) {
        item {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(Color(0xFFF0883B)),
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = modifier.align(Alignment.Center),
                ) {
                    Text(
                        text = "만족하며 거래하고 있나요?",
                        fontSize = 16.sp,
                        color = Color.White,
                    )
                    Spacer(modifier = modifier.size(8.dp))
                    Row {
                        Text(
                            text = "아니요",
                            fontSize = 16.sp,
                            color = Color.White,
                            modifier = modifier
                                .border(
                                    width = 1.dp,
                                    color = Color.White,
                                    shape = RectangleShape,
                                )
                                .padding(horizontal = 36.dp, vertical = 6.dp),
                        )
                        Spacer(modifier = modifier.size(6.dp))
                        Text(
                            text = "그럼요",
                            fontSize = 16.sp,
                            color = Color(0xFFF0883B),
                            modifier = modifier
                                .background(Color.White)
                                .padding(horizontal = 36.dp, vertical = 6.dp),
                        )
                    }
                }
            }
        }
        for (i in 1..20) {
            itemsIndexed(itemList) { index, item ->
                ForSaleItem(itemData = item)
                Divider(
                    thickness = 1.dp,
                    color = Color.LightGray,
                    modifier = modifier.padding(horizontal = 10.dp)
                )
            }
        }
    }
}

@Composable
fun ForSaleItem(
    itemData: ItemData,
    modifier: Modifier = Modifier,
) {
    val dec = DecimalFormat("#,###")

    Row(
        modifier = modifier.padding(horizontal = 10.dp, vertical = 12.dp),
    ) {
        Image(
            painter = painterResource(id = itemData.imageId),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(110.dp)
                .clip(shape = CutCornerShape(4.dp)),
        )
        Spacer(modifier = modifier.size(14.dp))
        Column(
            modifier = modifier.weight(1f),
        ) {
            Text(
                text = itemData.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = modifier.size(6.dp))
            Text(
                text = "${itemData.location} • ${itemData.time}",
                fontSize = 14.sp,
                color = Color.Gray,
            )
            Spacer(modifier = modifier.size(6.dp))
            Text(
                text = "${dec.format(itemData.price)}원",
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ForSalePreview() {
    ComposeHomework3Theme {
        ForSaleScreen(itemList = forSaleItemList)
    }
}
