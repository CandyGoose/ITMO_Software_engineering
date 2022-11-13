# coding: utf8
import datetime
from operator import itemgetter

def view_bought_by():
    alf = 'абвгдежзийклмнопрстуфхцчшщъыьэюabcdefghijklmnopqrstuvwxyАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮABCDEFGHIJKLMNOPQRSTUVWXY ,.:/\|-+=_'

    baza = []
    bought = []
    bought_baza2 = []
    all_baza_prod = []

    with open('bought.txt', 'r', encoding='UTF-8') as bought_baza:
        for line in bought_baza:
            bought.append(line.split('\n'))

    otvet = input('По какому параметру Вы хотите просмотреть покупки (введите цифру 1 (Дата), 2 (Категория) или 3 (Стоимость))? ')
    print()
    if otvet == 'Дата' or otvet == 'дата' or int(otvet) == 1:
        for_sort = []
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
                                print()
                                for k in range(len(bought)):
                                    bought_baza = bought[k][0].split(', ')
                                    bought_baza2.append(bought_baza)
                                print(f'Покупки по дате {today}: ')
                                cnt = 0
                                for i in range(len(bought)):
                                    if str(today) in (bought_baza2[i][-1]):
                                        line = ''
                                        for j in range(len(bought_baza2[i]) - 2):
                                            line += bought_baza2[i][j] + ', '
                                        line += bought_baza2[i][-2] + ' рублей, ' + bought_baza2[i][-1]
                                        print(line)
                                        cnt += 1
                                        for_sort.append(bought_baza2[i])
                                if cnt == 0:
                                    print('Покупок за эту дату нет.')
                                else:
                                    otvet = input('\nЖелаете отсортировать по стоимости (введите Да или Нет)? ')
                                    if otvet == 'Да' or otvet == 'да':
                                        otvet = input(
                                            'Желаете отсортировать по возрастанию или убыванию (введите цифры 1 (по возрастанию) или 2 (по убыванию))? ')
                                        if otvet == 'По возрастанию' or otvet == 'по возрастанию' or int(otvet) == 1:
                                            for_sort.sort(key=lambda i: int(i[-2]))
                                            print('\nОтсортированный список:')
                                            for i in range(len(for_sort)):
                                                line = ''
                                                for n in range(len(for_sort[i]) - 2):
                                                    line += for_sort[i][n] + ', '
                                                line += for_sort[i][-2] + ' рублей, ' + for_sort[i][-1]
                                                print(line)
                                        elif otvet == 'По убыванию' or otvet == 'по убыванию' or int(otvet) == 2:
                                            for_sort.sort(key=lambda i: int(i[-2]), reverse=True)
                                            print('\nОтсортированный список:')
                                            for i in range(len(for_sort)):
                                                line = ''
                                                for n in range(len(for_sort[i]) - 2):
                                                    line += for_sort[i][n] + ', '
                                                line += for_sort[i][-2] + ' рублей, ' + for_sort[i][-1]
                                                print(line)
                                        else:
                                            print('Ошибка! Неправильный ввод.')
                                    else:
                                        print('Записи не были отсортированы.')
                        else:
                            print('Ошибка! Неправильный ввод.')
                else:
                    print('Ошибка! Неправильный ввод.')
        else:
                print('Ошибка! Неправильный ввод.')

    elif otvet == 'Категория' or otvet == 'категория' or int(otvet) == 2:
        for_sort = []
        category = []
        with open('products.txt', 'r', encoding='UTF-8') as prod_baza:
            for line in prod_baza:
                baza.append(line.split('\n'))
        for k in range(len(bought)):
            bought_baza = bought[k][0].split(', ')
            bought_baza2.append(bought_baza)
        for j in range(len(baza)):
            baza_prod = baza[j][0].split(' ')
            all_baza_prod.append(baza_prod)
        for j in range(len(baza)):
            category.append(all_baza_prod[j][2])
        category = set(category)
        print('Доступные категории:', ', '.join(category))
        otvet = input('По какой категории Вы хотите просмотреть покупки (введите одну из доступных категорий)? ')
        if otvet in category:
            print('\nПокупки, содержащие категорию:')

            for i in range(len(bought)):
                cnt = 0
                for j in range(len(bought_baza2[i]) - 2):
                    for k in range(len(all_baza_prod)):

                        if (bought_baza2[i][j]) == all_baza_prod[k][0] and cnt == 0:
                            if otvet == all_baza_prod[k][2]:
                                cnt += 1
                                line = ''
                                for n in range(len(bought_baza2[i]) - 2):
                                    line += bought_baza2[i][n] + ', '
                                line += bought_baza2[i][-2] + ' рублей, ' + bought_baza2[i][-1]
                                print(line)
                                for_sort.append(bought_baza2[i])
            if line == '':
                print('Покупок в этой категории нет.')
            else:
                otvet = input('\nЖелаете отсортировать по стоимости (введите Да или Нет)? ')
                if otvet == 'Да' or otvet == 'да':
                    otvet = input(
                        'Желаете отсортировать по возрастанию или убыванию (введите цифры 1 (по возрастанию) или 2 (по убыванию))? ')
                    if otvet == 'По возрастанию' or otvet == 'по возрастанию' or int(otvet) == 1:
                        for_sort.sort(key=lambda i: int(i[-2]))
                        print('\nОтсортированный список:')
                        for i in range(len(for_sort)):
                            line = ''
                            for n in range(len(for_sort[i]) - 2):
                                line += for_sort[i][n] + ', '
                            line += for_sort[i][-2] + ' рублей, ' + for_sort[i][-1]
                            print(line)
                    elif otvet == 'По убыванию' or otvet == 'по убыванию' or int(otvet) == 2:
                        for_sort.sort(key=lambda i: int(i[-2]), reverse=True)
                        print('\nОтсортированный список:')
                        for i in range(len(for_sort)):
                            line = ''
                            for n in range(len(for_sort[i]) - 2):
                                line += for_sort[i][n] + ', '
                            line += for_sort[i][-2] + ' рублей, ' + for_sort[i][-1]
                            print(line)
                    else:
                        print('Ошибка! Неправильный ввод.')
                else:
                    print('Записи не были отсортированы.')
        else:
            print('Ошибка! Такой категории нет.')


    elif otvet == 'Стоимость' or otvet == 'стоимость' or int(otvet) == 3:
        cost = 0
        for k in range(len(bought)):
            bought_baza = bought[k][0].split(', ')
            bought_baza2.append(bought_baza)
        otvet = input('Введите стоимость в рублях, по которой Вы хотите просмотреть покупки: ')
        if otvet != '':
            for k in otvet:
                if k in alf:
                    print('Ошибка! Неправильный ввод.')
                    break
            else:
                cost = int(otvet)
                print(f'\nПокупки со стоимостью {cost} рублей: ')
                cnt = 0
                for i in range(len(bought)):
                    if str(cost) in (bought_baza2[i][-2]):
                        line = ''
                        for j in range(len(bought_baza2[i])-2):
                            line += bought_baza2[i][j] + ', '
                        line += bought_baza2[i][-2] + ' рублей, ' + bought_baza2[i][-1]
                        print(line)
                        cnt += 1

                if cnt == 0:
                    print('Покупок с такой стоимостью нет.')
    else:
        print('Ошибка! Неправильный ввод.')
view_bought_by()