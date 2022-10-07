month = int(input("Введите номер месяца: "))
if (month<0) or (month>12):
    print("Введите другое значение")
elif (month<=2) or (month==12):
    print("Зима")
elif (month<=5):
    print("Весна")
elif (month<=8):
    print("Лето")
else:
    print("Осень")