Number i = 1
Number j = 0
Number a = 0
while true {
    j = 0
    while true {
        a = a + i
        j = a + i
        if  j > 10 {
            break
        } else {
            continue
        }
        a = a + i
    }
    if j >= 10 {
        break
    }
}