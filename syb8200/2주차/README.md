# 1. Image

- xml에서의 `ImageView`가 → Compose에서는 `Image`라고 생각하면 된다.   
- `Image` 속성은 크게 3개로 나뉜다.   

   ![Untitled](https://github.com/YAPP-Github/23rd-Study-jetpack-compose/assets/70886911/b4e3c103-e18e-4672-8d9c-87536640d6c2)

    
### 1) **디스크에서 이미지 로드**

```kotlin
@Composable
fun ImageTest() {
    Column {
        // Drawable에 있는 이미지(아이콘)를 사용하고 싶을 때 : *painter* 사용
        Image(
            painter = painterResource(id = R.drawable.wall),
            contentDescription = null
        )

        // 기존에 내장된 Vector 이미지(아이콘) 사용하고 싶을 때 : *imageVector* 사용
        Image(
            imageVector = Icons.Filled.Settings,
            contentDescription = null
        )
    }
}
```

- 결과
    
    <img width="307" alt="Untitled 1" src="https://github.com/YAPP-Github/23rd-Study-jetpack-compose/assets/70886911/0265c8d2-37c1-4f54-91c9-e8a555c60bd1">

    

### 2) 인터넷에서 이미지 로드

- 네트워크에서 이미지를 갖고 올 경우, preview에서 지원이 잘 되지 않기 때문에 에뮬레이터를 사용해서 확인하는 것이 좋다.
- 공식문서에서 보여주는 `Coil` 라이브러리를 사용한 이미지 로드 예시
    
    ```kotlin
    @Composable
    fun CoilTest() {
        AsyncImage(
            // model에 이미지 link 적어주기
            model = "https://images.unsplash.com/photo-1628373383885-4be0bc0172fa?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1301&q=80",
            contentDescription = null
        )
    }
    ```
    
    - 결과
        
        <img width="306" alt="Untitled 2" src="https://github.com/YAPP-Github/23rd-Study-jetpack-compose/assets/70886911/5026d47b-afd6-441a-9ed0-88892a00b435">

        

cf.) 공식문서에 왜 `Coil`이 예시로 있는지?

: Jetpack Compose를 지원한다.

: Kotlin을 사용하여 구축되었고, 비동기 이미지 로드 작업을 처리하기 위해 Coroutine을 활용한다.

### 3) 이미지 맞춤 설정

[https://developer.android.com/jetpack/compose/graphics/images/customize?hl=ko](https://developer.android.com/jetpack/compose/graphics/images/customize?hl=ko)

<br/>

# 2. Icon

- `Icon`은 Material Design 가이드라인을 따르는 단일 색상의 아이콘을 화면에 그리는 방법이다.   
- `Icon` 속성은 크게 3개로 나뉜다.   
    <img width="526" alt="Untitled 3" src="https://github.com/YAPP-Github/23rd-Study-jetpack-compose/assets/70886911/055cc910-53ea-471d-9c7e-d1b5bc7151e1">
    
    

- 머티리얼 아이콘 라이브러리에는 SVG를 수동으로 가져오지 않고도 Compose에서 사용할 수 있는 `Icons` 집합이 사전 정의되어 있다. (Default == Filled)
    
    ![Untitled 4](https://github.com/YAPP-Github/23rd-Study-jetpack-compose/assets/70886911/dc0b767d-6315-402e-9805-25d281064d54)

    

**cf.) 추가 설명**

- `Icon`
    - 작은 아이콘 요소에 사용하기 위한 용도 (24.dp)
    - tint 설정
        
        ```kotlin
        Icon(
            modifier = Modifier.padding(8.dp),
            tint = Color.Yellow,
            imageVector = Icons.Filled.Star,
            contentDescription = "Cover"
        )
        ```
        
- `Image`
    - 더 많은 맞춤설정 옵션이 필요한 경우에 사용
    - tint 설정
        
        ```kotlin
        Image(
            painter = painterResource(id = R.drawable.baseline_directions_bus_24),
            contentDescription = stringResource(id = R.string.bus_content_description),
            colorFilter = ColorFilter.tint(Color.Yellow)
        )
        ```
        
<br/>

# 3. CheckBox

### 1) CheckBox 속성

```kotlin
@Composable
fun Checkbox(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: CheckboxColors = CheckboxDefaults.colors()
) {
    TriStateCheckbox(
        state = ToggleableState(checked),
        onClick = if (onCheckedChange != null) { { onCheckedChange(!checked) } } else null,
        interactionSource = interactionSource,
        enabled = enabled,
        colors = colors,
        modifier = modifier
    )
}
```

### 2) CheckBox 사용하기

```kotlin
@Composable
fun CheckBoxEx() {
	Row(verticalAlignment = Alignment.CenterVertically) {
		Checkbox(checked = false, onCheckedChange = {})
	}
}
```

- `checked` : 체크박스를 선택하거나 선택 취소하도록 설정하는 데 사용됩니다.
    
    ⇒ 기존 xml 때와는 다르게 `checked` 상태를 바꿔줘야 CheckBox가 체크된다.
    
- `onCheckedChange` : 체크박스 선택 여부에 관계없이 이벤트에 변경이 있을 때 수신되는 콜백입니다.

### 3) CheckBox 상태 바꾸기

```kotlin
@Composable
fun CheckBoxEx() {
        Row(verticalAlignment = Alignment.CenterVertically) {
        val (checked, setChecked) = remember{ mutableStateOf(false) }
        Checkbox(
            checked = checked,
            onCheckedChange = setChecked
        )
        Text(
            text = "Compose 공부를 했습니까?",
            modifier = Modifier.clickable {
                setChecked(!checked)
            }
        )
    }
}
```

- 결과
    
  <img width="307" alt="Untitled 5" src="https://github.com/YAPP-Github/23rd-Study-jetpack-compose/assets/70886911/1443307e-84dc-40d2-838a-86ba910a2eeb">
    
    

**cf.) 추가 설명**

- `mutableStateOf()`
    - `MutableState`라는 타입의 객체를 생성하고, Compose에서 관찰 가능한 상태를 표현한다.
    - 상태의 값이 변경되면, 이 상태를 참조하는 모든 컴포넌트가 자동으로 다시 구성되어 최신 상태를 반영하게 됩니다.
- `remember`
    - *@Composable* 함수의 생명주기를 알면 이해하기 쉽다. (4주차 상태관리)
        
        ![Untitled 6](https://github.com/YAPP-Github/23rd-Study-jetpack-compose/assets/70886911/ee41926a-45dd-483d-ac92-1e5ddb1234b5)

        
    - recomposition은 UI를 구성하는 데이터가 변경되었을 때 발생하는데, 일어나지 않을 수도 있고, 여러 번 일어날 수도 있다.
    - recomposition이 발생하면 `mutableStateOf()`이 날라가게 된다.
    - `remember`를 사용하면 생성된 객체를 recomposition 사이클 동안 유지할 수 있다.
        
        ⇒ recomposition이 되어도 데이터를 보존해야 할 때 사용하는 것이 좋다. 
        
        ⇒ 이전 상태를 기억했다가 나중에 다시 사용하게 해주는 역할
        

---

- **구조 분해(Destruction) 선언**으로 상태값 받기
    - 구조 분해 선언
        - 객체가 가지고 있는 여러 값을 분해해서 여러 변수에 한꺼번에 초기화 가능
        - 구조 분해 선언의 내부에서는 각 변수를 초기화하기 위해 componentN이라는 함수를 호출함
        - 여기서 N은 구조 분해 선언에 있는 변수 위치에 따라붙는 번호이다.
    - MutableState 내부
        
        ```kotlin
        @Stable
        interface MutableState<T> : State<T> {
            override var value: T
            operator fun component1(): T    // getter
            operator fun component2(): (T) -> Unit    // setter
        }
        ```
        
    - 코드 살펴보기
        
        ```kotlin
        // MutableState 객체의 getter와 setter를 각각 checked와 setChecked 변수에 바인딩
        val (checked, setChecked) = remember{ mutableStateOf(false) }
        Checkbox(
            // checked를 사용해서 현재 체크 상태 갖고옴
            checked = checked,
            // setChecked를 사용해서 체크 상태를 변경함
            onCheckedChange = setChecked
        )
        ```
        

### 4) Grouped CheckBox

```kotlin
@Composable
fun CheckBoxEx() {
    Column {
        val fruitsList = arrayListOf("Apple", "Mango", "Orange")
        fruitsList.forEach { option: String ->
            Spacer(modifier = Modifier.size(16.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val (checked, setChecked) = remember { mutableStateOf(false) }
                Checkbox(
                    checked = checked,
                    onCheckedChange = setChecked
                )
                Text(option)
            }
        }
    }
}
```

- 결과
    
    ![Untitled 7](https://github.com/YAPP-Github/23rd-Study-jetpack-compose/assets/70886911/f2e925ed-81e9-462b-a44b-be81f241ae1c)

    
<br/>

# 4. TextField

- `TextField`를 통해 사용자는 텍스트를 입력하고, 수정할 수 있다.
- xml에서의 `EditText`가 → Compose에서는 `TextField`라고 생각하면 된다.

### 1) TextField / BasicTextField

- `TextField`
    - Material Design 가이드라인을 따름 (변경이 제한적임)
    - 공식문서에서 권장함
    
    ```kotlin
    @Composable
    fun TextField(
        value: String,
        onValueChange: (String) -> Unit,
        modifier: Modifier = Modifier,
        enabled: Boolean = true,
        readOnly: Boolean = false,
        textStyle: TextStyle = LocalTextStyle.current,
        label: @Composable (() -> Unit)? = null,
        placeholder: @Composable (() -> Unit)? = null,
        leadingIcon: @Composable (() -> Unit)? = null,
        trailingIcon: @Composable (() -> Unit)? = null,
        isError: Boolean = false,
        visualTransformation: VisualTransformation = VisualTransformation.None,
        keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
        keyboardActions: KeyboardActions = KeyboardActions(),
        singleLine: Boolean = false,
        maxLines: Int = Int.MAX_VALUE,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
        shape: Shape =
            MaterialTheme.shapes.small.copy(bottomEnd = ZeroCornerSize, bottomStart = ZeroCornerSize),
        colors: TextFieldColors = TextFieldDefaults.textFieldColors()
    ) { ... }
    ```
    
- `BasicTextField`
    - Text를 보여주고 사용자의 입력을 처리하기 위한 뼈대
    - 앱의 독자적인 디자인을 구축해야 할 때 사용
    
    ```kotlin
    @Composable
    fun BasicTextField(
        value: String,
        onValueChange: (String) -> Unit,
        modifier: Modifier = Modifier,
        enabled: Boolean = true,
        readOnly: Boolean = false,
        textStyle: TextStyle = TextStyle.Default,
        keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
        keyboardActions: KeyboardActions = KeyboardActions.Default,
        singleLine: Boolean = false,
        maxLines: Int = Int.MAX_VALUE,
        visualTransformation: VisualTransformation = VisualTransformation.None,
        onTextLayout: (TextLayoutResult) -> Unit = {},
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
        cursorBrush: Brush = SolidColor(Color.Black),
        decorationBox: @Composable (innerTextField: @Composable () -> Unit) -> Unit =
            @Composable { innerTextField -> innerTextField() }
    ) { ... }
    ```
    
    [https://semicolonspace.com/jetpack-compose-basictextfield/](https://semicolonspace.com/jetpack-compose-basictextfield/)
    

### 2) TextField / OutlinedTextField 사용하기

- TextField
    
    ```kotlin
    @Composable
    fun TextFieldTest() {
        var name by remember{ mutableStateOf("예빈") }
    
        Column(modifier = Modifier.padding(16.dp)) {
            TextField(
                value = name,
                label = {
                    Text("이름")
                },
                onValueChange = {name = it}
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = "Hello $name")			
        }
    }
    ```
    
- OutlinedTextField
    
    ```kotlin
    @Composable
    fun TextFieldTest() {
        var name by remember{ mutableStateOf("예빈") }
    
        Column(modifier = Modifier.padding(16.dp)) {
            OutlinedTextField(
                value = name,
                label = {
                    Text("이름")
                },
                onValueChange = {name = it}
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = "Hello $name")			
        }
    }
    ```
    
- 결과
    
    ![Untitled 8](https://github.com/YAPP-Github/23rd-Study-jetpack-compose/assets/70886911/600ee51c-8d3b-47b9-8e95-0de38e8f810c)

    

### 3) 키보드 옵션 / 형식 지정

```kotlin
@Composable
fun PasswordTextField() {
    var password by rememberSaveable { mutableStateOf("") }

    TextField(
        value = password,
        onValueChange = { password = it },
        label = { Text("Enter password") },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    )
}
```

- 입력 값에 `VisualTransformation`을 설정할 수 있다.
    
    ex) 비밀번호를 나타내는 문자를 `*` 기호로 바꾸기, 신용카드 번호의 4자리마다 `-` 삽입하기 …
    
    ![Untitled 9](https://github.com/YAPP-Github/23rd-Study-jetpack-compose/assets/70886911/50033f90-0add-4a4e-a4d8-45903721deca)

    
- 키보드 옵션 (KeyboardType)
    
    ![Untitled 10](https://github.com/YAPP-Github/23rd-Study-jetpack-compose/assets/70886911/9c616982-e910-4d07-85b6-06f79e083572)


<br/>

# 5. Spacer

- Composable 사이에 공간을 추가하고 싶을 때 사용한다.

```kotlin
@Composable
fun SpacerDemo() {
    Column {
        Text("Hello")
        Spacer(modifier = Modifier.size(30.dp))
        Text("World")
    }
}
```

- 결과
    
    ![Untitled 11](https://github.com/YAPP-Github/23rd-Study-jetpack-compose/assets/70886911/590eb115-4184-4b39-a6cc-9204535602cf)

    

cf.) `Spacer()` vs `Modifier.padding()` : 언제 사용하는게 좋을까?

[https://stackoverflow.com/questions/72368888/jetpack-compose-spacer-vs-modifier-padding](https://stackoverflow.com/questions/72368888/jetpack-compose-spacer-vs-modifier-padding)

<br/>

# 6. Divider

- 목록과 레이아웃의 콘텐츠를 그룹화하는 얇은 선이다.

```kotlin
@Composable
fun Divider(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.onSurface.copy(alpha = DividerAlpha),
    thickness: Dp = 1.dp, // 기본적으로 1 dp 사용
    startIndent: Dp = 0.dp
): Unit
```

![Untitled 12](https://github.com/YAPP-Github/23rd-Study-jetpack-compose/assets/70886911/e6c726c2-038f-43e7-baa8-84f364412d87)


# 7. Slot API

> `Slot` 사전적 정의 : 구멍 / 자리, 시간, 틈 / 넣다

- 개발자가 원하는 대로 채울 수 있도록 UI에 빈 공간을 남겨둔다.
- Composable 함수가 다른 Composable 함수나, 컴포넌트를 포함할 수 있는 것 (Row, Column, …)
- 체계적으로 composable을 합성하고 관리할 수 있게 되었다. (구성요소의 유연성 향상)

<img width="402" alt="Untitled 13" src="https://github.com/YAPP-Github/23rd-Study-jetpack-compose/assets/70886911/ef2dd05d-81bf-487e-9b3f-60d8814faa3a">


### 1) Slot API 사용하기

```kotlin
@Composable
fun CheckboxWithSlot(
    checked: Boolean, // 직접 바꾸지 않기 때문에 MutableState가 아닌 Boolean 값만 받아와서 세팅한다.
    onCheckedChanged: () -> Unit, // 내부에서 직접 상태를 바꾸지 않게 하기 위해 람다를 받는다.
    content: @Composable RowScope.() -> Unit // 마지막 인자를 content(후행 람다)로 주는 편이다.
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable {
            onCheckedChanged()
        }
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = { onCheckedChanged() }
        )
        // CheckboxWithSlot 함수 내부에서 Text를 표기하는 것을 책임지지 않기 위함
        // 호출하는 측에서 책임을 지게 만들기 위함
        content()
    }
}
```

```kotlin
@Composable
fun SlotEx() {
    var checked1 by remember { mutableStateOf(false) }
    var checked2 by remember { mutableStateOf(false) }

    Column {
        CheckboxWithSlot(
            checked = checked1,
            onCheckedChanged = {
                checked1 = !checked1
            }
        ) {
            Text("텍스트 1")
        }
        CheckboxWithSlot(
            checked = checked2,
            onCheckedChanged = {
                checked2 = !checked2
            }
        ) {
            Text("텍스트 2")
        }
    }
}
```

- 결과
    
    <img width="118" alt="Untitled 14" src="https://github.com/YAPP-Github/23rd-Study-jetpack-compose/assets/70886911/6360c613-e907-4237-a2e1-14e226f07139">

    

**cf.) 추가 설명**

- Composable 함수는 대문자로 시작한다.
- *CheckboxWithSlot* 함수는 상태를 몰라도 되고, 상태를 바꾸는 것도 외부로 위임시킴 + 재사용성도 높임
- `content : @Composable RowScope.() → Unit`
    - Row 안에서 content를 사용하고 있으니까 RowScope를 사용한다.
    - RowScope로 바꾸면 람다 내에서 RowScope 안에 있는 것 처럼 사용할 수 있다.
        
        <img width="253" alt="Untitled 15" src="https://github.com/YAPP-Github/23rd-Study-jetpack-compose/assets/70886911/a29f1984-f06d-41a3-9b44-c34ebac40076">

<br/>

# 8. Scaffold

- Scaffold는 Slot API를 확장한 것이다.
- Material Design 가이드라인에 따라 앱 구조를 빠르게 조합하는 데 사용할 수 있는 간단한 API를 제공한다.
- `TopBar`, `BottomBar`, `FAB`(Floating Action Button)와 같은 항목들을 추가할 수 있다.

### 1) Scaffold 속성

```kotlin
@Composable
fun Scaffold(
    modifier: Modifier = Modifier,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable (SnackbarHostState) -> Unit = { SnackbarHost(it) },
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    isFloatingActionButtonDocked: Boolean = false,
    drawerContent: @Composable (ColumnScope.() -> Unit)? = null,
    drawerGesturesEnabled: Boolean = true,
    drawerShape: Shape = MaterialTheme.shapes.large,
    drawerElevation: Dp = DrawerDefaults.Elevation,
    drawerBackgroundColor: Color = MaterialTheme.colors.surface,
    drawerContentColor: Color = contentColorFor(drawerBackgroundColor),
    drawerScrimColor: Color = DrawerDefaults.scrimColor,
    backgroundColor: Color = MaterialTheme.colors.background,
    contentColor: Color = contentColorFor(backgroundColor),
    content: @Composable (PaddingValues) -> Unit
) { ... }
```

cf.) M2와 M3에 따라서 차이가 있는 것 같다.

[https://thdev.tech/android/2023/01/25/Android-Compose-Scaffold/](https://thdev.tech/android/2023/01/25/Android-Compose-Scaffold/)

### 2) Scaffold 사용해보기

```kotlin
@Composable
fun CheckBoxWithContent(
    checked: Boolean,
    toggleState: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { toggleState() }
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = { toggleState() },
        )
        content()
    }
}
```

```kotlin
@Composable
fun ScaffoldEx() {
    var checked by remember { mutableStateOf(false) }

    Scaffold(
        // topAppBar
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Image(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "뒤로 가기"
                        )
                    }
                },
                title = {
                    Text(text = "Scaffold App")
                }
            )
        },
        // floatingActionButton
        floatingActionButton = {
            FloatingActionButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "null"
                )
            }
        }
    ) {
        Surface(modifier = Modifier.padding(8.dp)) {
            CheckBoxWithContent(
                checked = checked,
                toggleState = { checked = !checked }
            ) {
                Text(text = "컴포즈를 좋아합니다.")
            }
        }
    }
}
```

- 결과
    
    <img width="222" alt="Untitled 16" src="https://github.com/YAPP-Github/23rd-Study-jetpack-compose/assets/70886911/b2c3b1ee-5cac-4afb-bee9-20fb3016f6b2">
