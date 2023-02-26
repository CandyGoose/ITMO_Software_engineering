# coding: utf8
import datetime

'''
Необходимо реализовать следующие функции, позволяющие:
	* Добавлять продукт в коллекцию (тип коллекции на ваш выбор).
	* Просматривать все записанное в программу.
	* Просматривать покупки по дате и категории.
	* Распределять их по стоимости от минимальной к максимальной или наоборот.
	* Удалять требуемые записи и выходить из программы.s
'''

alf = 'абвгдежзийклмнопрстуфхцчшщъыьэюabcdefghijklmnopqrstuvwxyАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮABCDEFGHIJKLMNOPQRSTUVWXY ,.:/\|-+=_'
cart = []
baza = []


def balance():
    print('---------------------------------------------------\n')
    with open('bank.txt', 'r', encoding='UTF-8') as bank:
        bank = bank.read()
    print('Текущий баланс:', bank, 'рублей.')
    print()
    main()


def add_balance():
    print('---------------------------------------------------\n')
    sm = 0
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
    print()
    main()


def delete_balance():
    print('---------------------------------------------------\n')
    with open('bank.txt', 'r', encoding='UTF-8') as bank:
        bank = bank.read()
    print('Текущий баланс:', bank, 'рублей.')

    otvet = input('Введите сумму в рублях, которая будет записана как новая: ')
    if otvet != '':
        for i in otvet:
            if i in alf:
                print('Ошибка при вводе.')
                break
        else:
            sm = int(otvet)
            if sm != int(bank):
                with open('bank.txt', 'w', encoding='UTF-8') as bank:
                    bank.write(str(sm))
                print('\nБаланс успешно изменен!')
                print('Текущий баланс:', sm, 'рублей.')
            else:
                print('Ошибка! Сумма должна отличаться от предыдущей.')
    else:
        print('Ошибка при вводе.')
    print()
    main()


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
    main()


def delete_prod():
    print('---------------------------------------------------\n')
    ans = 0
    un_cart = []
    if cart != []:
        print('Текущий список: ' + ', '.join(cart))

        otvet = input('Какой продукт желаете удалить? ')
        otvet = otvet.capitalize()
        if otvet not in cart:
            print('Ошибка. Такой записи нет.')
        else:
            cnt = 0
            for j in range(len(cart)):
                if otvet == cart[j-1]:
                    cnt += 1
            if cnt == 1:
                for i in range(len(cart)):
                    if otvet == cart[i-1]:
                        cart.pop(i-1)
                        print('Продукт успешно удален из списка!')
                        print('Текущий список: ' + ', '.join(cart))
            else:
                otvet2 = input(f'Количество продуктов у Вас в списке: {cnt}. Сколько желаете удалить? ')
                if otvet2 != '':
                    for k in otvet2:
                        if k in alf:
                            break
                    else:
                        ans = int(otvet2)
                    if cnt < ans or ans < 1:
                        print('Ошибка! Неправильный ввод.')
                    else:
                        for n in range(len(cart)):
                            if otvet == cart[n]:
                                un_cart.append(cart[n])
                                ans -= 1
                            if ans == 0:
                                break
                        for h in un_cart:
                            cart.remove(h)
                        print('Продукт успешно удален из списка!')
                        print('Текущий список: ' + ', '.join(cart))
                else:
                    print('Ошибка! Неправильный ввод.')
    else:
        print('Ошибка! Продуктов в списке нет.')
    print()
    main()


def view_cart():
    print('---------------------------------------------------\n')
    if cart != []:
        print('Текущий список: ' + ', '.join(cart))
    else:
        print('Текущий список пуст.')
    print()
    main()


def add_baza():
    print('---------------------------------------------------\n')
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
                    category = input(
                        'Введите категорию (если название составное, то напишите через нижнее подчеркивание): ')
                    category = category.capitalize()
                    if category != '':
                        prod_for_write = '\n' + new_product + ' ' + str(price) + ' ' + category
                        with open('products.txt', 'a', encoding='UTF-8') as new_prod_baza:
                            new_prod_baza.write(prod_for_write)
                        print('Продукт успешно добавлен в базу!')
                    else:
                        print('Ошибка! Нет данных.')
            else:
                print('Ошибка! Нет данных.')
    else:
        print('Ошибка! Нет данных.')
    print()
    main()


def delete_baza():
    baza = []
    print('---------------------------------------------------\n')
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
    otvet = otvet.capitalize()
    if otvet != '':
        for i in range(len(baza)):
            baza_prod = baza[i][0].split(' ')
            if otvet == baza_prod[0]:
                for k in range(len(baza)):
                    if otvet in (baza[k-1][0]):
                        baza.pop(k-1)
                with open('products.txt', 'w', encoding='UTF-8') as baz:
                    for i in range(len(baza)):
                        baz.write(baza[i][0])
                        if i != len(baza) - 1:
                            baz.write('\n')
                print('Продукт успешно удален из базы!')
                break
        else:
            print('Ошибка. Такой записи нет.')
    else:
        print('Ошибка. Неправильный ввод.')
    print()
    main()


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
    main()


def buy(cart):
    print('---------------------------------------------------\n')

    with open('bank.txt', 'r', encoding='UTF-8') as bank:
        bank = bank.read()
    baza = []
    sm = 0
    bought = ''

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
        print(f'Общая стоимость покупки в рублях: {sm}')
        if sm > int(bank):
            print(f'Ошибка! Недостаточно средств. Данные о покупке не записаны.')
        else:
            otvet = input('Желаете ввести дату вручную (введите Да или Нет (запишет сегодняшнее число))? ')
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
    main()


def view_bought():
    print('---------------------------------------------------\n')
    bought = []
    baza_bought2 = []
    with open('bought.txt', 'r', encoding='UTF-8') as bought_baza:
        for line in bought_baza:
            bought.append(line.split('\n'))
    for k in range(len(bought)):
        baza_bought = bought[k][0].split(', ')
        baza_bought2.append(baza_bought)
    for i in range(len(bought)):
        line = ''
        for j in range(len(baza_bought2[i]) - 2):
            line += baza_bought2[i][j] + ', '
        line += baza_bought2[i][-2] + ' рублей, ' + baza_bought2[i][-1]
        print(line)
    print()
    main()


def view_bought_by():
    print('---------------------------------------------------\n')

    baza = []
    bought = []
    bought_baza2 = []
    all_baza_prod = []

    with open('bought.txt', 'r', encoding='UTF-8') as bought_baza:
        for line in bought_baza:
            bought.append(line.split('\n'))

    otvet = input(
        'По какому параметру Вы хотите просмотреть покупки (введите цифру 1 (Дата), 2 (Категория) или 3 (Стоимость))? ')
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
                        for j in range(len(bought_baza2[i]) - 2):
                            line += bought_baza2[i][j] + ', '
                        line += bought_baza2[i][-2] + ' рублей, ' + bought_baza2[i][-1]
                        print(line)
                        cnt += 1

                if cnt == 0:
                    print('Покупок с такой стоимостью нет.')
    else:
        print('Ошибка! Неправильный ввод.')
    print()
    main()


def delete_view():
    print('---------------------------------------------------\n')
    baza = []
    baza_bought = []
    baza_clone = []

    with open('bought.txt', 'r', encoding='UTF-8') as bought:
        for line in bought:
            baza.append(line.split('\n'))
    for i in range(len(baza)):
        print(f'{i+1})', ' '.join(baza[i]))
        baza_bought.append(baza)

    otvet2 = input('\nКакую запись желаете удалить (введите число)? ')
    if otvet2 != '':
        for k in otvet2:
            if k in alf:
                print('Ошибка! Неправильный ввод.')
                break
        else:
            otvet = int(otvet2)
    if otvet > len(baza) or otvet < 1:
        print('Ошибка. Такой записи нет.')
    else:
        baza_clone.append(baza[otvet-1][0].split(', '))
        sm = int(baza_clone[0][-2])
        baza.pop(otvet-1)

        with open('bought.txt', 'w', encoding='UTF-8') as baz:
            for i in range(len(baza)):
                baz.write(baza[i][0])
                if i != len(baza)-1:
                    baz.write('\n')

        with open('bank.txt', 'r', encoding='UTF-8') as bank:
            bank = bank.read()
        sm += int(bank)
        with open('bank.txt', 'w', encoding='UTF-8') as bank:
            bank.write(str(sm))
        print('Баланс пополнен!')

        print('Запись успешно удалена.')
    print()
    main()


def ch_name():
    print('---------------------------------------------------\n')
    with open('name.txt', 'r', encoding='UTF-8') as name:
        name = name.read()
    new_name = input('Введите новое имя, которое будет появляться при входе: ')
    if new_name != '.' and new_name != ',' and new_name != ' ':
        if new_name != name:
            with open('name.txt', 'w', encoding='UTF-8') as name:
                name.write(new_name)
            print('Имя было успешно обновлено!')
        else:
            print('Ошибка! Это имя уже используется.')
    else:
        print('Ошибка! Неправильный ввод.')
    main()


# ---------------------------------------------------


def out():
    print('---------------------------------------------------\nДо свидания!')
    exit(0)


def bal():
    print('---------------------------------------------------\n'
          'Действия с балансом:\n'
          '1) Посмотреть баланс\n'
          '2) Пополнить баланс\n'
          '3) Изменить баланс\n'
          '4) Назад\n'
          '5) Выход из программы\n')

    ans = input('Введите номер действия: ')
    if ans != '':
        for k in ans:
            if k in alf:
                print('Ошибка! Неправильный ввод.')
                main()
                break

        if int(ans) == 1:
            balance()
        elif int(ans) == 2:
            add_balance()
        elif int(ans) == 3:
            delete_balance()
        elif int(ans) == 4:
            dop_func()
        elif int(ans) == 5:
            out()
        else:
            print('Ошибка! Неправильный ввод.')
            main()
    else:
        print('Ошибка! Неправильный ввод.')
        main()


def prod():
    print('---------------------------------------------------\n'
          'Действия со списком продуктов:\n'
          '1) Посмотреть текущий список продуктов\n'
          '2) Добавить продукт(ы) в список покупок\n'
          '3) Удалить продукт(ы) из списка покупок\n'
          '4) Сделать новую запись о покупке\n'
          '5) Назад\n'
          '6) Выход из программы\n')

    ans = input('Введите номер действия: ')
    if ans != '':
        for k in ans:
            if k in alf:
                print('Ошибка! Неправильный ввод.')
                main()
                break

        if int(ans) == 1:
            view_cart()
        elif int(ans) == 2:
            add_product(cart)
        elif int(ans) == 3:
            delete_prod()
        elif int(ans) == 4:
            buy(cart)
        elif int(ans) == 5:
            dop_func()
        elif int(ans) == 6:
            out()
        else:
            print('Ошибка! Неправильный ввод.')
            main()
    else:
        print('Ошибка! Неправильный ввод.')
        main()


def base():
    print('---------------------------------------------------\n'
          'Действия с базой продуктов:\n'
          '1) Посмотреть базу продуктов\n'
          '2) Добавить продукт в базу\n'
          '3) Удалить продукт из базы\n'
          '4) Назад\n'
          '5) Выход из программы\n')

    ans = input('Введите номер действия: ')
    if ans != '':
        for k in ans:
            if k in alf:
                print('Ошибка! Неправильный ввод.')
                main()
                break

        if int(ans) == 1:
            baza_prod()
        elif int(ans) == 2:
            add_baza()
        elif int(ans) == 3:
            delete_baza()
        elif int(ans) == 4:
            dop_func()
        elif int(ans) == 5:
            out()
        else:
            print('Ошибка! Неправильный ввод.')
            main()
    else:
        print('Ошибка! Неправильный ввод.')
        main()


def view():
    print('---------------------------------------------------\n'
          'Действия с историей покупок:\n'
          '1) Посмотреть всю историю покупок\n'
          '2) Посмотреть историю покупок с сортировкой\n'
          '3) Удалить запись из истории покупок\n'
          '4) Назад\n'
          '5) Выход из программы\n')

    ans = input('Введите номер действия: ')
    if ans != '':
        for k in ans:
            if k in alf:
                print('Ошибка! Неправильный ввод.')
                main()
                break

        if int(ans) == 1:
            view_bought()
        elif int(ans) == 2:
            view_bought_by()
        elif int(ans) == 3:
            delete_view()
        elif int(ans) == 4:
            dop_func()
        elif int(ans) == 5:
            out()
        else:
            print('Ошибка! Неправильный ввод.')
            main()
    else:
        print('Ошибка! Неправильный ввод.')
        main()


def dop_func():
    print('---------------------------------------------------\n'
          'Другие функции:\n'
          '1) Действия с балансом\n'
          '2) Действия со списком продуктов\n'
          '3) Действия с базой продуктов\n'
          '4) Действия с историей покупок\n'
          '5) Изменить имя пользователя в приветствии\n'
          '6) Назад\n'
          '7) Выход из программы\n')

    ans = input('Введите номер действия: ')
    if ans != '':
        for k in ans:
            if k in alf:
                print('Ошибка! Неправильный ввод.')
                main()
                break
        if int(ans) == 1:
            bal()
        elif int(ans) == 2:
            prod()
        elif int(ans) == 3:
            base()
        elif int(ans) == 4:
            view()
        elif int(ans) == 5:
            ch_name()
        elif int(ans) == 6:
            main()
        elif int(ans) == 7:
            out()
        else:
            print('Ошибка! Неправильный ввод.')
            main()
    else:
        print('Ошибка! Неправильный ввод.')
        main()


def main():
    print('---------------------------------------------------\n'
          'Быстрый доступ:\n'
          '1) Добавить продукт(ы) в список покупок\n'
          '2) Сделать новую запись о покупке\n'
          '3) Просмотреть записи о покупках\n'
          '4) Просмотреть текущий баланс\n'
          '5) Другие функции\n'
          '6) Выход из программы\n')
    ans = input('Введите номер действия: ')
    if ans != '':
        for k in ans:
            if k in alf:
                print('Ошибка! Неправильный ввод.')
                main()
                break
        if int(ans) == 1:
            add_product(cart)
        elif int(ans) == 2:
            buy(cart)
        elif int(ans) == 3:
            view_bought()
        elif int(ans) == 4:
            balance()
        elif int(ans) == 5:
            dop_func()
        elif int(ans) == 6:
            out()
        else:
            print('Ошибка! Неправильный ввод.')
            main()
    else:
        print('Ошибка! Неправильный ввод.')
        main()


def begin():
    with open('name.txt', 'r', encoding='UTF-8') as name:
        name = name.read()
    if name != '':
        print(f'\nС возвращением, {name}!')
        main()
    else:
        print(f'\nС возвращением!')
        main()


# ---------------------------------------------------

begin()