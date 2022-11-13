# coding: utf8
import datetime
alf = 'абвгдежзийклмнопрстуфхцчшщъыьэюabcdefghijklmnopqrstuvwxyАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮABCDEFGHIJKLMNOPQRSTUVWXY ,.:/\|-+=_'

cart = ['Яблоки', 'Яблоки', 'Молоко','Яблоки','Яблоки']


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
                    if otvet == cart[i - 1]:
                        cart.pop(i - 1)
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
                            print(cart)
                            print(n)
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
delete_prod()