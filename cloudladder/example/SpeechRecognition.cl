"audio/test1.WAV"
    | audio
    | Audio.play
    ;

"audio/test1.WAV"
    | audio
    | Audio.toSpeechRecognition("zh-CN")
    | print
    ;
