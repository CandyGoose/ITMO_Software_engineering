spisok = [2, 4, 6, 8, 10, 12, 14, 16, 18, 20]
shag = int(input('Введите шаг: '))
length = len(spisok)

if abs(shag) > length:
    print('Ошибка! Количество шагов больше количества элементов в списке.')
else:
    fin = spisok[-shag:] + spisok[:-shag]
    print(
        f'Изначальный список: {spisok}\n'
        f'Список со сдвигом: {fin}\n')