# 第六章：ArrayList 和真正的游戏

---

## 一、先说一个问题

你在写 `SimpleStartup` 时，位置存在 `int[]` 数组里。**数组有个致命缺点：大小固定，创建时必须指定，之后不能改变。**

但在真正的游戏里，战舰被打沉后你希望把它从"存活列表"里**删掉**。数组做不到——你只能把格子设成 `null`，但那个槽位还占着，长度永远是 3。

这就是 `ArrayList` 存在的理由：**会自动伸缩的"智能数组"**。

---

## 二、ArrayList 是什么——用购物清单打比方

**普通数组 = 固定格数的表格**

| 格子0 | 格子1 | 格子2 |
|-------|-------|-------|
| 牛奶 | 鸡蛋 | （空） |

创建时你说"我要3格"，之后格子数永远是3，买完东西你只能划掉，格子还在。

**ArrayList = 便利贴清单**

- 想加就加，想删就删，清单自动变长变短
- 有专门的"动作"可以做：加、删、查、数数……

```java
ArrayList<String> list = new ArrayList<String>();
list.add("牛奶");               // 加
list.add("鸡蛋");
list.remove(0);                 // 删第0个
int size = list.size();         // 数数：现在几个？
boolean has = list.contains("鸡蛋");  // 有没有？
```

---

## 三、ArrayList vs 数组——逐项对比

| 操作 | 数组 | ArrayList |
|------|------|-----------|
| 创建 | `new String[3]`（必须给大小） | `new ArrayList<String>()`（不用给） |
| 放东西进去 | `arr[0] = "a"`（必须指定位置） | `list.add("a")`（直接加到末尾） |
| 取东西 | `arr[1]` | `list.get(1)` |
| 删东西 | `arr[1] = null`（只是清空，格子还在） | `list.remove(1)`（真的删，后面的自动前移） |
| 数量 | `arr.length` | `list.size()` |
| 有没有某个 | 要自己写循环 | `list.contains(obj)` |
| 是否为空 | 要自己判断 | `list.isEmpty()` |

**关键区别：** 数组用的是特殊语法（`[]`），ArrayList 是普通对象，调普通方法。

---

## 四、用 ArrayList 修掉 Startup 的 bug

回忆上一节的 bug：同一个格子猜两次会重复计数。**用 ArrayList 有个更优雅的修法**——猜中了就直接把这个格子**从列表里删掉**，以后它根本不存在，自然不会重复命中。

```java
// 旧版：存 int 数字，需要 boolean[] alreadyHit 追踪
int[] locationCells;

// 新版：存 String（如 "A3"），猜中直接删，不需要 alreadyHit
ArrayList<String> locationCells;
```

新版 `checkYourself` 逻辑：

```java
public String checkYourself(String userInput) {
    String result = "miss";
    int index = locationCells.indexOf(userInput); // 找 "A3" 在不在列表里
    if (index >= 0) {              // 在的话 indexOf 返回位置，不在返回 -1
        locationCells.remove(index); // 命中！直接从列表删掉这个格子
        if (locationCells.isEmpty()) { // 列表空了 = 三格全被打掉 = 沉船
            result = "kill";
        } else {
            result = "hit";
        }
    }
    return result;
}
```

**思路对比：**

| | 旧版 | 新版 |
|---|---|---|
| 防重复命中 | `boolean[] alreadyHit` 记录哪些打过 | 打中就删，物理消失 |
| 判断是否沉船 | `numOfHits == locationCells.length` | `locationCells.isEmpty()` |
| 格子类型 | `int`（数字位置） | `String`（如 "A3"，支持网格） |

---

## 五、真正的游戏 StartupBust——结构拆解

### 三个文件，五个对象

```
GameHelper        ← 工具人：读键盘输入、随机摆战舰位置
Startup（3个）    ← 战舰：知道自己在哪、被猜中怎么反应
StartupBust       ← 游戏主脑：把其他所有对象组织起来
```

### StartupBust 的四个方法

每个方法只干一件事，这样出了问题方便定位：

```
setUpGame()       → 造三艘船，给名字，随机摆位置
startPlaying()    → 主循环：一直问用户猜什么，直到全沉
checkUserGuess()  → 拿用户猜的值，逐一问每艘船"中了吗"
finishGame()      → 打印结束语，根据猜的次数评价好坏
```

### 游戏怎么跑起来——一步步追踪

```
main() 被调用
  → new StartupBust()           创建游戏对象
  → game.setUpGame()
      → new Startup() × 3       造三艘船
      → startups.add(...)        加入 ArrayList
      → helper.placeStartup()    随机位置（返回 ["A2","A3","A4"] 这样的列表）
      → startup.setLocationCells(...) 每艘船记住自己的位置
  → game.startPlaying()
      → while (!startups.isEmpty())   还有活着的船就继续
          → helper.getUserInput()     问玩家猜什么
          → checkUserGuess(userGuess)
              → 循环问每艘船 checkYourself()
              → 如果 "kill" → 从 startups 列表删掉这艘船
      → finishGame()             全沉了，打印评价
```

### `while (!startups.isEmpty())` 是什么意思

`!` 是"取反"——`isEmpty()` 返回"列表是不是空的"，加上 `!` 就是"**不**为空"。

```
startups.isEmpty()  = "没船了吗？"
!startups.isEmpty() = "还有船吗？"
```

所以整句话：**还有船活着，就一直循环**。不需要手动维护 `isAlive` 这种变量，直接问列表本身。

---

## 六、新语法：布尔运算符

| 符号 | 含义 | 例子 |
|------|------|------|
| `&&` | 并且（两个都要满足） | `price >= 300 && price < 400` |
| `\|\|` | 或者（满足一个就行） | `brand.equals("A") \|\| brand.equals("B")` |
| `!` | 取反 | `!startups.isEmpty()`（不为空） |
| `!=` | 不等于 | `model != 2000` |

### 短路特性（很重要）

- `&&`：左边是 `false`，右边**不看**——两个都要真，左边已经假了，结果必然假
- `||`：左边是 `true`，右边**不看**——一个为真就够了

实际用处：

```java
// 先判断 refVar 不为 null，才敢调它的方法
// 如果 refVar == null，左边就短路了，右边不会执行，不会崩溃
if (refVar != null && refVar.isValidType()) {
    // 安全
}
```

---

## 七、这一节最重要的两件事

**1. 知道 Java 有"现成的工具类"**

`ArrayList` 是 Java 自带的，不需要自己写。学 Java 的核心技能之一就是**知道 API 里有什么**，不要重复造轮子。遇到问题先想"Java 有没有自带的方法能做这件事"。

**2. 用 ArrayList 比用数组更适合"动态列表"**

| 场景 | 用什么 |
|------|--------|
| 数量不确定、会增删 | ArrayList（游戏中存活战舰列表） |
| 数量固定、不变 | 数组（坐标 `{0,1,2}` 就三个） |

---

## 八、ArrayList 常用方法速查

| 方法 | 作用 |
|------|------|
| `add(obj)` | 加到末尾 |
| `add(index, obj)` | 插到指定位置 |
| `remove(index)` | 删除指定位置的元素 |
| `remove(obj)` | 删除第一个匹配的元素 |
| `get(index)` | 取指定位置的元素 |
| `size()` | 返回元素个数 |
| `contains(obj)` | 是否包含某元素，返回 boolean |
| `isEmpty()` | 是否为空，返回 boolean |
| `indexOf(obj)` | 返回元素的位置，找不到返回 -1 |