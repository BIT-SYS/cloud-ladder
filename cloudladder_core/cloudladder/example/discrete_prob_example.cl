let ppush = DiscreteProbability.push;
DiscreteProbability.new()
    | ppush(0.1, "10%")
    | ppush(0.2, "20%")
    | ppush(0.4, "40%")
    | ppush(0.3, "30%")
    | top(2)
    | print
    ;