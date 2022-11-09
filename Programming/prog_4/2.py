text = input('Напишите что-нибудь: ')
new_t = ''

for i in text:
    if i in 'ABCDEFGHIJKLMNOPQRSTUVWXYZАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ':
        new_t += i.lower()
    else:
        new_t += i.upper()

print('Текст с измененным регистром:', new_t)