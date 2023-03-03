'''
Напишите функцию, которая принимает неограниченное количество числовых аргументов и возвращает кортеж из двух
списков: отрицательных значений (отсортирован по убыванию); неотрицательных значений (отсортирован по возрастанию).
'''

spisok = list(map(int, input('Введите числа через пробел: ').split()))
un_spisok = []

def num():

    for i in range(len(spisok)):
        if spisok[i] < 0:
            un_spisok.append(spisok[i])
    for h in un_spisok:
        spisok.remove(h)
    spisok.sort()
    un_spisok.sort(reverse=True)
    return (un_spisok, spisok)

print(num())