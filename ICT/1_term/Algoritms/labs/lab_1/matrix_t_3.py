import numpy

# Ввод матрицы
row1 = int(input('Введите количество строк в матрице 1: '))  # ввод 1 матрицы
col1 = int(input('Введите количество столбцов в матрице 1: '))

matrix = [[0 for j in range(col1)] for i in range(row1)]
for i in range(row1):
    for j in range(col1):
        matrix[i][j] = int(input('Введите число в '+str(i+1)+' строке '+str(j+1)+' столбце: '))

row2 = int(input('Введите количество строк в матрице 2: '))  # ввод 2 матрицы
col2 = int(input('Введите количество столбцов в матрице 2: '))

bmatrix = [[0 for j in range(col2)] for i in range(row2)]
for i in range(row2):
    for j in range(col2):
        bmatrix[i][j] = int(input('Введите число в '+str(i+1)+' строке '+str(j+1)+' столбце: '))

# Транспонирование
print()
arr_matrix = numpy.transpose(matrix)
print('Транспонированная первая матрица:')
for i in range(col1):
    for j in range(row1):
        print(str(arr_matrix[i][j]).ljust(2), end=' ')
    print()

# Умножение
print()
if col1 == row2:  # проверка на возможность умножения
    cmatrix = numpy.matmul(matrix, bmatrix)  # произведение
    print('Результат умножения матриц:')
    for i in range(row1):
        for j in range(col2):
            print(str(cmatrix[i][j]).ljust(2), end=' ')
        print()
else:
    print('Ошибка! Такие матрицы нельзя перемножить')

# Ранг
print()
rank = numpy.linalg.matrix_rank(matrix)
print('Ранг первой матрицы:')
print(rank)