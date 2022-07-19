import "http";
import "encode\\JSON";
import "encode\\url";

export t2a = function (text, lan){
    url.encode(text)
        -> encoded;
    let baseurl ="http://127.0.0.1:8000/t2a?";
    //baseurl + "words=" + encoded + "&le=" + lan + "&keyfrom=speaker-target"
    baseurl + "rate=150&volume=1.0&voice=female&words=" + encoded
        ->url;
    print("accessing " + url);

    http.stream_get(url, {}) -> stream;

    return stream;
}