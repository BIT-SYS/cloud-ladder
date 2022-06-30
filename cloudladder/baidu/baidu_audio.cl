import "http";
import "encode\\JSON";
import "encode\\url";

export t2a = function (text, lan){
    url.encode(text)
        -> encoded;
    let baseurl ="https://tts.youdao.com/fanyivoice?";
    baseurl + "word=" + encoded + "&le=" + lan + "&keyfrom=speaker-target"
        ->url;
    print("accessing " + url);

    http.stream_get(url, {}) -> stream;

    return stream;
}