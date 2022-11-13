# coding: utf8

alf = 'абвгдежзийклмнопрстуфхцчшщъыьэюabcdefghijklmnopqrstuvwxyАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮABCDEFGHIJKLMNOPQRSTUVWXY ,.:/\|-+=_'

def delete_view():
    baza = []
    baza_bought = []
    baza_clone = []

    with open('bought.txt', 'r', encoding='UTF-8') as bought:
        for line in bought:
            baza.append(line.split('\n'))
    for i in range(len(baza)):
        print(f'{i+1})', ' '.join(baza[i]))
        baza_bought.append(baza)

    otvet2 = input('\nКакую запись желаете удалить (введите число)? ')
    if otvet2 != '':
        for k in otvet2:
            if k in alf:
                print('Ошибка! Неправильный ввод.')
                break
        else:
            otvet = int(otvet2)
    if otvet-1 > len(baza) or otvet-1 < 0:
        print('Ошибка. Такой записи нет.')
    else:
        baza_clone.append(baza[otvet-1][0].split(', '))
        sm = int(baza_clone[0][-2])
        baza.pop(otvet-1)

        with open('bought.txt', 'w', encoding='UTF-8') as baz:
            for i in range(len(baza)):
                baz.write(baza[i][0])
                if i != len(baza)-1:
                    baz.write('\n')

        with open('bank.txt', 'r', encoding='UTF-8') as bank:
            bank = bank.read()
        sm += int(bank)
        with open('bank.txt', 'w', encoding='UTF-8') as bank:
            bank.write(str(sm))
        print('Баланс пополнен!')

        print('Запись успешно удалена.')

delete_view()