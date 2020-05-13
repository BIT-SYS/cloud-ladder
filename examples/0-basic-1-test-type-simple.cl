List<String> string_list = ["Cats's", "foot", "iron", "claw"]

String cat = string_list[0]

proc word_count(String string) -> Number {
    42
}

Number how_many_foot = word_count(cat)

proc foot(String self) -> Number {
    42
}

for cat in string_list {
    Boolean is_the_same = cat.foot() == how_many_foot
}

// 测试两个 map，List<Number>和List<String>的
List<Number> fooot = string_list.map(|String string| -> Number {word_count(string)})

List<Number> foooot = fooot.map(|Number num| -> Number {num+1-1})

// 打开注释检查重定义变量有没有报错
// List<Number> fooot = string_list.map(|String string| -> Number {word_count(string)})

proc id(TypeA a) -> TypeA {
    a
}

String aString = id("a")
Number aNumber = id(fooot[1])