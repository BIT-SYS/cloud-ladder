import "http";
import "encode/JSON";

import "baidu/baiduToken.cl" as bt;

bt.getAccessToken("FtB3hNLMRAwDnNQpWpfAHqQR", "mGvxKe1xWwapWVy7OHT3bezB1z1GhOTq")
    | bt.t
    -> token
//    | print
    ;

function appendToken(url) {
    return url + "?access_token=" + token;
}

function createApp(url) {
    return function (img) {
        url = appendToken(url);

        let base64 = img | Image.base64;
        let headers = {
            "content-type": "application/x-www-form-urlencoded",
        };
        print("accessing " + url);

        let ret = http.raw(url, "POST", "image=" + base64, headers);
        //print(ret);
        return JSON.parse(ret);
    };
}

export plant = function (img) {
    createApp("https://aip.baidubce.com/rest/2.0/image-classify/v1/plant") -> app;
    app(img).result -> result;
    print(result);
    DiscreteProbability.new() -> prob;

    DiscreteProbability.push -> ppush;

    let do = function (item) {
        prob | ppush(item.score, item.name);
    };

    result | Array.forEach(do);
    return prob;
}

export animal = createApp("https://aip.baidubce.com/rest/2.0/image-classify/v2/animal")