useBaiduForImage("$$token$$")
String text = im_read("examples/images/guess.bmp").getString()

print("text is " + text)

if "猜猜我写的是什么字" == text {
    print("ok!")
} else {
    print("no...")
}

print("Car is " + im_read("examples/images/car.webp").detect("car"))
print("Animal is " + im_read("examples/images/animal.webp").detect("animal"))
print("Plant is " + im_read("examples/images/plant.webp").detect("plant"))
