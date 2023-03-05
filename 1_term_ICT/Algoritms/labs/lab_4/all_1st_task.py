import sortt
import soort
massiv = [22, 5, 1, 18, 99]
print('Изначальный массив', massiv)
variant = input('Какую сортировку вы хотите? 1 - быстрая, 2 - расческой ')
if variant == '1':
    soort.quick_sort(massiv)
elif variant == '2':
    sortt.comb(massiv)