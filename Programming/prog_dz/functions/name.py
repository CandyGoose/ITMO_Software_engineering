# coding: utf8
import datetime

def ch_name():
    with open('name.txt', 'r', encoding='UTF-8') as name:
        name = name.read()
    new_name = input('Введите новое имя, которое будет появляться при входе: ')
    if new_name != '' and new_name != ' ' and new_name != '.' and new_name != ',':
        if new_name != name:
            with open('name.txt', 'w', encoding='UTF-8') as name:
                name.write(new_name)
            print('Имя было успешно обновлено!')
        else:
            print('Ошибка! Это имя уже используется.')
    else:
        print('Ошибка! Неправильный ввод.')
ch_name()