x = int(input())
if x != 1:
    for i in range(2, x // 2 + 1):
        y = 0
        if x % i == 0:
            y = y + 1
    if y <= 0:
        print(str(x) + " является простым")
    a = b = 1
    while a + b <= x:
        a, b = b, a + b
        if x == a + b:
            print(str(x) + " является числом Фибоначчи")
else:
    print("1 является числом Фибоначчи")



