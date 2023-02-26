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