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