alf = 'абвгдежзийклмнопрстуфхцчшщъыьэюabcdefghijklmnopqrstuvwxyАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮABCDEFGHIJKLMNOPQRSTUVWXY .,:/\|-+=_'

def crypt(text, key):
    cr_text = ''
    for i in text:
         cr_text = cr_text + chr(ord(i) ^ int(key))  # ord преобразовывает символ в номер Unicode, а chr наоборот
    return cr_text

def decrypt(cr_text, key):
    return crypt(cr_text, key)

def txt():
    text = input('Введите текст: ')
    key = input('Введите ключ шифрования: ')
    for k in key:
        if k in alf:
            print('Ошибка! Неправильный ввод.')
            break
    else:
        print('Зашифрованный текст:', crypt(text, int(key)))
        print('Расшифрованный текст:', decrypt(crypt(text, key), int(key)))

def main():

    print('---------------------------------------------------\n'
          'Действие:\n'
          '1 - Зашифровать/расшифровать текст\n'
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
            txt()
        else:
            print('Ошибка! Неправильный ввод.')
            main()
    else:
        print('Ошибка! Неправильный ввод.')
        main()

main()