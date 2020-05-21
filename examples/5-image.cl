useBaiduForImage("$$token$$")
String text = im_read("examples/images/guess.bmp").getString()

print("text is " + text)

if "猜猜我写的是什么字" == text {
    print("ok!")
} else {
    print("no...")
}