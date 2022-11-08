import random
num = random.randint(1, 100)
cnt = 0

while cnt != 10:
    inp = int(input('Введите число: '))
    if inp == num:
        print('Вы угадали число')
        break
    elif inp > num:
        print('Число больше загаданного')
        cnt += 1
    else:
        print('Число меньше загаданного')
        cnt += 1
if cnt == 10:
    print('Вы не угадали число, загаданное число:', num)