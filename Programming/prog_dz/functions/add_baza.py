# coding: utf8
import datetime

alf = 'абвгдежзийклмнопрстуфхцчшщъыьэюabcdefghijklmnopqrstuvwxyАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮABCDEFGHIJKLMNOPQRSTUVWXY ,.:/\|-+=_'

baza = []

def add_baza():

    with open('products.txt', 'r', encoding='UTF-8') as prod_baza:
        baza = prod_baza.read()
    new_product = input('Введите название продукта: ')
    new_product = new_product.capitalize()
    if new_product != '':
        if new_product.casefold() in baza.casefold():
            print('Продукт продукт уже находится в базе.')
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
