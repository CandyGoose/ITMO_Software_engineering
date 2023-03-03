# coding: utf8

def balance():
    with open('bank.txt', 'r', encoding='UTF-8') as bank:
        bank = bank.read()
    print('Текущий баланс:', bank, 'рублей.')
balance()