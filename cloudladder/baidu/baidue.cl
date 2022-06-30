import "http";
import "encode\\JSON";

export getAccessToken = function (id, secret) {
    let url = "https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials";
    url = url + "&client_id=" + id + "&client_secret=" + secret;

    let ret = http.get(url);
    return JSON.parse(ret);
}

//baidue.getAccessToken("T61UqGpP5I9BhSXM7Blw47me", "KGzNtQsU2QfgnpjArKkKDTpRuht9ASpC") | print;
//baidue.setAccessToken("24.3888903b75318c8cafb56b5697d5b74f.2592000.1623576814.282335-23737401");
let token = "24.3888903b75318c8cafb56b5697d5b74f.2592000.1623576814.282335-23737401";
export setAccessToken = function (t) {
    token = t;
}

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
        return JSON.parse(ret);
    };
}

export anime = function (img) {
    let app = createApp("https://aip.baidubce.com/rest/2.0/image-process/v1/selfie_anime");

    let res = app(img);
    //print(res);

    let base64 = res["image"];
    return Image.fromBase64(base64);
}