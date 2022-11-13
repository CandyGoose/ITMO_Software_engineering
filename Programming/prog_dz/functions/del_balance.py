# coding: utf8

def delete_balance():
    sm = 0
    alf = 'абвгдежзийклмнопрстуфхцчшщъыьэюabcdefghijklmnopqrstuvwxyАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮABCDEFGHIJKLMNOPQRSTUVWXY ,.:/\|-+=_'
    with open('bank.txt', 'r', encoding='UTF-8') as bank:
        bank = bank.read()
    print('Текущий баланс:', bank, 'рублей.')

    otvet = input('Введите сумму в рублях, которая будет записана как новая: ')
    if otvet != '':
        for i in otvet:
            if i in alf:
                print('Ошибка при вводе.')
                break
        else:
            sm = int(otvet)
            if sm != int(bank):
                with open('bank.txt', 'w', encoding='UTF-8') as bank:
                    bank.write(str(sm))
                print('Баланс успешно изменен!')
                print('Текущий баланс:', sm, 'рублей.')
            else:
                print('Ошибка! Сумма должна отличаться от предыдущей.')
    else:
        print('Ошибка при вводе.')
delete_balance()