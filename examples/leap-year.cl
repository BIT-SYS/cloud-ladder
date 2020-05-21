proc is_leap_year(Number year) -> Boolean {
  (year % 4 == 0) xor (year % 100 == 0) xor (year % 400 == 0)   
}

Number till_now = input().toNumber()

for Number year in [1..till_now] {
  if is_leap_year(year) {
    print(year.toString() + " is leap year!\n")
  }
}

// same as above, but in a functional way
[1..till_now]
    .filter(is_leap_year)
    .map(|Number year| -> String {year.toString()})
    .forEach(|String y| -> String {print(y + " is leap year!\n")})
