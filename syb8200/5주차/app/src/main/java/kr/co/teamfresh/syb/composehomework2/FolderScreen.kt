package kr.co.teamfresh.syb.composehomework2

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FolderScreen(itemList: List<ItemData>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        item {
            FolderScreenHeader()
        }

        items(itemList.size) { item ->
            FolderScreenItem(itemData = itemList[item])
        }
    }
}

@Composable
fun FolderScreenHeader(
    modifier: Modifier = Modifier,
) {
    val stroke = Stroke(
        width = 2f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f),
    )

    Box(
        modifier = modifier
            .size(170.dp)
            .drawBehind {
                drawRoundRect(color = Color.DarkGray, style = stroke)
            },
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "폴더 추가",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
            )
            Spacer(modifier = modifier.size(10.dp))
            Box(
                modifier = modifier
                    .size(28.dp)
                    .clip(shape = CircleShape)
                    .background(Color(0xFF00CEF4)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = Color.White,
                )
            }
        }
    }
}

@Composable
fun FolderScreenItem(
    modifier: Modifier = Modifier,
    itemData: ItemData,
) {
    Box(
        modifier = modifier
            .size(170.dp),
    ) {
        Image(
            painter = painterResource(id = itemData.imageId),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = itemData.usage,
                fontSize = 16.sp,
                color = Color.White,
            )
            Spacer(modifier = modifier.size(12.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = modifier.size(16.dp),
                )
                Spacer(modifier = modifier.size(10.dp))
                Text(
                    text = "1",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

@Preview
@Composable
fun FolderScreenPreview() {
    FolderScreen(itemList)
}
