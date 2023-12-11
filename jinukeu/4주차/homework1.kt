@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Homework1() {
    val scrollState = rememberScrollState()

    Scaffold(
            containerColor = Color.LightGray, // White로 하면 경계선 구분이 안됨 ...
            bottomBar = {
                NavigationBar(
                        modifier = Modifier
                                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)),
                        containerColor = Color.White,
                ) {
                    repeat(4) {
                        HomeWorkNavigationBarItem()
                    }
                }
            },
    ) { innerPadding ->
        Homework1Content(
                modifier = Modifier.padding(innerPadding),
                scrollState = scrollState,
        )
    }
}

@Composable
fun RowScope.HomeWorkNavigationBarItem() {
    NavigationBarItem(
            colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.DarkGray,
                    unselectedIconColor = Color.Gray,
                    selectedTextColor = Color.DarkGray,
                    indicatorColor = Color.White,
            ),
            label = {
                Text(text = "라벨")
            },
            selected = true,
            onClick = { /*TODO*/ },
            icon = {
                Icon(imageVector = Icons.Default.AccountBox, contentDescription = "")
            },
            alwaysShowLabel = true,
    )
}

@Composable
fun Homework1Content(
        modifier: Modifier = Modifier,
        scrollState: ScrollState,
) {
    Column(
            modifier = modifier
                    .fillMaxSize()
                    .padding(20.dp)
                    .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        Icon(
                modifier = Modifier
                        .size(36.dp)
                        .align(Alignment.End),
                imageVector = Icons.Default.AccountBox,
                contentDescription = "",
        )

        Homework1Card {
            Text(text = "(유스)님의 지갑")
            Spacer(modifier = Modifier.size(20.dp))
            Text(text = "토스 머니")
            Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(text = "144,552원")
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "돈 보내기")
                }
            }
            Spacer(modifier = Modifier.size(20.dp))

            Divider()

            Spacer(modifier = Modifier.size(20.dp))

            Homework1Item(
                    icon = Icons.Default.Build,
                    label = "코레일 교통",
                    title = "교통카드 잔액보고 충전하기",
            )
            Homework1Item(
                    icon = Icons.Default.ShoppingCart,
                    title = "토스머니 내역 보기",
            )
            Homework1Item(
                    icon = Icons.Default.CheckCircle,
                    title = "토스머니 충전하기",
            )
        }

        Homework1Card {
            Text(text = "용돈기입장")
            Homework1Item(
                    icon = Icons.Default.ThumbUp,
                    label = "이번 달 쓴 돈",
                    title = "25,600원",
            )
        }
    }
}

@Composable
fun Homework1Card(
        modifier: Modifier = Modifier,
        verticalArrangement: Arrangement.Vertical = Arrangement.Top,
        content: @Composable ColumnScope.() -> Unit,
) {
    Column(
            modifier = modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White)
                    .padding(20.dp)
                    .fillMaxWidth(),
            verticalArrangement = verticalArrangement,
    ) {
        content()
    }
}

@Composable
fun Homework1Item(
        modifier: Modifier = Modifier,
        icon: ImageVector = Icons.Default.AccountCircle,
        label: String = "",
        title: String = "",
) {
    Row(
            modifier = modifier.fillMaxWidth().padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(imageVector = icon, contentDescription = "")
        Column(
                modifier = Modifier.weight(1f).padding(horizontal = 16.dp),
        ) {
            if (label.isNotEmpty()) {
                Text(
                        text = label,
                        fontSize = 12.sp,
                )
            }
            Text(text = title)
        }
        Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "")
    }
}