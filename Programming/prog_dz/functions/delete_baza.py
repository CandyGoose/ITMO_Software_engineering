# coding: utf8
import datetime

baza = []


def delete_baza():
    cur_list = ''

    with open('products.txt', 'r', encoding='UTF-8') as prod_baza:
        for line in prod_baza:
            baza.append(line.split('\n'))

    for j in range(len(baza)):
        baza_prod = baza[j][0].split(' ')
        cur_list += ', ' + baza_prod[0]
    cur_list = cur_list[2:]

    print('Текущая база: ' + cur_list)

    otvet = input('Какой продукт желаете удалить? ')

    if otvet not in cur_list:
        print('Ошибка. Такой записи нет.')
    else:
        for k in range(len(baza)):
            if otvet in (baza[k-1][0]):
                baza.pop(k-1)
        with open('products.txt', 'w', encoding='UTF-8') as baz:
            for i in range(len(baza)):
                baz.write(baza[i][0])
                if i != len(baza)-1:
                    baz.write("\n")
        print('Продукт успешно удален из базы!')
delete_baza()