let key1 = "kk";
let obj = {
    k: 1,
    [key1]: 2,
    key1
};

let keys = ["k", key1, "key1"];
for (let i = 0; i < 3; i = i + 1) {
    print(obj[keys[i]]);
}
