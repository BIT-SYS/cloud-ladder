<div style="text-align: center">

# CloudLadder语法

</div>

此处仅给出常见的语法，详细语法见`src/main/antlr/*.g4`，即`ANTLR`的语法描述文件

## 定义变量
```
let <iden> = <expression>;
```

## 列表字面值
```
let x = [<expression1>, <expression2> ..., <expression_n>];
```

## 字符串、数字、布尔值字面量
```
let x = "string";
let y = 3.14;
let z = true;
let a = false;
```

## 字典
```
let key3 = "123";
let x = {
    key1: value1,       // key可以不带引号
    "key2": value2,     // key也可以带引号
    key3,               // 省略value，表示用变量的名字作为key，变量的值作为value
    [key3]: value4      // 变量的值作为key
};
```

## 控制结构
```
if (<expression>) <statement> else <statement>
if (<expression>) <statement>
```

```
for (let i = 0; i < 10; i += 1) {
    // ...
}
```

```
for (...) {
    break;
    continue;
}
```

## 函数
```
// 可以直接在外部定义
function f() {
    return 1;
}

// 可以把函数当作变量，类似Python，或者JS
let sum = function(x, y) {
    return x + y;
};

// 函数调用跟大多数语言都一样
let result = sum(1, x)
```

## 表达式
```
// 这些跟通常类C语言没区别
let x = a + b;
let x = a - b;
let x = a * b;
let x = a / b;
let x = a % b;
let x = a && b;
let x = a || b;
let x = a == b;
let x = a != b;
let x = a < b;
let x = a <= b;
let x = a > b;
let x = a >= b;
let x = -a;
let x = !a;

// 字典取值，类似JS
let x = a.b;
// 数组或字典取值
let x = a[b];
// 函数调用
let x = a(b);
```

### 管道表达式
与Linux Shell的管道表达式类似
```
let x = 0;
// 等价于print(x)
x | print;
x | print();

// 等价于Image.show(x)
x | Image.show;
x | Image.show();

function ge(x, t) { return t >= x; }

// 等价于ge(x, 10)
// 即总是把左边的数作为右边函数调用的第一个参数
x | ge(10)
```