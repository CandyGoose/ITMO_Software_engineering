baza = ['Маша', 'Федя', 'Никита']
login = input('Введите логин: ')
if login in baza:
    print('Доступ разрешен')
else:
    print('Логина нет в разрешенном списке')
    otv = input('Желаете создать логин? (введите Да или Нет): ')
    if otv == 'Да' or otv == 'да':
        baza.append(login)
        print('Логин успешно добавлен!')
    else:
        print('Доступ запрещен')
print('Разрешенный список:', baza)