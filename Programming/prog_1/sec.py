print ("Введите число: ")
chislo = input()
minute = int(chislo) // 60
sec = int(chislo) % 60
hour = minute // 60
minute = minute % 60
days = hour // 24
hour = hour % 24
print(str(days) + ":" + str(hour) + ":" + str(minute) + ":" + str(sec))

