alf = 'абвгдежзийклмнопрстуфхцчшщъыьэюabcdefghijklmnoprstuvwxyАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮABCDEFGHIJKLMNOPRSTUVWXY ,:/\|+=_'
num = '1234567890'
spec = './,!@#$%^&*_-+=|}{[]~`'

def non(psw, cnt):
    if psw != '':
        cnt += 1
    return cnt

def dlina(psw, cnt):
    if len(psw) >= 8:
        cnt += 1
    return cnt

def upper(psw, cnt):
    if psw.lower() != psw:
        cnt += 1
    return cnt

def lower(psw, cnt):
    if psw.upper() != psw:
        cnt += 1
    return cnt

def nm(psw, cnt):
    for i in num:
        if i in psw:
            cnt += 1
            break
    return cnt

def special(psw, cnt):
    for i in spec:
       if i in psw:
           cnt += 1
           break
    return cnt

def pr():
    print('---------------------------------------------------')
    psw = input('Введите пароль: ')
    cnt = 0
    sm = (non(psw, cnt) + dlina(psw, cnt) + upper(psw, cnt) + lower(psw, cnt) + nm(psw, cnt) + special(psw, cnt))
    print('Сложность пароля:', sm)
    main()

def main():

    print('---------------------------------------------------\n'
          'Действие:\n'
          '1 - Посчитать сложность пароля\n'
          'Q - Выход из программы\n')

    ans = input('Введите номер действия: ')
    if ans != '':
        for k in ans:
            if k in alf:
                print('Ошибка! Неправильный ввод.')
                main()
                break

        if ans == 'q' or ans == 'Q':
            print('---------------------------------------------------')
            print('Был произведен выход из программы.')
            exit(0)
        elif int(ans) == 1:
            pr()
        else:
            print('Ошибка! Неправильный ввод.')
            main()
    else:
        print('Ошибка! Неправильный ввод.')
        main()

main()