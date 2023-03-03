import timeit
import numpy

# Обратная матрица (функция)
def inverse(matrix):

    b = "Неправильный ввод или детерминант равен 0"

    def determ(mx):  # определитель матрицы
        diff_diag = mx[0][0] * mx[1][1] * mx[2][2] + mx[0][1] * mx[1][2] * mx[2][0] + mx[1][0] * mx[2][1] * mx[0][2] - mx[2][0] * mx[1][1] * mx[0][2] - mx[1][0] * mx[0][1] * mx[2][2] - mx[2][1] * mx[1][2] * mx[0][0]
        return diff_diag

    def minor(mi):  # нахождение минора
        return mi[0][0] * mi[1][1] - mi[0][1] * mi[1][0]

    rows_len = len(matrix[0])  # количество столбцов и строк исходной матрицы (проверка)
    columns_len = len(matrix)

    mx_det = determ(matrix)

    if rows_len == 3 and columns_len == 3 and mx_det != 0:  # проверка

        result_matrix = [  # итоговая нулевая матрица
            [0, 0, 0],
            [0, 0, 0],
            [0, 0, 0]
        ]

        # определение минора
        for r_num in range(3):
            for i_row in range(3):  # удаляемый элемент
                matrix_copy = [row.copy() for row in matrix]  # создание копии итоговой матрицы
                matrix_copy.pop(r_num)  # удаляем строки с текущим элементом

                for i_del in range(2):
                    matrix_copy[i_del].pop(i_row)  # удаление столбца

                # определение коэффициента четности (-1)^x
                if (r_num + i_row) % 2 == 0:
                    k = 1
                else:
                    k = -1

                result_matrix[r_num][i_row] = minor(matrix_copy) * k / mx_det
                matrixt = [[0 for j in range(3)] for i in range(3)]
                for i in range(3):
                    for j in range(3):
                        matrixt[i][j] = result_matrix[j][i]
        return matrixt
    else:
        return b

def inverse_numpy(matrix):

    b = "Неправильный ввод или детерминант равен 0"

    def determ(mx):  # определитель матрицы
        diff_diag = mx[0][0] * mx[1][1] * mx[2][2] + mx[0][1] * mx[1][2] * mx[2][0] + mx[1][0] * mx[2][1] * mx[0][2] - mx[2][0] * mx[1][1] * mx[0][2] + mx[1][0] * mx[0][1] * mx[2][2] + mx[2][1] * mx[1][2] * mx[0][0]
        return diff_diag

    rows_len = len(matrix[0])  # количество столбцов и строк исходной матрицы
    columns_len = len(matrix)

    matrix_det = determ(matrix) if rows_len == 3 and columns_len == 3 else 0  # детерминант исходной матрицы
    if rows_len == 3 and columns_len == 3 and matrix_det != 0:  # проверка
        matrix_arr = numpy.array(matrix)
        return numpy.linalg.inv(matrix_arr)
    else:
        return b

print("Введите строки матрицы 3x3 (через Enter), указывая все элементы через пробел: ")

matrix = []
for i in range(3):
    val = input()
    matrix.append(list(map(int, val.split())))

matrix_fin = inverse(matrix)
print(matrix_fin)