# coding: utf8
import datetime

cart = []
baza = []

def baza_prod():

    cur_list = ''

    with open('products.txt', 'r', encoding='UTF-8') as prod_baza:
        for line in prod_baza:
            baza.append(line.split('\n'))

    for j in range(len(baza)):
        baza_prod = baza[j][0].split(' ')
        cur_list += ', ' + baza_prod[0]
    cur_list = cur_list[2:]
    print('Продукты, которые уже есть в базе:', cur_list)
baza_prod()