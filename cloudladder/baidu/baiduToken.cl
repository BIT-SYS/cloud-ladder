import "http";
import "encode\\JSON";

export getAccessToken = function (id, secret) {
    let url = "https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials";
    url = url + "&client_id=" + id + "&client_secret=" + secret;

    let ret = http.get(url);
    return JSON.parse(ret);
}

export t = function(tokenRet) {
    return tokenRet["access_token"];
}