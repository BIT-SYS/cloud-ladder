import "baidu\\baidui.cl" as bi;

"images/flowers"
    | load_dir
    | Array.map(function (path) {
    return path | image | bi.plant | top;
}) | print;