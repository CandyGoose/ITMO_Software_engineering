# coding: utf8
import datetime

alf = 'абвгдежзийклмнопрстуфхцчшщъыьэюabcdefghijklmnopqrstuvwxyАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮABCDEFGHIJKLMNOPQRSTUVWXY ,.:/\|-+=_'

baza = []

def add_baza():
    baza = []
    with open('products.txt', 'r', encoding='UTF-8') as prod_baza:
        for line in prod_baza:
            baza.append(line.split('\n'))
    new_product = input('Введите название продукта: ')
    new_product = new_product.capitalize()
    if new_product != '':
        for j in range(len(baza)):
            baza_new = baza[j][0].split(' ')
            if new_product.casefold() == baza_new[0].casefold():
                print('Продукт продукт уже находится в базе.')
                break
        else:
            otvet = input('Введите цену: ')
            if otvet != '':
                for k in otvet:
                    if k in alf:
                        print('Ошибка! Неправильный ввод.')
                        break
                else:
                    price = int(otvet)
                    category = input('Введите категорию: ')
                    category = category.capitalize()
                    if category != '':
                        prod_for_write = '\n' + new_product + ' ' + price + ' ' + category
                        with open('products.txt', 'a', encoding='UTF-8') as new_prod_baza:
                            new_prod_baza.write(prod_for_write)
                        print('Продукт успешно добавлен в базу!')
                    else:
                        print('Ошибка! Нет данных.')
            else:
                print('Ошибка! Нет данных.')
    else:
        print('Ошибка! Нет данных.')
add_baza()
