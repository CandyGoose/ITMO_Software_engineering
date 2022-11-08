print('Введите слово: ')
flag = True
v = ''
while flag:
    val = input()
    if val != '':
        v += val[0]
    else:
        flag = False
print(v)