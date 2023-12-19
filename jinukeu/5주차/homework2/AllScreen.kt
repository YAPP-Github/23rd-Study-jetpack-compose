package com.jinukeu.playground.homework2

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun AllScreen() {
    val allItemList = listOf(
        "https://img.freepik.com/free-photo/modern-styled-entryway_23-2150695879.jpg?size=626&ext=jpg",
        "https://img.freepik.com/free-photo/strategic-meeting-room-setting-staged-for-crucial-business-decisions_91128-3499.jpg?size=626&ext=jpg",
        "https://img.freepik.com/free-photo/mockup-frames-in-living-room-interior-with-chair-and-decorscandinavian-style_41470-5148.jpg?size=626&ext=jpg",
        "https://img.freepik.com/free-photo/white-sideboard-in-living-room-interior-with-copy-space_43614-800.jpg?size=626&ext=jpg",
        "https://img.freepik.com/free-photo/empty-chair-decoration-in-living-room-interior_74190-2080.jpg?size=626&ext=jpg&ga=GA1.1.1812542365.1702094613&semt=sph",
        "https://img.freepik.com/free-photo/film-screen-with-bean-bag-chairs-on-rooftop_91128-3608.jpg?size=626&ext=jpg&ga=GA1.1.1812542365.1702094613&semt=sph",
        "https://img.freepik.com/free-photo/modern-styled-entryway_23-2150695879.jpg?size=626&ext=jpg",
        "https://img.freepik.com/free-photo/strategic-meeting-room-setting-staged-for-crucial-business-decisions_91128-3499.jpg?size=626&ext=jpg",
        "https://img.freepik.com/free-photo/mockup-frames-in-living-room-interior-with-chair-and-decorscandinavian-style_41470-5148.jpg?size=626&ext=jpg",
        "https://img.freepik.com/free-photo/white-sideboard-in-living-room-interior-with-copy-space_43614-800.jpg?size=626&ext=jpg",
        "https://img.freepik.com/free-photo/empty-chair-decoration-in-living-room-interior_74190-2080.jpg?size=626&ext=jpg&ga=GA1.1.1812542365.1702094613&semt=sph",
        "https://img.freepik.com/free-photo/film-screen-with-bean-bag-chairs-on-rooftop_91128-3608.jpg?size=626&ext=jpg&ga=GA1.1.1812542365.1702094613&semt=sph",
        "https://img.freepik.com/free-photo/modern-styled-entryway_23-2150695879.jpg?size=626&ext=jpg",
        "https://img.freepik.com/free-photo/strategic-meeting-room-setting-staged-for-crucial-business-decisions_91128-3499.jpg?size=626&ext=jpg",
        "https://img.freepik.com/free-photo/mockup-frames-in-living-room-interior-with-chair-and-decorscandinavian-style_41470-5148.jpg?size=626&ext=jpg",
        "https://img.freepik.com/free-photo/white-sideboard-in-living-room-interior-with-copy-space_43614-800.jpg?size=626&ext=jpg",
        "https://img.freepik.com/free-photo/empty-chair-decoration-in-living-room-interior_74190-2080.jpg?size=626&ext=jpg&ga=GA1.1.1812542365.1702094613&semt=sph",
        "https://img.freepik.com/free-photo/film-screen-with-bean-bag-chairs-on-rooftop_91128-3608.jpg?size=626&ext=jpg&ga=GA1.1.1812542365.1702094613&semt=sph",
    )

    LazyVerticalGrid(
        modifier = Modifier.padding(horizontal = 20.dp),
        contentPadding = PaddingValues(vertical = 20.dp),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        items(count = allItemList.size, key = { allItemList[it] + "$it" }) {
            AsyncImage(
                modifier = Modifier
                    .height(160.dp)
                    .clickable(onClick = { /* viewModel.goToXXXScreen */ }),
                model = allItemList[it],
                contentScale = ContentScale.Crop,
                contentDescription = "",
            )
        }
    }
}

@Preview
@Composable
fun AllScreenPreview() {
    AllScreen()
}
