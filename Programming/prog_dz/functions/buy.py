# coding: utf8
import datetime

cart = ['Яблоки', 'Яблоки']
baza = []


def buy(cart):

    alf = 'абвгдежзийклмнопрстуфхцчшщъыьэюabcdefghijklmnopqrstuvwxyАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮABCDEFGHIJKLMNOPQRSTUVWXY ,.:/\|-+=_'

    with open('bank.txt', 'r', encoding='UTF-8') as bank:
        bank = bank.read()

    sm = 0
    bought = ''
    today = ''
    if cart != []:
        with open('products.txt', 'r', encoding='UTF-8') as prod_baza:
            for line in prod_baza:
                baza.append(line.split('\n'))
        for i in range(len(cart)):
            for j in range(len(baza)):
                baza_prod = baza[j][0].split(' ')
                if cart[i] == baza_prod[0]:
                    sm += int(baza_prod[1])
                    bought += ', ' + str(baza_prod[0])
        if sm > int(bank):
            print(f'Ошибка! Недостаточно средств. Данные о покупке не записаны.')
        else:
            otvet = input('Желаете ввести дату вручную (введите Да или Нет)? ')
            if otvet == 'Да' or otvet == 'да':
                otvet = input('Какой год? ')
                if otvet != '':
                    for k in otvet:
                        if k in alf:
                            print('Ошибка! Неправильный ввод.')
                            break
                    else:
                        year = int(otvet)
                    if year > datetime.date.today().year:
                        print('Ошибка! Этот год еще не наступил.')
                    else:
                        otvet = input('Какой месяц (введите число)? ')
                        if otvet != '':
                            for k in otvet:
                                if k in alf:
                                    print('Ошибка! Неправильный ввод.')
                                    break
                            else:
                                month = int(otvet)
                            if month < 0 or month > 12:
                                print('Ошибка! Неправильный ввод месяца.')
                            elif month > datetime.date.today().month and year == datetime.date.today().year:
                                print('Ошибка этот месяц еще не наступил.')
                            else:
                                if month == 0:
                                    month = 12
                                days = [0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
                                otvet = input('Какой день? ')
                                if otvet != '':
                                    for k in otvet:
                                        if k in alf:
                                            print('Ошибка! Неправильный ввод.')
                                            break
                                    else:
                                        day = int(otvet)
                                    if day < 1 or day > days[month]:
                                        print('Ошибка! Неправильный ввод дня.')
                                    elif day > datetime.date.today().day and month == datetime.date.today().month and year == datetime.date.today().year:
                                        print('Ошибка этот день еще не наступил.')
                                    else:
                                        today = datetime.date(year, month, day)
                                        if today != '':
                                            bought = '\n' + bought[2:] + ', ' + str(sm) + ', ' + str(today)
                                            del cart[:]
                                            new_bank = int(bank) - sm
                                            with open('bought.txt', 'a', encoding='UTF-8') as new_bought:
                                                new_bought.write(bought)
                                            with open('bank.txt', 'w', encoding='UTF-8') as bank:
                                                bank.write(str(new_bank))
                                            print('Данные о покупке успешно записаны!')
                                else:
                                    print('Ошибка! Неправильный ввод.')
                        else:
                            print('Ошибка! Неправильный ввод.')
                else:
                    print('Ошибка! Неправильный ввод.')
            elif otvet == 'Нет' or otvet == 'нет':
                today = datetime.date.today()
                if today != '':
                    bought = '\n' + bought[2:] + ', ' + str(sm) + ', ' + str(today)
                    del cart[:]
                    new_bank = int(bank) - sm
                    with open('bought.txt', 'a', encoding='UTF-8') as new_bought:
                        new_bought.write(bought)
                    with open('bank.txt', 'w', encoding='UTF-8') as bank:
                        bank.write(str(new_bank))
                    print('Данные о покупке успешно записаны!')
            else:
                print('Ошибка! Неправильный ввод.')
    else:
        print('Ошибка! Список пуст.')
    print()
    return cart


print(buy(cart))