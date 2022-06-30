import "baidu\\baidu_audio.cl" as aud;


let content = text("audio/WordForTest.docx") | Text.getContent ;
aud.t2a(content ,"zh")
    | save("audio/111.mp3")
    ;

audio("audio/111.mp3")
    |Audio.play
    ;
