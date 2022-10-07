month = int(input("Введите номер месяца: "))
day = int(input("Введите день: "))
days=[month]
days=[0,31,28,31,30,31,30,31,31,30,31,30,31]

if (month < 1) or (month > 12) or (day < 1) or (day > 31) or (day > days[month]):
  print('Введите другие значения')
elif (month == 1) and (day == 1):
    print('Сегодня Новый год')
else:
    cnt = month
    ny = 0
    while cnt<=12:
      ny += days[cnt]
      cnt += 1
    ny -= day
    if ny == 1:
        print('До Нового года остался '+str(ny)+' день.')
    else:
        print('До Нового года осталось '+str(ny)+' дней.')
