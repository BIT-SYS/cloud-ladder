proc add1(Number x) -> Number {
    x + 1
}

Number x = 1
for x in [1..3] {
    for Number x in [3..5] {
        print(add1(x))
    }
}