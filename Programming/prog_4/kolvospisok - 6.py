spisok = []
sm = 0
pr = 1
a = input('Введите число (если хотите закончить, введите Q): ')
while a not in ('q', 'Q', 'Й', 'й', ''):
    spisok.append(float(a))
    a = input('Введите число (если хотите закончить, введите Q): ')

length = len(spisok)
if length != 0:
    for i in range(length):
        sm += spisok[i]
    sr = sm / length

    minimum = min(spisok)
    ind_min = spisok.index(minimum)

    maximum = max(spisok)
    ind_max = spisok.index(maximum)

    if ind_min < ind_max:
        if ind_min == ind_max - 1:
            pr = 'нет чисел между значениями'
        else:
            for k in range(ind_min + 1, ind_max):
                pr *= spisok[k]
    elif ind_min > ind_max:
        if ind_min == ind_max + 1:
            pr = 'нет чисел между значениями'
        else:
            for k in range(ind_max + 1, ind_min):
                pr *= spisok[k]
    else:
        pr = 'нет чисел между значениями'

else:
    sr = 0
    minimum = 'нет минимального'
    maximum = 'нет максимального'
    ind_min = 'нет индекса'
    ind_max = 'нет индекса'
    pr = 'нет чисел между значениями'

print(
    f'Введенный список: {spisok}\n'
    f'Элементов в списке: {length}\n'
    f'Среднее арифметическое всех значений: {sr}\n'
    f'Сумма всех значений: {sm}\n'
    f'Минимальное значение: {minimum}, его индекс: {ind_min}\n'
    f'Максимальное значение: {maximum}, его индекс: {ind_max}\n'
    f'Произведение всех чисел между минимальным и максимальным значениями: {pr}')