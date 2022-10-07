import math
a = int(input("Введите 1 сторону треугольника: "))
b = int(input("Введите 2 сторону треугольника: "))
c = int(input("Введите 3 сторону треугольника: "))
if (a+b>=c) and (c>=a) and (c>=b):
  p = (a+b+c)/2
  S = p*(p-a)*(p-b)*(p-c)
  math.sqrt(S)
  print ("Площадь треугольника равна " + str(S))
elif (c+b>=a) and (a>=c) and (a>=b):
  p = (a+b+c)/2
  S = p*(p-a)*(p-b)*(p-c)
  math.sqrt(S)
  print ("Площадь треугольника равна " + str(S))
elif (a+c>=b) and (b>=a) and (b>=c):
  p = (a+b+c)/2
  S = p*(p-a)*(p-b)*(p-c)
  math.sqrt(S)
  print ("Площадь треугольника равна " + str(S))
else:
  print ("Введите другие значения")
