let map = [[1,1,1], [0,1,0], [1,1,0]];
let q = [];
let vis = [
    [false, false, false],
    [false, false, false],
    [false, false, false]
];
q | Array.push([0, 0]);
vis[0][0] = true;
let d = [[1,0], [-1,0], [0,1], [0,-1]];

function check(x, y) {
    return
        x >= 0 &&
        x < 3 &&
        y >= 0 &&
        y < 3 &&
        map[x][y] == 1
        && !vis[x][y];
}

while (!Array.empty(q)) {
    Array.pop(q) -> p;
    print(p);
    for (let i = 0; i < 4; i = i + 1) {
        let nx = p[0] + d[i][0];
        let ny = p[1] + d[i][1];
        if (check(nx, ny)) {
            Array.push(q, [nx, ny]);
            vis[nx][ny] = true;
        }
    }
}