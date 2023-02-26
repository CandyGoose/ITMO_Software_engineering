# coding: utf8
import datetime

cart = []


def baza_prod():
    baza = []
    prod_baza2 = []
    print('---------------------------------------------------\n')

    with open('products.txt', 'r', encoding='UTF-8') as prod_baza:
        for line in prod_baza:
            baza.append(line.split('\n'))
    for k in range(len(baza)):
        prod_baza = baza[k][0].split(' ')
        prod_baza2.append(prod_baza)
    print('Продукты, которые уже есть в базе:\n')
    for i in range(len(baza)):
        line = ''
        for j in range(len(prod_baza2[i]) - 2):
            line += prod_baza2[i][j] + ', '
        line += prod_baza2[i][-2] + ' рублей, категория - ' + prod_baza2[i][-1]
        print(line)
    print()

baza_prod()