def pr(str):

    spisok = []

    for i in range(len(str)):
        if str[i] == '(':
            spisok.append(i + 1)
        else:
            if spisok == []:
                spisok.append(i + 1)
                break
            else:
                spisok.pop()

    if spisok == []:
        print('Ошибки в вводе нет.')
    else:
        print(f'Ошибка в символе под номером: {spisok[0]}')

str = input('Введите скобки: ')
pr(str)
