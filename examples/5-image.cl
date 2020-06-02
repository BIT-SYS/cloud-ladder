useBaiduForImage("$$token$$")
String text = ImageRead("examples/images/glass.bmp").getString()

print("text is " + text)

if "我能吞下玻璃而不伤身体" == text {
    print("ok!")
} else {
    print("no...")
}

Image carImg = ImageRead("examples/images/car.jpg")
Image animalImg = ImageRead("examples/images/animal.jpg")
Image plantImg = ImageRead("examples/images/plant.jpg")

print("==flat:")

print("Car is " + carImg.detect("car"))
print("Animal is " + animalImg.detect("animal"))
print("Plant is " + plantImg.detect("plant"))

print("==for & list:")

List<Image> images = [carImg, animalImg, plantImg]
List<String> cats = ["car", "animal", "plant"]

Number i = 0
for i in [0..3] {
    print(cats.get(i) + " is " + (images.get(i)).detect((cats.get(i))))
}

Image people = ImageRead("examples/images/woman.jpg")
Image animePeople = people.getAnime()
if animePeople.save("examples/images/anime.jpg") {
    print("anime saved")
} else {
    print("anime not saved")
}
