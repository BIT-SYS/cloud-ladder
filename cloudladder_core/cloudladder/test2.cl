//using hello from "cloudladder";

//print(hello());

//x = [1, 3];
//y = ["asd", "zxc", 123, 100];
//Array.interleave(x, y) | Array.map((x): type(x)) | print;

//using String.toAudio from "baidu";
//let x = "1 + 1 = 2";
//print(x | String.toAudio("token", "cuid"));

//using imdb from "cloudladder";
//imdb(2, 10) | MapArray.values() | print;

csv_string = "index,number\n0,666\n1,777";
table = Table.load_csv_string(csv_string);
print(table);
print(Table.row(table, "123"));
