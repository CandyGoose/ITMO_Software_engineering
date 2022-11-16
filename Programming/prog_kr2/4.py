def check_snils(snils):
    check = []
    sm = 0

    if len(snils) != 14:
        print('Ошибка! Некорректный ввод.')
        return False

    for i in range(len(snils)):
        if snils[i] in '1234567890':
            check.append(int(snils[i]))

    if len(check) != 11:
        print('Ошибка! Некорректный ввод.')
        return False

    for i in range(len(check)-2):
        if str(check[i]) == str(check[i+1]) == str(check[i+2]):
            print('Ошибка! Некорректный ввод.')
            return False

    del check[9:]
    for k in range(1, 10):
        sm += k * check[-k]

    while sm > 101:
        sm %= 101
    if sm == 100 or sm == 101:
        sm = '00'
    elif sm == 0:
        sm = '00'

    print('\nКонтрольная сумма:', sm)
    print('Результат проверки:', str(sm) == (snils[-2:]))

snils = str(input('Введите номер СНИЛС: '))
check_snils(snils)