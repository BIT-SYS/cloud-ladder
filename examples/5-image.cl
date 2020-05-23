useBaiduForImage("$$token$$")
String text = im_read("examples/images/guess.bmp").getString()

print("text is " + text)

if "猜猜我写的是什么字" == text {
    print("ok!")
} else {
    print("no...")
}

print("Car is " + im_read("examples/images/car.jpg").detect("car"))
print("Animal is " + im_read("examples/images/animal.jpg").detect("animal"))
print("Plant is " + im_read("examples/images/plant.jpg").detect("plant"))

List<Image> images = [im_read("examples/images/car.jpg"), im_read("examples/images/animal.jpg"), im_read("examples/images/plant.jpg")]
List<String> cats = ["car", "animal", "plant"]
print(cats.get(0) + " is " + (images.get(0)).detect((cats.get(0))))
Number i = 0
for i in [0..3] {
    print(cats.get(i) + " is " + (images.get(i)).detect((cats.get(i))))
}

Image people = im_read("examples/images/woman.jpg")
Image animePeople = people.getAnime()
if animePeople.save("examples/images/anime.jpg") {
    print("anime saved")
} else {
    print("anime not saved")
}