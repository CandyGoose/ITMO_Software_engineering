n = input('Введите числа через пробел: ')
a = [int(value) for value in n.split(' ')]

for i in range(len(a) - 1):
    for j in range(len(a) - i - 1):
        if a[j] > a[j + 1]:
            a[j], a[j + 1] = a[j + 1], a[j]

print(a)