# Ввод матриц
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

# Транспонирование матрицы
print()
print('Транспонированная первая матрица:')
for i in range(col1):
    for j in range(row1):
        print(str(matrix[j][i]).ljust(2), end=' ')
    print()

# Умножение матриц
print()
cmatrix = [[0 for j in range(col2)] for i in range(row1)]  # создание 3 матрицы
if col1 == row2:  # проверка на возможность умножения
    for i in range(row1):
        for j in range(col2):
            cmatrix[i][j] = sum(matrix[i][kk] * bmatrix[kk][j] for kk in range(row2))

    print('Умножение матриц:')
    for i in range(row1):
        for j in range(col2):
            print(str(cmatrix[i][j]).ljust(2), end=' ')
        print()
else:
    print('Ошибка! Такие матрицы нельзя перемножить.')

# Ранг матрицы
matrix_copy = matrix.copy()  # удаление нулевых строк
for r_copy in matrix_copy:
    if r_copy == [0] * len(r_copy):
        matrix.remove(r_copy)

for r_num in range(len(matrix)):  # нахождение первого ненулевого элемента
    for i_del in range(len(matrix[r_num])):
        if matrix[r_num][i_del] != 0:
            delitel = matrix[r_num][i_del]  # r_num - счетчик строки, i_del - индекс первого ненулевого элемента
            break
    matrix[r_num] = [matrix[r_num][i] / delitel for i in range(len(matrix[r_num]))]  # делим все элементы строки на первый ненулевой элемент

    for r_next in range(r_num + 1, len(matrix)):  # для следующей строки
        k = matrix[r_next][i_del] / matrix[r_num][i_del]  # k - коэффициент, с помощью которого первый элемент следующей строки будет 0
        matrix[r_next] = [matrix[r_next][i] - k * matrix[r_num][i] for i in range(len(matrix[r_next]))]  # делаем первый элемент 0

rank_matrix = len(matrix)  # определяем ранг с возможными нулевыми строками

for r_fin in matrix:  # убираем нулевые строки
    null = True
    for i in r_fin:
        if i != 0:
            null = False
            break
    if null:
        rank_matrix -= 1
print()
print('Ранг первой матрицы:')
print(rank_matrix)