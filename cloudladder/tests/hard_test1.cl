function f(x, y) {
    let temp = x * y;
    return function(z) {
        return z + temp;
    };
}

print(f(3, 9)(10));
