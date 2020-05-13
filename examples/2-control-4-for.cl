proc add1(Number x) -> Number {
    x + 1
}

Number x = 1
for x in [1..2] {
    for Number x in [3..4] {
        print(add1(x).toString())
    }
}