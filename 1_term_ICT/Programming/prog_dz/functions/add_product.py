# coding: utf8
import datetime
alf = 'абвгдежзийклмнопрстуфхцчшщъыьэюabcdefghijklmnopqrstuvwxyАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮABCDEFGHIJKLMNOPQRSTUVWXY ,.:/\|-+=_'

cart = ['Яблоки', 'Яблоки', 'Молоко']
baza = []
baza_copy = []


def add_product(cart):
    print('---------------------------------------------------\n')
    baza = []
    baza_copy = []
    cnt = 0

    with open('bank.txt', 'r', encoding='UTF-8') as bank:
        bank = bank.read()
    with open('products.txt', 'r', encoding='UTF-8') as prod_baza:
        for line in prod_baza:
            baza.append(line.split('\n'))
    new_product = list(
        input(
            'Введите название продукта(ов) через запятую, который(ые) Вы хотели бы добавить в список (если название составное, то напишите через нижнее подчеркивание): ').split(
            ', '))
    if new_product == [] or new_product == ['']:
        print('Ошибка! Нет данных.')
    else:
        for i in range(len(new_product)):
            new_product[i] = new_product[i].capitalize()
        for i in range(len(new_product)):
            otvet = input(f'\nВведите количество продукта \"{new_product[i]}\": ')
            if otvet != '':
                for k in otvet:
                    if k in alf:
                        print('Ошибка! Неправильный ввод.')
                        break
                else:
                    cnt = int(otvet)

            if cnt <= 0:
                print('Ошибка! Продукт не был добавлен в список.')
            else:
                for j in range(len(baza)):
                    baza_new = baza[j][0].split(' ')
                    if new_product[i] == baza_new[0]:
                        cost = 0
                        with open('products.txt', 'r', encoding='UTF-8') as prod_baza:
                            for line in prod_baza:
                                baza_copy.append(line.split('\n'))
                        for j in range(len(baza_copy)):
                            baza_prod = baza_copy[j][0].split(' ')
                            if new_product[i] == baza_prod[0]:
                                cost += int(baza_prod[1]) * cnt

                        if cost > int(bank):
                            print(
                                f'Ошибка! Недостаточно средств. Продукт \"{new_product[i]}\" не был добавлен в список.')
                            break
                        else:
                            for k in range(cnt):
                                cart.append(new_product[i])
                            print(f'\nПродукт \"{new_product[i]}\" успешно добавлен в список!')
                            print('Текущий список: ' + ', '.join(cart))
                            break
                else:
                    print('\nПродукта нет в базе.')
                    otvet = input('Желаете добавить продукт в базу (введите Да или Нет)? ')
                    if (otvet == 'Да') or (otvet == 'да'):
                        price = input('Введите цену: ')
                        if price != '':
                            for k in price:
                                if k in alf:
                                    print('Ошибка! Неправильный ввод.')
                                    break
                            else:
                                category = input('Введите категорию (если название составное, то напишите через нижнее подчеркивание): ')
                                if category != '':
                                    category = category.capitalize()
                                    prod_for_write = '\n' + new_product[i] + ' ' + str(price) + ' ' + category
                                    for k in range(cnt):
                                        cart.append(new_product[i])
                                    with open('products.txt', 'a', encoding='UTF-8') as new_prod_baza:
                                        new_prod_baza.write(prod_for_write)
                                    print('\nПродукт успешно добавлен в базу и список!')
                                    print('Текущий список: ' + ', '.join(cart))

                                else:
                                    print('Ошибка! Неправильный ввод. Продукт не был добавлен в список и базу.')
                        else:
                            print('Ошибка! Неправильный ввод. Продукт не был добавлен в список и базу.')
                    else:
                        print('Продукт не был добавлен в список.')
    print()

    return cart

print(add_product(cart))