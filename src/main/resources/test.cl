let f = function() {
    let i = 0;
    for1: while (i < 10) {
        i = i + 1;
        let j = 0;
        while (j < 10) {
            j = j + 2;
            print(j);
            if (j >= 5) {
                break for1;
            }
        }
    }
};
print(dis(f));
f();