package com.jinukeu.playground.homework2

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

enum class FolderItemType {
    ADD, CONTENT,
}

data class FolderItem(
    val key: Int = 0,
    val type: FolderItemType,
    val name: String = "",
    val count: Int = 0,
    val thumbnailUrl: String = "",
)

@Composable
fun FolderScreen() {
    val folderItemList = listOf(
        FolderItem(
            key = 0,
            type = FolderItemType.ADD,
            name = "폴더 추가",
        ),
        FolderItem(
            key = 1,
            type = FolderItemType.CONTENT,
            name = "수납",
            count = 3,
            thumbnailUrl = "https://img.freepik.com/free-photo/modern-styled-entryway_23-2150695879.jpg?size=626&ext=jpg",
        ),
        FolderItem(
            key = 2,
            type = FolderItemType.CONTENT,
            name = "커튼",
            count = 2,
            thumbnailUrl = "https://img.freepik.com/free-photo/modern-styled-entryway_23-2150695879.jpg?size=626&ext=jpg",
        ),
        FolderItem(
            key = 3,
            type = FolderItemType.CONTENT,
            name = "위시리스트",
            thumbnailUrl = "https://img.freepik.com/free-photo/modern-styled-entryway_23-2150695879.jpg?size=626&ext=jpg",
        ),
        FolderItem(
            key = 4,
            type = FolderItemType.CONTENT,
            name = "스크랩북",
        ),
    )

    LazyVerticalGrid(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxSize(),
        contentPadding = PaddingValues(vertical = 20.dp),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        items(
            count = folderItemList.size,
            key = { folderItemList[it].key },
            contentType = { folderItemList[it].type },
        ) {
            if (folderItemList[it].type == FolderItemType.ADD) {
                FolderAddCard(
                    folderItem = folderItemList[it],
                    onClick = { },
                )
            } else {
                FolderContentCard(
                    folderItem = folderItemList[it],
                    onClick = { },
                )
            }
        }
    }
}

@Composable
fun FolderAddCard(
    modifier: Modifier = Modifier,
    folderItem: FolderItem,
    onClick: (FolderItem) -> Unit = {},
) {
    val (strokeWidthDp, dashWidthDp, gapWidthDp) = listOf(2.dp, 8.dp, 4.dp)

    val (strokeWidthPx, dashWidthPx, gapWidthPx) = with(LocalDensity.current) {
        listOf(
            strokeWidthDp.toPx(),
            dashWidthDp.toPx(),
            gapWidthDp.toPx(),
        )
    }

    Box(
        modifier = modifier
            .height(160.dp)
            .clickable(onClick = { onClick(folderItem) }),
    ) {
        val stroke = Stroke(
            width = strokeWidthPx,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(dashWidthPx, gapWidthPx), 0f),
        )
        Canvas(Modifier.fillMaxSize().padding(strokeWidthDp / 2)) {
            drawRoundRect(color = Color.Blue, style = stroke)
        }

        Column(
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = folderItem.name, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
            Spacer(modifier = Modifier.size(10.dp))
            Icon(
                modifier = Modifier.size(36.dp),
                imageVector = Icons.Default.AddCircle,
                contentDescription = "",
                tint = Color.Blue,
            )
        }
    }
}

@Composable
fun FolderContentCard(
    modifier: Modifier = Modifier,
    folderItem: FolderItem,
    onClick: (FolderItem) -> Unit = {},
) {
    Box(
        modifier = modifier
            .height(160.dp)
            .clickable(onClick = { onClick(folderItem) }),
    ) {
        AsyncImage(
            model = folderItem.thumbnailUrl,
            contentScale = ContentScale.Crop,
            contentDescription = "",
        )
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color.Gray.copy(alpha = 0.5f)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = folderItem.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
            )
            Spacer(modifier = Modifier.size(10.dp))
            Row {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "",
                    tint = Color.White,
                )

                Spacer(modifier = Modifier.size(8.dp))

                Text(
                    text = "${folderItem.count}",
                    fontSize = 18.sp,
                    color = Color.White,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FolderContentCardPreview() {
    FolderContentCard(
        folderItem = FolderItem(
            key = 1,
            type = FolderItemType.CONTENT,
            name = "수납",
            count = 3,
            thumbnailUrl = "https://img.freepik.com/free-photo/modern-styled-entryway_23-2150695879.jpg?size=626&ext=jpg",
        ),
    )
}

@Preview(showBackground = true)
@Composable
fun FolderAddCardPreview() {
    FolderAddCard(
        folderItem = FolderItem(
            key = 0,
            type = FolderItemType.ADD,
            name = "폴더 추가",
        ),
    )
}

@Preview(showSystemUi = true)
@Composable
fun FolderScreenPreview() {
    FolderScreen()
}
