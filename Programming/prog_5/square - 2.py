import math

alf = 'абвгдежзийклмнопрстуфхцчшщъыьэюabcdefghijklmnoprstuvwxyАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮABCDEFGHIJKLMNOPRSTUVWXY ,:/\|-+=_'

def true(k):
    if k in alf:
        print('Ошибка! Неправильный ввод.')
        main()

def krug():
    print('---------------------------------------------------')
    a = input('Введите радиус круга: ')
    if a != '':
        for k in a:
            true(k)
        else:
            S = float(a) * float(a) * math.pi  # Нахождение площади круга
            print('Площадь круга равна ' + str(S))
            main()
    else:
        print('Ошибка! Неправильный ввод.')
        main()

def pryam():
    print('---------------------------------------------------')
    a = input('Введите первую сторону: ')
    b = input('Введите вторую сторону: ')
    if a != '' and b != '':
        for k in a:
            true(k)
        for k in b:
            true(k)
        else:
            S = float(a) * float(b)  # Нахождение площади прямоугольника
            print('Площадь прямоугольника равна ' + str(S))
            main()
    else:
        print('Ошибка! Неправильный ввод.')
        main()

def tre():
    print('---------------------------------------------------')
    a = input('Введите первую сторону: ')
    b = input('Введите вторую сторону: ')
    c = input('Введите третью сторону: ')
    if a != '' and b != '' and c != '':
        for k in a:
            true(k)
        for k in b:
            true(k)
        for k in c:
            true(k)
        else:  # Нахождение площади треугольника
            if (float(a) + float(b) >= float(c)) and (float(c) >= float(a)) and (float(c) >= float(b)):
                p = (float(a) + float(b) + float(c)) / 2
                S = p * (p - float(a)) * (p - float(b)) * (p - float(c))
                math.sqrt(S)
                print('Площадь треугольника равна ' + str(S))
                main()
            elif (float(c) + float(b) >= float(a)) and (float(a) >= float(c)) and (float(a) >= float(b)):
                p = (float(a) + float(b) + float(c)) / 2
                S = p * (p - float(a)) * (p - float(b)) * (p - float(c))
                math.sqrt(S)
                print('Площадь треугольника равна ' + str(S))
                main()
            elif (float(a) + float(c) >= float(b)) and (float(b) >= float(a)) and (float(b) >= float(c)):
                p = (float(a) + float(b) + float(c)) / 2
                S = p * (p - float(a)) * (p - float(b)) * (p - float(c))
                math.sqrt(S)
                print('Площадь треугольника равна ' + str(S))
                main()
            else:
                print('Ошибка в значениях.')
                main()
    else:
        print('Ошибка! Неправильный ввод.')
        main()

def main():
    print('---------------------------------------------------\n'
          'Действие:\n'
          '1) Площадь круга\n'
          '2) Площадь прямоугольника\n'
          '3) Площадь треугольника\n'
          'Q - Выход из программы\n')

    ans = input('Введите действие: ')
    if ans != '':
        for k in ans:
            if k in alf:
                print('Ошибка! Неправильный ввод.')
                main()
                break

        if ans == 'q' or ans == 'Q':
            print('---------------------------------------------------')
            print('Был произведен выход из программы.')
            exit(0)
        elif int(ans) == 1:
            krug()
        elif int(ans) == 2:
            pryam()
        elif int(ans) == 3:
            tre()
        else:
            print('Ошибка! Неправильный ввод.')
            main()
    else:
        print('Ошибка! Неправильный ввод.')
        main()
main()
