import math

alf = 'абвгдежзийклмнопрстуфхцчшщъыьэюabcdefghijklmnoprstuvwxyАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮABCDEFGHIJKLMNOPRSTUVWXY ,:/\|+=_'

def true(k):
    if k in alf:
        print('Ошибка! Неправильный ввод.')
        main()

def iks():
    print('---------------------------------------------------\n')
    a = input('Введите значение a: ')
    b = input('Введите значение b: ')
    c = input('Введите значение c: ')
    if a != '' and b != '' and c != '':
        for k in a:
            true(k)
        for k in b:
            true(k)
        for k in c:
            true(k)
        else:
            discr = float(b) ** 2 - 4 * float(a) * float(c)
            if discr < 0:
                print('Корней нет.')
                main()
            elif discr == 0:
                x = -float(b) / (2 * float(a))
                print('x1,2 = ' + str(x))
                main()
            else:
                x1 = (-float(b) + math.sqrt(discr)) / (2 * float(a))
                x2 = (-float(b) - math.sqrt(discr)) / (2 * float(a))
                print('x1 = ' + str(x1))
                print('x2 = ' + str(x2))
                main()

def main():

    print('---------------------------------------------------\n'
          'Действие:\n'
          '1 - Посчитать корни\n'
          'Q - Выход из программы\n')

    ans = input('Введите номер действия: ')
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
            iks()
        else:
            print('Ошибка! Неправильный ввод.')
            main()
    else:
        print('Ошибка! Неправильный ввод.')
        main()

main()
