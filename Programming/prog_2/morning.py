name = input('Введите ваше имя: ')
time = int(input('Который час? '))
if 0<=time<=4 or time == 24:
    print('Доброй ночи,', name + '!')
elif 4<time<12:
    print('Доброе утро,', name +'!')
elif 12<=time<18:
    print('Добрый день,', name + '!')
elif 18<=time<24:
    print('Добрый вечер,', name + '!')
else:
    print('Неправильный формат, введите корректный час.')