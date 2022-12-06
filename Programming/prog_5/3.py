def razresh(name):

    a = name.rfind('.')  # Находим индекс последней точки в имени
    if (a < 1) or (a == len(name)-1) or (a != name.find('.')): # Проверка на первое, последнее место и несколько точек
        raise ValueError
    else:
        print('Разрешение у файла:', name[a:])

try:
    name = input('Введите имя файла: ')
    razresh(name)

except ValueError:
    print('У файла неправильное разрешение.')