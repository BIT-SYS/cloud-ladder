let x = {
    "key1": 1,
    "key2": 2
};

let keys = ["key1", "key2"];
for (let i = 0; i < 2; i = i + 1) {
    let key = keys[i];
    print(x[key]);
}
