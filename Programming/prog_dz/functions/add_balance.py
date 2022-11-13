# coding: utf8

def add_balance():
    sm = 0
    alf = 'абвгдежзийклмнопрстуфхцчшщъыьэюabcdefghijklmnopqrstuvwxyАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮABCDEFGHIJKLMNOPQRSTUVWXY ,.:/\|-+=_'
    otvet = input('Введите сумму в рублях, на которую хотите пополнить баланс: ')
    if otvet != '':
        for i in otvet:
            if i in alf:
                break
        else:
            sm = int(otvet)
    if sm > 0:
        with open('bank.txt', 'r', encoding='UTF-8') as bank:
            bank = bank.read()
        sm += int(bank)
        with open('bank.txt', 'w', encoding='UTF-8') as bank:
            bank.write(str(sm))
        print('Баланс успешно пополнен!')
        print('Текущий баланс:', sm, 'рублей.')
    else:
        print('Ошибка при вводе.')
add_balance()