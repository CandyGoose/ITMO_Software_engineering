alf = 'абвгдежзийклмнопрстуфхцчшщъыьэюabcdefghijklmnopqrstuvwxyАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮABCDEFGHIJKLMNOPQRSTUVWXY .,:/\|-+=_'

def crypt(text, key):
    cr_text = ''
    for i in text:
         cr_text = cr_text + chr(ord(i) ^ int(key))  # ord преобразовывает символ в номер, а chr наоборот
    return cr_text

def decrypt(cr_text, key):
    return crypt(cr_text, key)

text = input('Введите текст: ')
key = input('Введите ключ шифрования: ')
for k in key:
    if k in alf:
        print('Ошибка! Неправильный ввод.')
        break
else:
    print('Зашифрованный текст:', crypt(text, int(key)))
    print('Расшифрованный текст:', decrypt(crypt(text, key), int(key)))