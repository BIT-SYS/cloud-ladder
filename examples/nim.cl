while true {
    Number n = input().toNumber()

    if 0 == n {
        break
    }
    Number counter = 0
    List<Number> a = input().split().map(|String x| -> Number {x.toNumber()})

    // 只是例子，你要想叫fold也行，变成.fold也行，
    // 另外你看看你定义的匿名函数语法是不是这样用
    Number ans = reduce(|Number x, Number y| -> Number {x xor y}, a, 0)
    for Number i in a {
        if ans xor i < i {
            counter = counter + 1
        }
    }
    print(counter.toString())
}
