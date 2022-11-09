spisok = list(map(int, input('Введите числа через пробел: ').split()))
shag = int(input('Введите шаг: '))
length = len(spisok)

if abs(shag) > length:
    print('Ошибка! Количество шагов больше количества элементов в списке.')
else:
    fin = spisok[-shag:] + spisok[:-shag]
    print(
        f'Введенный список: {spisok}\n'
        f'Список со сдвигом: {fin}\n')