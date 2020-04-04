while true {
    if 0 == n {
        break
    }
    Number counter = 0
    List<Number> a = input().split().map(|x| x.toNumber())

    // 只是例子，你要想叫fold也行，变成.fold也行，
    // 另外你看看你定义的匿名函数语法是不是这样用
    Number ans = reduce(|x, y| x^y, a, 0)
    for Number i in a {
        if ans ^ i < i {
            counter = counter + 1
        }
    }
    print(counter)
}
