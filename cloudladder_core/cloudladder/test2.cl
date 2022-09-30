import "test3.cl";

export f1 = function(x) {
    return x * x;
};

export f2 = test3.deriv(f1);