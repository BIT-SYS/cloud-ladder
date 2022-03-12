function fibo(x) {
    if (x == 0 || x == 1) {
        return 1;
    }

    return fibo(x - 1) + fibo(x - 2);
}

print(fibo(10));
