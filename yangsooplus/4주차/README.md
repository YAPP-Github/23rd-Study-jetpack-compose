# Compose 이해

## 선언형 UI

- XML 시스템
  - `findViewById`, `setText(String)`, `setImageBitmap(Bitmap)` 등 메서드를 호출하여 UI 노드 변경
    - 수동으로 위젯의 내부 상태 변경
    - 뷰를 수동으로 조작하면 발생할 수 있는 문제?
      - 데이터를 여러 곳에서 렌더링하여 뷰 업데이트 누락 위험
      - 업데이트 충돌 (방금 삭제한 뷰를 업데이트 하는 등...)
- Compose
  - 화면 전체를 개념적으로 재생성, 필요한 변경사항만 적용
  - 잠재적인 리소스가 많이 드는 방식
    - Recomposition으로 지능적으로 다시 그릴 부분 선택

### Composable 함수

- `@Composable` 어노테이션을 명시하여 Compose 컴파일러에 알림
- 데이터를 매개변수로 받아서 표시
- Composable 함수 안에서 Composable 함수를 호출하는 UI 계층 구조
- Composable 함수는 아무것도 반환하지 않음
- 멱등원(Idempotence)으로 동일한 인수로 여러번 호출될 때 동일한 방식으로 작동해야 함

### Recomposition

- 언제 다시 호출?
  - 함수의 입력이 변경될 때 호출
  - 입력이 변경되지 않은 함수는 건너 뛴다
- SideEffect에 의존하면 안됨
  - Recomposition을 뛰어넘을 수 있기 때문
    - ViewModel observable, SharedPreference 업데이트 등...
      - 백그라운드 코루틴으로 읽기 쓰기를 처리
- 애니메이션이 렌더링 되는 빈도와 같은 빈도로 실행될 수 있음

# 상태 관리

### 상태?
- 시간이 지남에 따라 변할 수 있는 값
  - 룸 데이터베이스, 클래스 변수, 스낵바 등등...

## State & Composition

- Compose 업데이트: 새로운 인수로 컴포저블 함수 호출
  - 인수가 곧 UI 상태를 표현

### remember
- 메모리에 객체를 저장
- 객체를 컴포지션에 저장하고, remember를 호출한 컴포저블이 컴포지션에서 삭제되면 그 객체를 잊음

```kotlin
interface MutableState<T> : State<T> {
    override var value: T
}
```
- value가 변경되면 value를 읽는 Composable의 Recomposition이 예약
- `remember`는 Recomposition에서 상태를 유지, configuration change에는 유지하지 못함
  - 이 경우에는 `rememberSaveable`을 사용하여 `Bundle`에 값을 저장

### collectAsStateWithLifecycle
- Flow의 값을 수집하여 State로 변환

| collectAsState                                                   | collectAsStateWithLifeCycle         |
|------------------------------------------------------------------|-------------------------------------|
| Composition의 Lifecycle을 따름. 컴포지션에 들어오면 flow collect 시작, 컴포지션을 떠나면 멈춤 | Lifecylce을 인식하여 필요하지 않을 때 리소스 확보    |
| 앱이 백그라운드에 있을 때도 flow collect가 활성 상태                              | 앱이나 화면이 백그라운드에 있을 때 flow collect 취소 |

## Stateful vs Stateless
- Composable remember을 사용하여 객체를 저장 ➡️ Stateful Composable
  - 내부적으로 상태를 보존학고 수정
  - 직접 상태를 관리하지 않아도 되는 경우
  - 재사용성 ⬇️
- State 호이스팅으로 Stateless 달성

### State Hoisting
- Composable을 Stateless하게 만드는 방법
- 상태를 호출자로 옮기는 패턴
- 특징
  - 단일 정보 소스: 상태를 복제하지 않고 전달하기 때문에 소스가 하나
  - 캡슐화: Stateful한 상위 컴포저블만 상태 수정 가능
  - 공유 가능: 호이스탕한 상태를 다른 컴포저블과 공유
  - 가로채기: 상태를 변경하기 전에 이벤틑 무시할지 결정 가능
  - 분리됨: Stateless 상태는 어디에나 저장 가능

```kotlin
import java.awt.TextField

@Composable
fun GreetingStateful() {
  val greet by remember { mutableStateOf("Welcome!") }
  TextField(value = greet, onValueChanged = { greet = it })
}

fun GreetingStateless(greet: String, onGreetChanged: (String) -> Unit) {
  TextField(value = greet, onValueChanged = { onGreetChanged(it) })
}
```

- State 다운! Event 업! Stateful 비상! 젠장... 또 Stateless 해졌잖아!
- 단방향 데이터 흐름
  - UI에 상태를 표시하는 컴포저블과 상태를 변경하는 비즈니스 로직을 분리

- 상태를 끌어올릴 때 이동 위치를 쉽게 파악할 수 있는 3가지 규칙
1. State는 적어도 그 상태를 사용하는 모든 컴포저블의 **가장 낮은 공통 상위 요소**로 끌어올리기
2. State는 최소한 **변경될 수 있는 가장 높은 수준**으로 끌어올리기
3. 동일한 이벤트에 대한 응답으로 두 상태가 변경되면 **함께** 끌어올려야 함


## Compose State 복원
- `rememberSaveable`을 사용하여 UI State 복원

### Parcelize
```kotlin
@Parcelize
data class City(val name: String, val country: String) : Parcelable

@Composable
fun CityScreen() {
    var selectedCity = rememberSaveable {
        mutableStateOf(City("Madrid", "Spain"))
    }
}
```
- @Parcelize 어노테이션으로 객체를 번들에 저장할 수 있음

### MapSaver
```kotlin
data class City(val name: String, val country: String)

val CitySaver = run {
    val nameKey = "Name"
    val countryKey = "Country"
    mapSaver(
        save = { mapOf(nameKey to it.name, countryKey to it.country) },
        restore = { City(it[nameKey] as String, it[countryKey] as String) }
    )
}

@Composable
fun CityScreen() {
    var selectedCity = rememberSaveable(stateSaver = CitySaver) {
        mutableStateOf(City("Madrid", "Spain"))
    }
}
```
- @Parcelize 가 적합하지 않은 경우
- Bundle에 저장하고 읽는 방법 정의

### ListSaver
```kotlin
data class City(val name: String, val country: String)

val CitySaver = listSaver<City, Any>(
    save = { listOf(it.name, it.country) },
    restore = { City(it[0] as String, it[1] as String) }
)

@Composable
fun CityScreen() {
    var selectedCity = rememberSaveable(stateSaver = CitySaver) {
        mutableStateOf(City("Madrid", "Spain"))
    }
}
```
- MapSaver와 달리 Key를 정의할 필요 없이 인덱스로 저장 가능

## State Holder
- 추적할 상태의 양이 늘어나거나 컴포저블 함수에서 실행하는 로직이 발생하는 경우
- 로직과 상태 책임을 상태 홀더에 위임하는 것이 좋음

`var name by remember { mutableStateOf("") }`
- remember가 처음 실행되면 람다를 호출하고 그 결과를 저장
- 리컴포지션 중에는 마지막으로 저장된 값을 반환
- 계산하는데 비용이 많이 드는 객체를 매 리컴포지션마다 반복한다면?
- remeber에 key 매개변수를 사용
