import itertools
n = input('Введите числа через пробел: ')
a = [int(value) for value in n.split(' ')]
perm_set = itertools.permutations(a)
for i in perm_set:
    print(i)