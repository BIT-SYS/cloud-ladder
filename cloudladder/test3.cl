let dx = 0.001;

export deriv = function(f) {
    return function(x) {
        return (f(x + dx) - f(x)) / dx;
    };
};

export integrate = function(f, a, b) {
    let i = a;
    let result = 0;
    while (i < b) {
        result = result + f(i) * dx;
        i = i + dx;
    }
    return result;
};

print("test3.cl");
