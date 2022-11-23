temp = [20, 21, 20, 'None', 21, 24, 'None', 24, 25, 22]

def sred():
    un_temp = []
    for i in range(len(temp)):
        if temp[i] == 'None':
            un_temp.append(temp[i])  # Список None
    for h in un_temp:
        temp.remove(h)  # Удаление None
    sr = sum(temp) / len(temp)
    res = ('%.2f' % sr)
    return res

print('Список температур:', temp)
print('Среднее значение температуры:', sred())