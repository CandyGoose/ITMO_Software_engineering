def partition(nums, low, high):

    pivot = nums[(low + high) // 2]
    i = low - 1
    j = high + 1
    while True:
        i += 1
        while nums[i] < pivot:
            i += 1

        j -= 1
        while nums[j] > pivot:
            j -= 1

        if i >= j:
            return j

        nums[i], nums[j] = nums[j], nums[i]

def quick_sort(nums):

    def _quick_sort(items, low, high):
        if low < high:

            split_index = partition(items, low, high)
            _quick_sort(items, low, split_index)
            _quick_sort(items, split_index + 1, high)

    _quick_sort(nums, 0, len(nums) - 1)
    print('Отсортированный список:', massiv)

def comb(massiv):
    step = int(len(massiv)/1.247)
    swap = 1
    while step > 1 or swap > 0:
        swap = 0
        i = 0
        while i + step < len(massiv):
            if massiv[i] > massiv[i+step]:
                massiv[i], massiv[i+step] = massiv[i+step], massiv[i]
                swap += 1
            i = i + 1
        if step > 1:
            step = int(step / 1.247)
            print('Отсортированный список:', massiv)

massiv = [22, 5, 1, 18, 99]
print('Изначальный список:', massiv)
ans = int(input('Какой сортировкой вы хотите отсортировать список (введите 1 (быстрая сортировка) или 2 (сортировка расчекой): '))
if ans == 1:
    quick_sort(massiv)
elif ans == 2:
    comb(massiv)