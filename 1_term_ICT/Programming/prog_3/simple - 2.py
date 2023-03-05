'''
Напишите программу, позволяющую вывести все простые числа в диапазоне до заданного, введенного пользователем.
'''

num_low = int(input('Введите нижнюю границу диапазона: '))
num_high = int(input('Введите верхнюю границу диапазона: '))

print('Простыми числами в данном диапазоне являются: ')
for number in range(num_low, num_high + 1):
    if number > 1:
        for i in range(2, number):
            if (number % i) == 0:
                break
        else:
            print(number)