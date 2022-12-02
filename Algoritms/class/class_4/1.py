def pr(str):
    count = 0
    cnt = 0
    for i in str:
        cnt += 1
        if i == '(':
            count += 1
        elif i == ')':
            count -= 1
        if count < 0:
            text = f'Ошибка в символе под номером: {cnt}'
            return text

    if count == 0:
        text = 'Нет ошибки в вводе.'
    elif count < cnt:
        text = f'Ошибка в символе под номером: {count}'
    elif count == cnt:
        cnt -= count-1
        text = f'Ошибка в символе под номером: {cnt}'
    else:
        cnt -= count+1
        text = f'Ошибка в символе под номером: {cnt}'
    return text
str = input('Введите скобки: ')
print(pr(str))
