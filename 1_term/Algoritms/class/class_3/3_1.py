print('Введите строки матрицы (через Enter), указывая все элементы через пробел (если вы закончили ввод, нажмите Enter): ')
val = input()
c = 2
matrix = []
c += 2
while val != '':
    matrix.append(list(map(int, val.split())))
    val = input()
    c += 2

print('Транспонированная матрица:')
for i in range(len(matrix[0])):
    c += 2
    for j in range(len(matrix)):
        c += 4
        print(str(matrix[j][i]).ljust(2), end=' ')
    print()
print('Шагов:', c)