import itertools

# n = input('Введите числа через пробел: ')
# a = [int(value) for value in n.split(' ')]
# perm_set = itertools.permutations(a)

n = 'ТУШАВИНКРАШ'
perm_set = itertools.permutations(n)

for i in perm_set:
    print(i)