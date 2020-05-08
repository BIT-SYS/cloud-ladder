Number i = 0
Number j = 0
Number a = 0
while true {
    j = 0
    while true {
        a = a + i
        a = a + j
        if  j > 10 {
            break
        } else {
            continue
        }
        a = a + i
    }
    if i >= 10 {
        break
    }
}