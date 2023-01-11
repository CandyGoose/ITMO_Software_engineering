import json

'''
Реализуете указанную функциональность в задании 1 с помощью функций. Количество функций и их наполнение на ваше
усмотрение. В комментариях обоснуйте ваше решение.
'''

spisok_new = []
a = 1.13
b = 0.18

# Каждая функция выполняет свою задачу

def adding(n):
    spisok = [float(value) for value in n.split(' ')]
    return spisok

def multy(spisok):
    for i in range(len(spisok)):
        if spisok[i] < 10:
            c = spisok[i] * a
            spisok_new.append(round(c, 2))
        elif spisok[i] > 10:
            c = spisok[i] * b
            spisok_new.append(round(c, 2))
        else:
            spisok_new.append(spisok[i])
    return spisok_new

def sorting(spisok_new):
    spisok_new.sort()
    return spisok_new

def text(spisok_new):
    with open('result2.txt', 'w') as fw:
        json.dump(spisok_new, fw)

n = input('Введите числа через пробел: ')
spisok = adding(n)
spisok_new = sorting(multy(spisok))
print('Полученный список:', spisok_new)
text(spisok_new)
