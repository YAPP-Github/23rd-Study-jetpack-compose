## 1. Text
- **Text 사용하기**
```kotlin
@Composable
fun TextTest(name: String) {
	Text(text = "Hello $name")
}
```
<br/>

- **Text에 스타일 지정해보기**
```kotlin
@Composable
fun TextTest(name: String) {
	Text(
    	// 보여지는 text
		text ="Hello $name\n".repeat(3)
        // color 지정 (색)
    	color = Color.Red,
        // fontSize 지정 (사이즈)
        fontSize = 30.sp,
        // fontWeight 지정 (두께)
        fontWeight = FontWeight.Bold,
        // fontFamily 지정 (폰트)
        fontFamily = FontFamily.Monospace,
        // letterSpacing 지정 (글자 간격)
        letterSpacing = 2.sp,
        // maxLines 지정 (최대 줄 수)
        maxLines = 2,
        // textDecoration 지정 (밑줄)
        textDecoration = TextDecoration.Underline,
		// textAlign 지정 (정렬)
       	textAlign = TextAlign.Center,
        // modifier로 text 영역을 늘려 textAlign 확인함
        modifier = Modifier.size(300.dp)
    )
}
```

**cf.) 추가 설명** 

1. text 색 지정할 때, 직접 Hex 값 지정도 가능함
	- `Color(0xffff9944)`
    <br/>
2. text는 sp 단위를 사용하고, 사용할 때 `원하는 사이즈.sp`로 작성함
    <br/>
3. 기본적으로 제공해주는 <u>_fontWeight_</u>가 다양하게 있음
	  - `Bold`
    - `Black`
    - `Light`
    - `ExtraBold`
    - `Medium`
    - `ExtraLight`
    - `Normal`
    - `SemiBold`
    - `Thin`
	<br/>
4. 기본적으로 제공해주는 <u>_fontFamily_</u>도 다양하게 있음
	  - `Cursive` : 필기체 (영어만 먹힘)
    - `SansSerif` : 고딕체
    - `Serif` : 궁서체
    - `Monospace`
    <br/>
5. 예를 들어, 3줄이지만 `maxLines = 2`로 지정해주면 2줄만 보임
    <br/>
6. 기본적으로 지원해주는 <u>_textDecoration_</u>은 3가지 정도 있음
	  - `Underline` : 밑줄
    - `LineThrough` : 취소선
    - `None`
    <br/>
7. 기본적으로 제공해주는 <u>_textAlign_</u>도 다양하게 있음
	  - `Center`
    - `Right`
    - `Left`
    - `Start`
    - `End`
    - `Justify` : 줄바꿈 일어날 때 이전 줄을 가득 채우도록 글자간 폭 조절
<br/>
<br/>

## 2. Button
- **Button 사용하기**
```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ButtonTheme {
                ButtonTest(onButtonClicked = {})
            }
        }
    }
}
```
```kotlin
@Composable
fun ButtonTest(onButtonClicked: () -> Unit) {
    Button(onClick = onButtonClicked) {
        Text(text = "Send")
    }
}
```
<br/>

- **Button에 Toast 메시지 적용해보기**
```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ButtonTheme {
                ButtonTest(onButtonClicked = {
                	// Toast 메시지
                    Toast.makeText(this, "Send Button Clicked!", Toast.LENGTH_SHORT).show()
                })
            }
        }
    }
}
```
<br/>

- **Button에 스타일 지정해보기**
```kotlin
@Composable
fun ButtonTest(onButtonClicked: () -> Unit) {
    Button(
	    // onClick 지정 (클릭 이벤트)
        onClick = onButtonClicked,
        // enabled 지정 (클릭 가능 여부)
        enabled = true,
        // border 지정 (테두리 두께, 색상)
        border = BorderStroke(10.dp, Color.Green),
        // shape 지정 (모양)
        shape = CircleShape,
        // contentPadding 지정 (버튼 padding)
        contentPadding = PaddingValues(20.dp)
    ) {
    	// Icon 지정 (버튼 내부 아이콘)
        Icon(
            imageVector = Icons.Filled.Send,
            contentDescription = "Send"
        )
        // Spacer 지정 (Icon과 Text 간의 간격)
        Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
        // Text 지정 (버튼 내부 텍스트)
        Text(text = "Send")
    }
}
```
**cf.) 추가 설명** 

1. `enabled`는 기본적으로 `true`로 세팅되어 있으며, `enabled = false` 를 하면 버튼 색이 바뀌면서 클릭이 되지 않음
2. 기본적으로 제공해주는 <u>_shape_</u> 종류는 4가지 정도가 있음
   - `RectangleShape`
   - `CircleShape`
   - `RoundedCornerShape` : radius 느낌
   - `CutCornerShape` : 육각형 느낌
3. 버튼 내부에 Padding을 적용하려면 `PaddingValues()`를 사용해서 지정해줘야 함
4. 버튼 내부에 Icon을 적용할 때, 기본적으로 가지고 있는 `imageVector`를 활용할 수 있음
5. 버튼 내부에 Icon과 Text가 있을 때, 사이 간격을 `Spacer`를 통해 지정할 수 있음
	- 이때 `Modifier`를 사용해서 지정해줄 수 있음
	- `30.dp` 이런 식으로 직접 지정해 줄 수도 있고,`ButtonDefaults.IconSpacing`을 통해 설정된 기본값 사용할 수도 있음
6. `Icon()`과 `Text()`의 순서를 바꾸면 배치가 바뀜
<br/>
<br/>

## 3. Modifier
- **공식문서에서 말하는 Modifier**

> Modifier를 사용하면 Composable을 장식하거나 강화할 수 있습니다.
> 수정자를 통해 다음과 같은 종류의 작업을 실행할 수 있습니다.
> - 컴포저블의 크기, 레이아웃, 동작 및 모양 변경
> - 접근성 라벨과 같은 정보 추가
> - 사용자 입력 처리
> - 요소를 클릭 가능, 스크롤 가능, 드래그 가능 또는 확대/축소 가능하게 만드는 높은 수준의 상호작용 추가

=> Modifier는 레이아웃을 변경하고 이벤트 리스너 추가 등을 가능하게 만들어 주는 코틀린 객체 <br/>
=> 모든 Composable 함수에는 Modifier를 추가할 수 있음

<br/>

- **Modifier로 사이즈 지정**


```kotlin
@Composable
fun ModifierTest(onButtonClicked: () -> Unit) {
	Button(
		onClick = {},
		modifier = Modifier.size(200.dp)
	) {
		Icon(
			imageVector = Icons.Filled.Search,
			contentDescription = null
		)
		Spacer(
			modifier = Modifier.size(ButtonDefaults.IconSpacing)
		)
		Text("Search")
	}
}
```
**cf.) 관련 속성들** 
- `Modifier.width(100.dp)`, `Modifier.height(100.dp)` : 각각 너비, 높이 지정 가능<br/>
 `Modifier.width(100.dp).height(100.dp)` : 체이닝 방식으로 지정 가능

- `Modifier.size(100.dp)` : 위의 방식을 <u>_size_</u>를 이용해서 한 번에 지정 가능<br/>
  `Modifier.size(width = 100.dp, height = 100.dp)` : 안에서 각각 지정 가능

- `Modifier.fillMaxWidth()` : 가로 크기를 부모 크기와 같게

- `Modifier.fillMaxHeight()` : 세로 크기를 부모 크기와 같게

- `Modifier.fillMaxSize()` : 가로, 세로 모두 부모 크기와 같게

    <br/>
- **Modifier로 배경색 지정**
```kotlin
@Composable
fun ModifierTest(onButtonClicked: () -> Unit) {
    Button(
        onClick = {},
        // 실패
        modifier = Modifier.size(200.dp).background(Color.Red)
    ) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = null
        )
        Spacer(
            modifier = Modifier.size(ButtonDefaults.IconSpacing)
        )
        Text("Search")
    }
}
```
: Button에서 Modifier를 사용해서 `background()`로 배경색 설정하면 적용되지 않음
    
```kotlin
@Composable
fun ModifierTest(onButtonClicked: () -> Unit) {
    Button(
        colors = ButtonDefaults.buttonColors(
        	// 버튼 자체의 색상
            backgroundColor = Color.Red,
            // 버튼에 들어가는 이미지나 텍스트에도 영향을 줌
            contentColor = Color.Blue
        ),
        onClick = {},
        // padding 추가
        modifier = Modifier.size(200.dp).padding(10.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = null
        )
        Spacer(
            modifier = Modifier.size(ButtonDefaults.IconSpacing)
        )
        Text("Search")
    }
}
```
: 대신 `ButtonDefaults.buttonColors()` 속성을 사용함
<br/>

- **Modifier로 clickable 설정**
```kotlin
@Composable
fun ModifierTest(onButtonClicked: () -> Unit) {
        Button(
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Red,
            contentColor = Color.Blue
        ),
        onClick = {},
        // 일부 요소만 클릭 되게끔 하게 하기 위해서 false로 줌
        enabled = false,
        modifier = Modifier.size(200.dp).padding(10.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = null
        )
        Spacer(
            modifier = Modifier.size(ButtonDefaults.IconSpacing)
        )
        Text(
            "Search",
            // clickable 설정
            modifier = Modifier.clickable {}
        )
    }
}
```
: 현재 Button은 `enabled = false`이고, Text에만 `clickable` 속성을 주었기 때문에, Text만 클릭됨
<br/>

- **Modifier로 offset 지정**
```kotlin
@Composable
fun ModifierTest(onButtonClicked: () -> Unit) {
        Button(
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Red,
            contentColor = Color.Blue
        ),
        onClick = {},
        modifier = Modifier
            .size(200.dp)
            .padding(10.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = null,
            modifier = Modifier.background(Color.Green)
        )
        Spacer(
            modifier = Modifier
                .size(ButtonDefaults.IconSpacing)
                .background(Color.Cyan)
        )
        Text(
            "Search",
            modifier = Modifier
            	// 텍스트 'Search'가 오른쪽으로 10dp만큼 이동함
                .offset(x = 10.dp)
                .background(Color.Yellow)
        )
    }
}
```
: offset은 x,y축에 설정할 수 있으며, 양수 혹은 음수일 수 있음<br/>
:  padding과 offset의 차이점은 Composable에 offset을 추가해도 측정값이 변경되지 않는다는 것<br/>
(자식 컴포넌트의 크기에 영향이 없음, 위치에는 영향을 줄 수 있음)
<br/>
<br/>

## 4. Surface
: Surface 는 요소를 감싸는 컨테이너와 같은 역할을 하는 요소<br/>
: Material 디자인의 기본적인 패턴<br/>
: 기존의 margin의 역할을 Surface의 padding이 한다고 생각하면 편하다고 함<br/>

- **Surface 사용하기**

```kotlin
@Composable
fun SurfaceTest(name: String) {
    Surface(
    	// margin 역할
        modifier = Modifier.padding(5.dp),
        // border 설정 (테두리 두께, 색상)
        border = BorderStroke(
            width = 2.dp,
            color = Color.Magenta
        ),
        // elevation 설정 (그림자 효과)
        elevation = 10.dp,
        // shape 설정 (shape 종류는 위에서 언급한 부분과 동일)
        shape = CircleShape,
        // color 설정 (배경색)
        color = MaterialTheme.colors.secondary
    ) {
        Text(
            text = "Hello $name!",
            modifier = Modifier.padding(8.dp)
        )
    }
}
```
cf.) 추가 설명

- Surface에서는 `MaterialTheme.colors`를 통해 `primary`, `error`, `background`, `surface`, `secondary` 등의 컬러를 지정
- 이에 따라 `contentColor`가 자동으로 선택됨
<br/>
<br/>

## 5. Box
Box는 주로 두 가지 용도로 사용함
1) Box 자체를 만들기 위해서 사용
2) FrameLayout 처럼 중첩 시키기 위해 사용

- **Box 내부에 Text 중첩**
```kotlin
@Composable
fun BoxTest() {
	Box(modifier = Modifier.size(100.dp)) {
        // Box 내에서 Modifier.align을 사용해서 Text 배치 변경
        Text(
            text = "Hello",
            modifier = Modifier.align(Alignment.BottomEnd)
        )
        Text(
            text = "Jetpack",
            modifier = Modifier.align(Alignment.CenterEnd)
        )
        Text(
            text = "Compose",
            modifier = Modifier.align(Alignment.TopStart)
        )
    }
}
```
**cf.) 추가 설명**
- `Modifier.align()` 종류
	- `TopStart`, `TopCenter`, `TopEnd`
  - `CenterStart`, `Center`, `CenterEnd`
  - `BottomStart`, `BottomCenter`, `BottomEnd`
<br/>

- **Box 내부에 Box 중첩**
```kotlin
@Composable
fun BoxTest() {
Box(modifier = Modifier.size(100.dp)) {
        // 먼저 작성한 Box(Cyan 컬러)가 밑에 깔리고
        Box(modifier = Modifier
        	.size(70.dp)
            .background(Color.Cyan)
            .align(Alignment.CenterStart)
        )
        // 그 위에 나중에 작성한 Box(Red 컬러)가 올라옴
        Box(modifier = Modifier
        	.size(70.dp)
            .background(Color.Red)
            .align(Alignment.BottomEnd)
        )
    }
}
```
```kotlin
@Composable
fun BoxTest() {
	Box {
        // 최상위 Box가 사이즈가 지정이 되어있지 않기 때문에, 두번째 Box(Red컬러) 사이즈에 맞게 설정됨
        // 때문에 첫번째 Box(Cyan컬러)가 matchParentSize()로 설정되어 있다면 -> 두번째 Box(Red 컬러)에 완전히 가려지게 됨
		Box(modifier = Modifier.matchParentSize().background(Color.Cyan).align(Alignment.CenterStart))

        // fillMaxSize()로 설정하면 첫번째 Box(Cyan컬러)가 전체를 가득 체우고, 가운데에 두번째 Box(Red컬러)가 오게 된다.
        Box(modifier = Modifier.fillMaxSize().background(Color.Cyan).align(Alignment.CenterStart))
        Box(modifier = Modifier.size(70.dp).background(Color.Red).align(Alignment.Center))
    }
}
```
<br/>
<br/>

## 6~7. Row / Column
**Row**는 <u>가로로 배치(수평 배치)</u> 하고 싶을 때 사용
**Column은** <u>세로로 배치(수직 배치)</u> 하고 싶을 때 사용
<br/>

- **Alignment 적용해보기**
: Alignment는 <u>Row / Column의 진행방향과 반대방향</u>으로 적용됨

1) Row (Alignment : 수직 -> `verticalAlignment`)
```kotlin
@Composable
fun RowTest() {
	Row(
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier.height(40.dp)
    ) {
        Text(text = "첫 번째!", modifier = Modifier.align(Alignment.Top))
        Text(text = "두 번째!")
        Text(text = "세 번째!")
    }
}
```
2) Column (Alignment : 수평 -> `horizontalAlignment`)
```kotlin
@Composable
fun ColumnTest() {
    Column(
        horizontalAlignment = Alignment.End,
        modifier = Modifier.size(100.dp)
    ) {
        Text(text = "첫 번째")
        Text(text = "두 번째")
        Text(text = "세 번째")
    }
}
```
: 전반적으로 설정한 Alignment에 따라 위치하고, 이후 개별적으로도 세팅할 수 있음

**cf.) 추가 설명**

-  Alignment는  <u>1D Alignment / 2D Alignment</u> 로 나뉨 (Row / Column 은 1D Alignment를 사용함)
	-  **Row** (수평) <-> Alignment (수직) : `Top`, `CenterVertically`, `Bottom`
	-  **Column** (수직) <-> Alignment (수평) : `Start`, `CenterHorizontally`, `End`

- 1D Aligment
    ![Img](https://velog.velcdn.com/images/yaebb82/post/0a0c1633-9c2c-4f16-a54f-9d8d3a983d64/image.png)
- 2D Alignment
	![Img](https://velog.velcdn.com/images/yaebb82/post/8a741f89-1693-411e-b0b0-58e79ce2be96/image.png)
<br/>

- **Arrangement 적용해보기**
: Arrangement는 <u>Row / Column이 진행되는 방향</u>으로 배치해줌

1) Row (Arrangement : 수평)
```kotlin
@Composable
fun RowTest() {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier
            .width(200.dp)
            .height(40.dp)
    ) {
        Text(text = "첫 번째!", modifier = Modifier.align(Alignment.Top))
        Text(text = "두 번째!")
        Text(text = "세 번째!")
    }
}
```
2) Column (Arrangement : 수직)
```kotlin
@Composable
fun ColumnTest() {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.End,
        modifier = Modifier.size(100.dp)
    ) {
        Text(text = "첫 번째")
        Text(text = "두 번째")
        Text(text = "세 번째")
    }
}
```

**cf.) 추가 설명**

- Arrangement 속정들 (생소한 것만 / 배치되는 구조)
	- `SpaceAround` : #1##2##3#
   	- `SpaceBetween` : 1##2##3
   	- `SpaceEvenly` : #1#2#3#
<br/>


- **Modifier.align 적용해보기**
: `Modifier.align()`은 <u>Alignment</u>를 따라감

1) Row (Alignment : 수평)

```kotlin
@Composable
fun RowTest() {
    Row(modifier = Modifier.height(40.dp)) {
        Text(text = "첫 번째!", modifier = Modifier.align(Alignment.Top))
        Text(text = "두 번째!", modifier = Modifier.align(Alignment.CenterVertically))
        Text(text = "세 번째!", modifier = Modifier.align(Alignment.Bottom))
    }
}
```

2) Column (Alignment : 수직)
```kotlin
@Composable
fun ColumnTest() {
Column(modifier = Modifier.size(100.dp)) {
        Text(text = "첫 번째",modifier = Modifier.align(Alignment.CenterHorizontally))
        Text(text = "두 번째",modifier = Modifier.align(Alignment.Start))
        Text(text = "세 번째", modifier = Modifier.align(Alignment.End))
    }
}
```
<br/>


- **weight 적용해보기**
```kotlin
@Composable
fun RowTest() {
    Row(
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier
            .width(200.dp)
            .height(40.dp)
    ) {
        Text(text = "첫 번째!", modifier = Modifier.align(Alignment.Top).weight(3f))
        Text(text = "두 번째!", modifier = Modifier.weight(2f))
        Text(text = "세 번째!", modifier = Modifier.weight(3f))
    }
}
```
: 현재 코드 상으로 Text가 각각 <u>3: 2: 3 비중</u>을 갖고 있는다고 보면 됨
: Text 외에 다른 요소들에도 적용 가능함
: Row / Column 모두 적용 가능
<br/>
<br/>
<br/>

## 8. BoxWithConstraints
: Box와 비슷한 역할을 수행함<br/>
: 자주 쓰이진 않지만, <u>길이에 맞춰서 유동적으로 제한</u>하고 싶을 때 사용<br/>
: <u>Box의 속성들</u>을 다 사용할 수 있음<br/>
: 추가로 `maxWidth`, `maxHeight`, `minWidth`, `minHeight`값을 받을 수 있음<br/>
(해당 값은 장비에 따라서, preview에 따라서 달라질 수 있음 / dp로 받음)<br/>

cf.) scope가 다름<br/>
: Box -> `BoxScope`<br/>
: BoxWithConstraints -> `BoxWithConstraintScope`<br/>
<br/>

- **BoxWithConstraints 사용하기**
: `Modifier.widIn()`, `Modifier.heightIn()` 사용할 수 있음
```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BoxWithConstraintsTheme {
                Outer()
            }
        }
    }
}
```
```kotlin
@Composable
fun Outer() {
    Column () {
        // widthIn 적용 전 (minW : 0.0, minH : 0.0, maxW: 392.72726, maxH: 130.90909)
        Inner()

        // widthIn 적용 후 (minW : 100.0, minH : 0.0, maxW : 350.18182, maxH : 40.363636)
        Inner(modifier = Modifier.widthIn(min = 100.dp, max = 350.dp))
    }
}
```
```kotlin
@Composable
private fun Inner(modifier: Modifier = Modifier) {
    BoxWithConstraints(modifier) {
        Text("minW: $minWidth minH:$minHeight maxW:$maxWidth maxH:$maxHeight")
    }
}
```
<br/>

- **BoxWithConstraints 조건에 따라 처리하기**
```kotlin
@Composable
fun Outer() {
    Column (modifier = Modifier.width(150.dp)) {
        // 이미 외부에서 width가 150.dp로 제약이 걸렸기 때문에 width는 200.dp가 아닌 150.dp로 적용됨
        Inner(modifier = Modifier.width(200.dp).height(150.dp))
        Inner(modifier = Modifier.width(300.dp).height(300.dp))
    }
}
```
```kotlin
@Composable
private fun Inner(modifier: Modifier = Modifier) {
    BoxWithConstraints(modifier) {
        if (maxHeight > 150.dp) {
            Text(
                text = "여기 꽤 길군요!",
                modifier = Modifier.align(Alignment.BottomEnd)
            )
        }
        Text("minW: $minWidth minH:$minHeight maxW:$maxWidth maxH:$maxHeight")
    }
}
```
- ![img](https://velog.velcdn.com/images/yaebb82/post/483eefc0-dcf8-4b04-9433-872415bc2085/image.png)
