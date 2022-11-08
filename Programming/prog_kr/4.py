import json

spisok_new = []
a = 1.135
b = 0.642

n = input('Введите числа через пробел: ')
spisok = [float(value) for value in n.split(' ')]

for i in range(len(spisok)):
    if spisok[i] < 10:
        c = spisok[i] * a
        spisok_new.append(round(c, 2))
    elif spisok[i] > 10:
        c = spisok[i] * b
        spisok_new.append(round(c, 2))
    else:
        spisok_new.append(spisok[i])

spisok_new.sort()
print(spisok_new)
with open('file.txt', 'w') as fw:
    json.dump(spisok_new, fw)