package com.jinukeu.playground.homework3

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ForSaleScreen() {
    // dummyList (List 타입)을 MutableStateList 타입으로 변경시킴.
    // dummyListState가 변경되면 Compose에 알림이 간다.
    val dummyListState = remember {
        dummyList.toMutableStateList()
    }
    val listState = rememberLazyListState()

    LazyColumn(state = listState, content = {
        item(key = -1, contentType = -1) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(text = "만족하며 거래하고 있나요?")

                Spacer(modifier = Modifier.size(10.dp))

                Row {
                    Text(
                        modifier = Modifier.border(width = 2.dp, color = Color.White)
                            .padding(vertical = 5.dp, horizontal = 20.dp),
                        text = "아니요",
                    )

                    Spacer(modifier = Modifier.size(10.dp))

                    Text(
                        modifier = Modifier.background(Color.White)
                            .padding(vertical = 5.dp, horizontal = 20.dp),
                        text = "그럼요",
                    )
                }
            }
        }

        items(
            count = dummyListState.size,
            key = { dummyListState[it].id },
            itemContent = {
                ForSaleScreenCard(dummy = dummyListState[it])
            },
        )
    })

    listState.OnBottomReached {
        dummyListState.loadMore()
    }
}

@Composable
fun ForSaleScreenCard(dummy: Dummy) {
    dummy.run {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Row(
                modifier = Modifier.padding(10.dp),
            ) {
                AsyncImage(
                    modifier = Modifier.size(80.dp),
                    model = thumbnailUrl,
                    contentScale = ContentScale.Crop,
                    contentDescription = "",
                )
                Spacer(modifier = Modifier.size(10.dp))
                Column {
                    Text(text = title)
                    Text(text = address)
                    Text(text = price)
                }
            }
            Divider(thickness = 1.dp)
        }
    }
}

@Preview
@Composable
fun ForSaleScreenPreview() {
    ForSaleScreen()
}
