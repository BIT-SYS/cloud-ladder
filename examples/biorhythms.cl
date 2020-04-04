Number case = 0
while true {
    List<String> peid = input().split()
    Number p = peid[0]
    Number e = peid[1]
    Number i = peid[2]
    Number d = peid[3]

    if -1 == p {
        break
    } else {
        case = case + 1
    }

    day = (5544*p+14421*e+1288*i-d+21252)%21252
    if 0 == day {
        day = 21252
    }

    print("Case " + case.toString() + ": the next triple peak occurs in " + day.toString() + " days.")
}
