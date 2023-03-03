a = float(input('Введите первое число: '))
b = float(input('Введите второе число: '))
oper = input('Введите желаемую операцию (+, -, *, /): ')
if oper == '+':
    print(a+b)
elif oper == '-':
    print(a-b)
elif oper == '*':
    print(a*b)
elif oper == '/':
    if b != 0:
        print(a/b)
    else:
        print('Нельзя делить на 0')

