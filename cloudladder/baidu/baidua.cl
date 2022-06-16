import "http";
import "encode/url";
import "baidu/baiduToken.cl" as bt;

bt.getAccessToken("YMdwxMRCVXMdWR7c5CZ8UNbe", "2Lbb2mOFCkDwvIMlGTSM45LGe1E4b8WC")
    | bt.t
    -> token;

//let token = "24.948bfe1cc75a59933f2fa973b01b8b4d.2592000.1623580626.282335-24171393";

let baseURL = "http://tsn.baidu.com/text2audio?lan=zh&ctp=1&cuid=123456&tok=" + token + "&per=1&spd=5&pit=5&vol=5";
export tts = function (text) {
    url.encode(text)
        -> encoded;
    baseURL + "&tex=" + encoded
        -> url;
    print("accessing " + url);

    http.stream_get(url, {}) -> stream;

    return stream;
}