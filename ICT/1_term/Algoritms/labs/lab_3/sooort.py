n = input('Введите числа через пробел: ')
v = [int(value) for value in n.split(' ')]
v.sort()
print(v)