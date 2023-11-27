# [3주차] LazyColumn, LazyRow, LazyGrid, Dialog, SnackBar

# 1. LazyColumn

![Untitled.png](..%2F..%2F..%2F..%2F..%2FDownloads%2Fa49fc2a6-7a92-4a21-8441-864bfccd00a6_Export-d66cf60e-b0c8-41cf-9b8d-1400c04f6c68%2F%5B3%EC%A3%BC%EC%B0%A8%5D%20LazyColumn%2C%20LazyRow%2C%20LazyGrid%2C%20Dialog%2C%20Sna%206271ae49aa644dd78088303d593619a1%2FUntitled.png)

- 스크롤할 수 있는 항목의 세로 목록을 표시(세로 배치)하는 컴포저블이다.
- 안드로이드 RecyclerView와 유사하다. (vertical)
- 화면에 보이는 항목만 구성 및 배치하고 스크롤할 때 재활용하는 방식으로 작동한다.

> ❓ **Column** vs **Lazy Column** 차이점
> <br/>
> - Column
   : 표시할 항목이 적은 경우 사용
   : 사전에 정의(고정)된 개수의 컴포저블만 보유할 수 있음
<br/>

> - Lazy Column
   : 많은 항목들을 보여줘야 할 때 사용
   : 추가 코드 없이 기본적으로 스크롤 제공
   >

### 1) Lazy Column 속성

```kotlin
@Composable
fun LazyColumn(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(), // 리스트 상태를 제어, 관찰할 때 사용하는 상태값
    contentPadding: PaddingValues = PaddingValues(0.dp), // 전체 컨텐츠 주위에 패딩을 추가, 첫 번째 항목 앞이나 마지막 항목 뒤에 패딩을 추가할 수도 있음
    reverseLayout: Boolean = false, // 방향 반대로
    verticalArrangement: Arrangement.Vertical =
        if (!reverseLayout) Arrangement.Top else Arrangement.Bottom, // 레이아웃의 자식들의 세로 배열, 아이템 사이 간격을 추가
    horizontalAlignment: Alignment.Horizontal = Alignment.Start, // 아이템 가로 정렬 적용에 사용
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(), // 플링 동작을 설명하는 로직
    userScrollEnabled: Boolean = true, // 스크롤이 허용되는지 여부
    content: LazyListScope.() -> Unit
)
```

- **추가 설명**
    - `fling` (플링)

      ![https://blog.kakaocdn.net/dn/bRxi7s/btrGTY7Gsjr/U2rkOgFN5zGhmaorLkOmCk/img.gif](https://blog.kakaocdn.net/dn/bRxi7s/btrGTY7Gsjr/U2rkOgFN5zGhmaorLkOmCk/img.gif)

    - `LazyListScope` 블록을 제공 (LazyListScope DSL을 제공)

### 2) Lazy Column 사용하기

```kotlin
@Composable
fun CatalogEx(itemList: List<ItemData>) {
    LazyColumn {
        // 추가설명 : items
        items(itemList) { item ->
            Item(item)
        }

        // 추가설명 : item
        // 수동으로 추가하는 방법 (헤더, 푸터로 사용할 수 있을 것 같음)
        item {
            Item(itemList[0])
        }
        item {
            Item(itemList[1])
        }
    }
}
```

```kotlin
@Composable
fun Item(itemData: ItemData) {
    Card(
        elevation = 8.dp,
        modifier = Modifier.padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = itemData.imageId),
                contentDescription = itemData.title
            )
            Text(
                text = itemData.title,
                style = MaterialTheme.typography.h4,
                modifier = Modifier.padding(horizontal = 0.dp, vertical = 16.dp)
            )
            Text(
                text = itemData.description,
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}
```

```kotlin
data class ItemData(
    @DrawableRes val imageId: Int,
    val title: String,
    val description: String,
)

val items = listOf(
    ItemData(
        imageId = drawable.a1,
        title = "해변 놀이 공원",
        description = "해변 놀이 공원 설명입니다. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. "
    ),
    ItemData(
        imageId = drawable.a2,
        title = "캐년",
        description = "미국의 캐년입니다. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. "
    ),
    ItemData(
        imageId = drawable.a3,
        title = "워터월드",
        description = "워터월드입니다. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. "
    ),
    ItemData(
        imageId = drawable.a4,
        title = "미국의 캐년",
        description = "미국의 캐년입니다. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. "
    ),
    ItemData(
        imageId = drawable.a5,
        title = "라스베가스",
        description = "라스베가스입니다. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. "
    ),
    ItemData(
        imageId = drawable.a6,
        title = "호르슈 밴드",
        description = "호르슈 밴드입니다. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. "
    ),
    ItemData(
        imageId = drawable.a7,
        title = "미국의 길",
        description = "미국의 길입니다. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. "
    ),
    ItemData(
        imageId = drawable.a8,
        title = "엔텔로프 캐년",
        description = "엔텔로프 캐년입니다. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. "
    ),
    ItemData(
        imageId = drawable.a9,
        title = "그랜드 캐년",
        description = "그랜드 캐년입니다. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. "
    ),
)
```

- 결과

    ![Untitled.gif](..%2F..%2F..%2F..%2F..%2FDownloads%2Fa49fc2a6-7a92-4a21-8441-864bfccd00a6_Export-d66cf60e-b0c8-41cf-9b8d-1400c04f6c68%2F%5B3%EC%A3%BC%EC%B0%A8%5D%20LazyColumn%2C%20LazyRow%2C%20LazyGrid%2C%20Dialog%2C%20Sna%206271ae49aa644dd78088303d593619a1%2FUntitled.gif)

---

- **추가 설명**
    - *LazyListScope DSL* : 레이아웃의 항목을 설명하는 여러 함수를 제공한다.
        - `item`
        - `items`
        - `itemsIndexed`
    - scope 비교
        - LazyColumn 내부 scope : `LazyListScope`
        - item, items, itemsIndexed 내부 scope : `LazyItemScope`

    <br/>

    - `item`
        - Lazy Column 내부에 단일 항목을 추가할 때 사용한다.

            ```kotlin
            fun item(key: Any? = null, content: @Composable LazyItemScope.() -> Unit)
            ```

            ```kotlin
            LazyColumn {
                // Add a single item
                item {
                    Text(text = "First item")
                }
            }
            ```

    - `items`
        - 컴포저블을 반복해서 나타내고자 할 때 사용한다.
            - count 지정하는 경우

                ```kotlin
                fun items(
                    count: Int,
                    key: ((index: Int) -> Any)? = null,
                    itemContent: @Composable LazyItemScope.(index: Int) -> Unit
                )
                ```

                ```kotlin
                LazyColumn {
                    // Add 5 items
                    items(5) { index ->
                        Text(text = "Item: $index")
                    }
                }
                ```

              ![Untitled 1.png](..%2F..%2F..%2F..%2F..%2FDownloads%2Fa49fc2a6-7a92-4a21-8441-864bfccd00a6_Export-d66cf60e-b0c8-41cf-9b8d-1400c04f6c68%2F%5B3%EC%A3%BC%EC%B0%A8%5D%20LazyColumn%2C%20LazyRow%2C%20LazyGrid%2C%20Dialog%2C%20Sna%206271ae49aa644dd78088303d593619a1%2FUntitled%201.png)

            - List를 넣는 경우

                ```kotlin
                inline fun <T> LazyListScope.items(
                    items: List<T>,
                    noinline key: ((item: T) -> Any)? = null,
                    crossinline itemContent: @Composable LazyItemScope.(item: T) -> Unit
                )
                ```

                ```kotlin
                LazyColumn {
                    items(itemList) { item ->
                        Item(item)
                    }
                }
                ```

    - `itemsIndexed`
        - items와 유사하지만, 각 항목의 인덱스도 제공한다.

            ```kotlin
            inline fun <T> LazyListScope.itemsIndexed(
                items: Array<T>,
                noinline key: ((index: Int, item: T) -> Any)? = null,
                crossinline itemContent: @Composable LazyItemScope.(index: Int, item: T) -> Unit
            )
            ```

            ```kotlin
            LazyColumn {
                itemsIndexed(listOf(100, 200, 300)) { index, item ->
                    KotlinWorldCard(order = item)
                }
            }
            ```

          ![Untitled 2.png](..%2F..%2F..%2F..%2F..%2FDownloads%2Fa49fc2a6-7a92-4a21-8441-864bfccd00a6_Export-d66cf60e-b0c8-41cf-9b8d-1400c04f6c68%2F%5B3%EC%A3%BC%EC%B0%A8%5D%20LazyColumn%2C%20LazyRow%2C%20LazyGrid%2C%20Dialog%2C%20Sna%206271ae49aa644dd78088303d593619a1%2FUntitled%202.png)


# 2. LazyRow

- 스크롤할 수 있는 항목의 가로 목록을 표시(가로 배치)하는 컴포저블이다.
- 안드로이드 RecyclerView와 유사하다. (horizontal)

### 1) LazyRow 속성

```kotlin
@Composable
fun LazyRow(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    horizontalArrangement: Arrangement.Horizontal =
        if (!reverseLayout) Arrangement.Start else Arrangement.End, // 레이아웃 자식의 수평 배치
    verticalAlignment: Alignment.Vertical = Alignment.Top, // 아이템에 적용된 수직 정렬
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    content: LazyListScope.() -> Unit
)
```

: LazyColumn과 거의 비슷하다.

### 2) LazyRow 사용하기

- LazyColumn 코드와 거의 비슷

    ```kotlin
    @Composable
    fun CatalogEx(itemList: List<ItemData>) {
        // LazyColumn → LazyRow로 변경
        LazyRow {
            items(itemList) { item ->
                Item(item)
            }
    
            item {
                Item(itemList[0])
            }
            item {
                Item(itemList[1])
            }
        }
    }
    ```

    ```kotlin
    @Composable
    fun Item(itemData: ItemData) {
        // Card, Image 약간 수정
        Card(
            elevation = 8.dp,
            modifier = Modifier.padding(16.dp).width(300.dp).fillMaxHeight()
        ) {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Image(
                    painter = painterResource(id = itemData.imageId),
                    contentDescription = itemData.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.height(500.dp),
                )
                Text(
                    text = itemData.title,
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.padding(horizontal = 0.dp, vertical = 16.dp)
                )
                Text(
                    text = itemData.description,
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
    ```

    - 결과

      ![Untitled 1.gif](..%2F..%2F..%2F..%2F..%2FDownloads%2Fa49fc2a6-7a92-4a21-8441-864bfccd00a6_Export-d66cf60e-b0c8-41cf-9b8d-1400c04f6c68%2F%5B3%EC%A3%BC%EC%B0%A8%5D%20LazyColumn%2C%20LazyRow%2C%20LazyGrid%2C%20Dialog%2C%20Sna%206271ae49aa644dd78088303d593619a1%2FUntitled%201.gif)


# 3. LazyGrid

- `LazyVerticalGrid` 및 `LazyHorizontalGrid` 컴포저블은 그리드로 항목 표시를 지원한다.
- 갤러리와 같은 대용량 데이터를 효율적으로 보여줄 수 있다.

### 1) LazyVerticalGrid / LazyHorizontalGrid 속성

- LazyVerticalGrid

    ```kotlin
    @Composable
    fun LazyVerticalGrid(
        columns: GridCells, // 셀이 Column으로 형성하는 방식 제어
        modifier: Modifier = Modifier,
        state: LazyGridState = rememberLazyGridState(), // 리스트 상태를 제어하거나 관찰하는 데 쓰이는 상태 객체
        contentPadding: PaddingValues = PaddingValues(0.dp), // 전체 컨텐츠 주위에 패딩 지정
        reverseLayout: Boolean = false,
        verticalArrangement: Arrangement.Vertical =
            if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
        horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
        flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
        userScrollEnabled: Boolean = true,
        content: LazyGridScope.() -> Unit
    )
    ```

- LazyHorizontalGrid

    ```kotlin
    @Composable
    fun LazyHorizontalGrid(
        rows: GridCells, // 셀이 Row로 형성하는 방식 제어
        modifier: Modifier = Modifier,
        state: LazyGridState = rememberLazyGridState(), // 리스트 상태를 제어하거나 관찰하는 데 쓰이는 상태 객체
        contentPadding: PaddingValues = PaddingValues(0.dp), // 전체 컨텐츠 주위에 패딩 지정
        reverseLayout: Boolean = false,
        horizontalArrangement: Arrangement.Horizontal =
            if (!reverseLayout) Arrangement.Start else Arrangement.End,
        verticalArrangement: Arrangement.Vertical = Arrangement.Top,
        flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
        userScrollEnabled: Boolean = true,
        content: LazyGridScope.() -> Unit
    )
    ```

    - GridCells

      [https://www.valueof.io/blog/lazy-grids-gridcells-fixed-adaptive-custom-compose](https://www.valueof.io/blog/lazy-grids-gridcells-fixed-adaptive-custom-compose)

        - `Fixed`
            - 고정된 수의 행 or 열이 있는 그리드를 정의한다.

              ex) `columns = GridCells.Fixed(2)`

              ![https://images.squarespace-cdn.com/content/v1/5e97431c825c59018588244f/5e82af63-06ef-40f8-991b-d13f5c20c26c/lazy-grid-vertical-fixed.gif?format=2500w](https://images.squarespace-cdn.com/content/v1/5e97431c825c59018588244f/5e82af63-06ef-40f8-991b-d13f5c20c26c/lazy-grid-vertical-fixed.gif?format=2500w)

              ex) `rows = GridCells.Fixed(3)`

              ![https://images.squarespace-cdn.com/content/v1/5e97431c825c59018588244f/7644ee60-0ac0-4ec5-92e1-860c44dc2c67/lazy-grid-horizontal-fixed.gif?format=2500w](https://images.squarespace-cdn.com/content/v1/5e97431c825c59018588244f/7644ee60-0ac0-4ec5-92e1-860c44dc2c67/lazy-grid-horizontal-fixed.gif?format=2500w)

        - `Adaptive`
            - 모든 셀에 최소한 minSize 공간이 있고 모든 추가공간이 고르게 분포되어 있는 조건에서 가능한 많은 행 or 열로 그리드를 정의한다.
            - 몇 개의 행으로 표시할지 정해주지 않아도 dp의 크기에 따라 알아서 격자 형태를 바꿔준다.
            
                ex) `columns = GridCells.Adaptive(100.dp)`
            
                ![https://images.squarespace-cdn.com/content/v1/5e97431c825c59018588244f/95bdb4a2-a973-460a-8d12-8f752a6c0851/lazy-grid-vertical-adaptive.gif?format=2500w](https://images.squarespace-cdn.com/content/v1/5e97431c825c59018588244f/95bdb4a2-a973-460a-8d12-8f752a6c0851/lazy-grid-vertical-adaptive.gif?format=2500w)
            
                ex) `rows = GridCells.Adaptive(128.dp)`
            
                ![https://images.squarespace-cdn.com/content/v1/5e97431c825c59018588244f/832b1ad6-d1ec-4bd7-9125-d8f7660f61d7/lazy-grid-horizontal-adaptive.gif?format=2500w](https://images.squarespace-cdn.com/content/v1/5e97431c825c59018588244f/832b1ad6-d1ec-4bd7-9125-d8f7660f61d7/lazy-grid-horizontal-adaptive.gif?format=2500w)

        - `calculateCrossAxisCellSizes`를 override해서 Custom 할 수도 있음
            - `availableSize`와 `spacing`을 활용한다.
            - `GridItemSpan`을 사용하면 더 자세하게 custom이 가능하다.
            
                ```kotlin
                LazyVerticalGrid(
                  columns = object : GridCells {
                    override fun Density.calculateCrossAxisCellSizes(
                      availableSize: Int, // 사용 가능한 사이즈
                      spacing: Int // 아이템의 간격
                    ): List<Int> {
                      val firstColumn = (availableSize - spacing) * 2 / 3
                      val secondColumn = availableSize - spacing - firstColumn
                      return listOf(firstColumn, secondColumn)
                    }
                  },
                  ...
                )
                ```
            
                ![https://images.squarespace-cdn.com/content/v1/5e97431c825c59018588244f/6afc4ce1-2670-40ff-909b-5748d3baf6ca/lazy-grid-vertical-custom.gif?format=2500w](https://images.squarespace-cdn.com/content/v1/5e97431c825c59018588244f/6afc4ce1-2670-40ff-909b-5748d3baf6ca/lazy-grid-vertical-custom.gif?format=2500w)


---

- **추가 설명**

  - Content Padding / Content Spacing

    [https://www.valueof.io/blog/lazy-layouts-lazyrow-lazycolumn-contentpadding-compose](https://www.valueof.io/blog/lazy-layouts-lazyrow-lazycolumn-contentpadding-compose)

      - `Content Padding`

        : 리스트의 앞뒤가 잘리지 않게 하려면 Content Padding을 사용해준다.

          - Content Padding 적용X (Modifier의 padding → start, end 적용)

            ![https://images.squarespace-cdn.com/content/v1/5e97431c825c59018588244f/464991b2-3600-4d85-b30c-54e0284c82c5/lazy-row.gif?format=2500w](https://images.squarespace-cdn.com/content/v1/5e97431c825c59018588244f/464991b2-3600-4d85-b30c-54e0284c82c5/lazy-row.gif?format=2500w)

          - Content Padding 적용O (Content Padding → start, end 적용)

            ![https://images.squarespace-cdn.com/content/v1/5e97431c825c59018588244f/20553a34-5649-478f-9c2d-83097cf709f0/lazy-row-fixed.gif?format=2500w](https://images.squarespace-cdn.com/content/v1/5e97431c825c59018588244f/20553a34-5649-478f-9c2d-83097cf709f0/lazy-row-fixed.gif?format=2500w)

      - `Content Spacing`

          : 아이템 간의 간격을 추가하고 싶을 때, `Arrangement.spacedBy()`를 사용해준다.
        
          ```kotlin
          LazyRow(
              // 아이템 간 간격 4dp 설정
              verticalArrangement = Arrangement.spacedBy(4.dp),
          ) {
              // ...
          }
          ```
        
          ![Untitled 3.png](..%2F..%2F..%2F..%2F..%2FDownloads%2Fa49fc2a6-7a92-4a21-8441-864bfccd00a6_Export-d66cf60e-b0c8-41cf-9b8d-1400c04f6c68%2F%5B3%EC%A3%BC%EC%B0%A8%5D%20LazyColumn%2C%20LazyRow%2C%20LazyGrid%2C%20Dialog%2C%20Sna%206271ae49aa644dd78088303d593619a1%2FUntitled%203.png)

    <br/>

  - Sticky Header

      ```kotlin
      @ExperimentalFoundationApi
          fun stickyHeader(key: Any? = null, content: @Composable LazyItemScope.() -> Unit)
      ```

      ```kotlin
      class MainActivity : ComponentActivity() {
          @ExperimentalFoundationApi
          override fun onCreate(savedInstanceState: Bundle?) {
              super.onCreate(savedInstanceState)
              setContent {
                  val sections = listOf("A", "B", "C", "D", "E", "F", "G")
    
                  LazyColumn(
                      contentPadding = PaddingValues(all = 12.dp),
                      verticalArrangement = Arrangement.spacedBy(12.dp)
                  ) {
                      sections.forEach { section ->
                                              // stickyHeader 함수 사용
                          stickyHeader {
                              Text(
                                  modifier = Modifier
                                      .fillMaxSize()
                                      .background(Color.Gray)
                                      .padding(12.dp),
                                  text = "Section $section"
                              )
                          }
                          items(10){
                              Text(text = "Item $it from the section $section",
                              modifier = Modifier.padding(12.dp))
                          }
    
                      }
                  }
              }
          }
      }
      ```

    ![https://velog.velcdn.com/images/blue-sky/post/b3071ea3-99b5-4ccd-b786-b77aed720716/image.gif](https://velog.velcdn.com/images/blue-sky/post/b3071ea3-99b5-4ccd-b786-b77aed720716/image.gif)


# 4. Dialog

- Dialog 종류
    - `AlertDialog` : Material Design 테마 다이얼로그 생성
    - `Dialog` : 커스텀한 다이얼로그 생성

### 1) AlertDialog 속성

```kotlin
@Composable
fun AlertDialog(
    onDismissRequest: () -> Unit, // 사용자가 대화상자 외부를 탭하는 등의 방법으로 대화상자를 닫을 때 호출되는 함수
    confirmButton: @Composable () -> Unit, // 확인 버튼 역할을 하는 컴포저블
    modifier: Modifier = Modifier,
    dismissButton: @Composable (() -> Unit)? = null, // 닫기 버튼 역할을 하는 컴포저블
    title: @Composable (() -> Unit)? = null, // 대화상자 상단에 표시되는 텍스트
    text: @Composable (() -> Unit)? = null, // 대화상자 중앙에 표시되는 텍스트
    shape: Shape = MaterialTheme.shapes.medium,
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    properties: DialogProperties = DialogProperties()
)
```

```kotlin
@Composable
fun AlertDialog(
    onDismissRequest: () -> Unit,
    buttons: @Composable () -> Unit, // confirmButton, dismissButton 대신에 buttons를 사용할 수도 있음
    modifier: Modifier = Modifier,
    title: (@Composable () -> Unit)? = null,
    text: @Composable (() -> Unit)? = null,
    shape: Shape = MaterialTheme.shapes.medium,
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    properties: DialogProperties = DialogProperties()
)
```

### 2) AlertDialog 사용해보기

```kotlin
@Composable
fun DialogEx() {
    var openDialog by remember { mutableStateOf(false) }
    var counter by remember { mutableStateOf(0) }

    Column {
        Button(onClick = { openDialog = true }) {
            Text("다이얼로그 열기")
        }
        Text("카운터: $counter")
    }

    if (openDialog) {
        // Slot API로 되어있음
        AlertDialog(
            // dialog 종료 요청이 왔을 때 처리
            onDismissRequest = {
                openDialog = false
            },
            // 확인 버튼 (오른쪽)
            confirmButton = {
                Button(onClick = {
                    counter++
                    openDialog = false
                }) {
                    Text(text = "더하기")
                }
            },
            // 취소 버튼 (왼쪽)
            dismissButton = {
                Button(onClick = {
                    openDialog = false
                }) {
                    Text(text = "취소")
                }
            },
            // 제목
            title = {
                Text(text = "더하기")
            },
            // 설명 텍스트
            text = {
                Text(text = "더하기 버튼을 누르면 카운터가 증가합니다.\n버튼을 눌러주세요.")
            }
        )
    }
}
```

![Untitled 2.gif](..%2F..%2F..%2F..%2F..%2FDownloads%2Fa49fc2a6-7a92-4a21-8441-864bfccd00a6_Export-d66cf60e-b0c8-41cf-9b8d-1400c04f6c68%2F%5B3%EC%A3%BC%EC%B0%A8%5D%20LazyColumn%2C%20LazyRow%2C%20LazyGrid%2C%20Dialog%2C%20Sna%206271ae49aa644dd78088303d593619a1%2FUntitled%202.gif)

```kotlin
// buttons를 사용해서 더하기, 취소 버튼 순서 바꿀 수 있음
buttons = {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(onClick = {
            counter++
            openDialog = false
        }) {
            Text(text = "더하기")
        }

        Button(onClick = {
            openDialog = false
        }) {
            Text(text = "취소")
        }
    }
},
```

![Untitled 4.png](..%2F..%2F..%2F..%2F..%2FDownloads%2Fa49fc2a6-7a92-4a21-8441-864bfccd00a6_Export-d66cf60e-b0c8-41cf-9b8d-1400c04f6c68%2F%5B3%EC%A3%BC%EC%B0%A8%5D%20LazyColumn%2C%20LazyRow%2C%20LazyGrid%2C%20Dialog%2C%20Sna%206271ae49aa644dd78088303d593619a1%2FUntitled%204.png)

### 3) Dialog 속성

```kotlin
@Composable
fun Dialog(
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(),
    content: @Composable () -> Unit // content 안에 원하는대로 커스텀해서 만들어야 한다.
)
```

- **추가 설명**
    - DialogProperties

        ```kotlin
        @Immutable
        class DialogProperties @ExperimentalComposeUiApi constructor(
            val dismissOnBackPress: Boolean = true,
            val dismissOnClickOutside: Boolean = true,
            val securePolicy: SecureFlagPolicy = SecureFlagPolicy.Inherit,
            @Suppress("EXPERIMENTAL_ANNOTATION_ON_WRONG_TARGET")
            @get:ExperimentalComposeUiApi
            val usePlatformDefaultWidth: Boolean = true
        )
        ```

        - `dismissOnBackPress` : back 버튼 처리 / true를 주고 back 버튼을 누르면 onDismissRequest가 호출된다.
        - `dismissOnClickOutside` : Dialog 외부 클릭 처리 / true를 주고 Dialog 외부 클릭 시 onDismissRequest가 호출된다.
        - `securePolicy` : SecureFlagPolicy.SecureOn을 대입하면 화면 캡쳐 기능이 동작하지 않는다.


### 2) Dialog 사용해보기

```kotlin
@Composable
fun CustomDialog() {
    var openDialog by remember { mutableStateOf(false) }
    var counter by remember { mutableStateOf(0) }

    Column {
        Button(onClick = {
            openDialog = true
        }) {
            Text("다이얼로그 열기")
        }
        Text("카운터: $counter")
    }

    if (openDialog) {
        Dialog(onDismissRequest = {
            openDialog = false
        }) {
            // Surface로 감싸지 않으면 전체에 dim 효과가 먹게 된다.
            Surface {
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(text = "버튼을 클릭해주세요.\n* +1을 누르면 값이 증가합니다.\n* -1을 누르면 값이 감소합니다.")
                    Row(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Button(onClick = {
                            openDialog = false
                        }) {
                            Text(text = "취소")
                        }
                        Button(onClick = {
                            openDialog = false
                            counter++
                        }) {
                            Text(text = "+1")
                        }
                        Button(onClick = {
                            openDialog = false
                            counter--
                        }) {
                            Text(text = "-1")
                        }
                    }
                }
            }
        }
    }
}
```

![Untitled 3.gif](..%2F..%2F..%2F..%2F..%2FDownloads%2Fa49fc2a6-7a92-4a21-8441-864bfccd00a6_Export-d66cf60e-b0c8-41cf-9b8d-1400c04f6c68%2F%5B3%EC%A3%BC%EC%B0%A8%5D%20LazyColumn%2C%20LazyRow%2C%20LazyGrid%2C%20Dialog%2C%20Sna%206271ae49aa644dd78088303d593619a1%2FUntitled%203.gif)

# 5. Snackbar

- 화면 하단에 표시되는 간단한 알림 역할을 한다.
- 사용자 환경을 방해하지 않고 작업이나 작업에 관한 피드백을 제공한다.
- 몇 초 후에 사라진다.

### 1) Snackbar 속성

```kotlin
@Composable
fun Snackbar(
    modifier: Modifier = Modifier,
    action: @Composable (() -> Unit)? = null,
    actionOnNewLine: Boolean = false,
    shape: Shape = MaterialTheme.shapes.small,
    backgroundColor: Color = SnackbarDefaults.backgroundColor,
    contentColor: Color = MaterialTheme.colors.surface,
    elevation: Dp = 6.dp,
    content: @Composable () -> Unit
)
```

- Snackbar 컴포저블은 Material Design 가이드라인에 정의된 대로 표현된다.

- Snackbar를 숨기기 옵션이나 애니메이션이 없기 때문에, Scaffold를 사용해서 Snackbar를 구현한다.
<br/>
<br/>

cf.) 공식문서에서도 Scaffold를 사용하고 있다.
[https://developer.android.com/jetpack/compose/components/snackbar?hl=ko](https://developer.android.com/jetpack/compose/components/snackbar?hl=ko)
<br/>
<br/>

cf.) SnackbarHost
```kotlin
@Composable
fun SnackbarHost(
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    snackbar: @Composable (SnackbarData) -> Unit = { Snackbar(it) }
)
```

- SnackbarHost의 상태에 따라 실제로 화면에 Snackbar를 표시하는 역할을 합니다.
- Material 사양 및 호스트 상태에 따라 항목을 적절하게 표시, 숨기기 및 해제하기 위해 Scaffold에서 사용되는 스낵바용 호스트이다.
<br/>
<br/>

cf.) SnackbarHostState

```kotlin
@Stable
class SnackbarHostState {
		suspend fun showSnackbar(
		    message: String,
		    actionLabel: String? = null,
		    duration: SnackbarDuration = SnackbarDuration.Short
		): SnackbarResult {…}
}
```

- SnackbarHost의 상태를 관리하는 데 사용되는 객체입니다.
- 현재 표시되고 있는 Snackbar의 정보를 얻거나, 새로운 Snackbar를 큐에 추가하거나, 현재 표시되고 있는 Snackbar를 제거하는 등의 작업을 수행할 수 있습니다.

  (한 번에 하나의 스낵바만 보이도록 한다. 다른 스낵바가 이미 보이는 동안 showSnackbar()가 호출되면 현재 스낵바가 해제된 후 새 스낵바가 큐에 추가되어 표시된다.)


### 2) Snackbar 구현하기

```kotlin
@Composable
fun SnackbarEx() {
    var counter by remember { mutableStateOf(0) }

    // 만든 이유: showSnackbar()가 suspend function이기 때문 (코루틴 범위 안에서만 호출 가능)
    val coroutineScope = rememberCoroutineScope()

    // ScaffoldState -> 2가지 있음 (drawerState, snackbarHostState)
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Button(onClick = {
            counter++
            coroutineScope.launch {
                // 스낵바 만들기 (scaffoldState.snackbarHostState.showSnackbar)
                // ScaffoldState의 snackbarHostState 사용
                scaffoldState.snackbarHostState.showSnackbar(
                    message = "카운터는 ${counter} 입니다.",
                    actionLabel = "닫기",
                    duration = SnackbarDuration.Short
                )
            }
        }) {
            Text(text = "더하기")
        }
    }
}
```

```kotlin
@Composable
fun SnackbarEx() {
    var counter by remember { mutableStateOf(0) }
    val coroutineScope = rememberCoroutineScope()

    // 이렇게 쓰기도 함
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) {
        Button(onClick = {
            counter++
            coroutineScope.launch {
                // 스낵바 만들기 (snackbarHostState.showSnackbar)
                // snackbarHostState.showSnackbar()는 SnackbarResult를 반환
                val result = snackbarHostState.showSnackbar(
                    message = "카운터는 ${counter} 입니다.",
                    actionLabel = "닫기",
                    // Short, Long, Indefinite 있음
                    duration = SnackbarDuration.Short
                )

                // result를 활용할 수도 있다.
                when (result) {
                    SnackbarResult.Dismissed -> {}
                    SnackbarResult.ActionPerformed -> {}
                }
            }
        }) {
            Text(text = "더하기")
        }
    }
}
```

- 결과

  ![Untitled 4.gif](..%2F..%2F..%2F..%2F..%2FDownloads%2Fa49fc2a6-7a92-4a21-8441-864bfccd00a6_Export-d66cf60e-b0c8-41cf-9b8d-1400c04f6c68%2F%5B3%EC%A3%BC%EC%B0%A8%5D%20LazyColumn%2C%20LazyRow%2C%20LazyGrid%2C%20Dialog%2C%20Sna%206271ae49aa644dd78088303d593619a1%2FUntitled%204.gif)