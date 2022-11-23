name = list(input('Введите имена через пробел: ').split())
k = int(input('Введите какой по счету человек выбывает из круга: '))

def voda():
    list = [i for i in range(1, len(name) + 1)]
    num = 0
    while list:
        for j in range(k):
            if num == len(list):
                num = 0
            num += 1
        num -= 1  # Возвращаем нормальный вид для списка
        if len(list) == 1:
            print('Имя последнего человека:', name[num])
        del list[num]  # Удаляем из списка водящего
        del name[num]
voda()