f1 = 1
f2 = 1
num = [1, 1]

def fib(n, num, f1, f2):

    i = 0
    while i < n - 2:
        f_s = f1 + f2
        f1 = f2
        f2 = f_s
        i = i + 1
        num.append(f2)  # Добавляем числа в список

    print('Значение этого элемента:', f2)
    print('Все числа:', num)

n = int(input('Введите номер элемента: '))
fib(n, num, f1, f2)