List<String> string_list = ["Cats's", "foot", "iron", "claw"]

String cat = string_list[0]

proc word_count(String string) -> Number {
    42
}

Number how_many_foot = word_count(cat)

proc foot(String self) -> Number {
    42
}

Boolean is_the_same = cat.foot() == how_many_foot
