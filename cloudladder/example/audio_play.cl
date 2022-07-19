import "baidu\\baidu_audio.cl" as aud;

//let cltext = text("audio/Test.pdf");
let cltext = text("audio/ctext.pdf");
//let cltext = text("audio/WordForTest.pdf");

let content = Text.getContent(cltext);
Text.createWordCloud(cltext, "D:\\ciyun.png");
print(content);
aud.t2a(content ,"zh")
    | save("audio/111.mp3")
    ;

audio("audio/111.mp3")
    |Audio.play
    ;
