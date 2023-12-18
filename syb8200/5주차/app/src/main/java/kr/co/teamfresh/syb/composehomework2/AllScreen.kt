package kr.co.teamfresh.syb.composehomework2

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AllScreen(itemList: List<ItemData>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        items(itemList.size) { item ->
            AllScreenItem(itemData = itemList[item])
        }
    }
}

@Composable
fun AllScreenItem(
    modifier: Modifier = Modifier,
    itemData: ItemData,
) {
    Box(
        modifier = modifier.size(170.dp),
    ) {
        Image(
            painter = painterResource(id = itemData.imageId),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
        Box(
            modifier = modifier
                .padding(10.dp)
                .background(Color.Black.copy(alpha = 0.5f))
                .align(Alignment.BottomStart),
        ) {
            Text(
                text = itemData.type,
                fontSize = 10.sp,
                color = Color.White,
                modifier = modifier.padding(horizontal = 6.dp, vertical = 4.dp),
            )
        }
    }
}

@Preview
@Composable
fun AllScreenPreview() {
    AllScreen(itemList)
}
