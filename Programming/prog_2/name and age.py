name = input("Введите имя: ")
year = input("Введите возраст: ")
pol = input("Введите пол (ж/м): ")
if (pol == "м"):
  p_name = "Его"
  p_year = "Ему"
else:
  p_name = "Её"
  p_year = "Ей"

if any(i in year for i in ('1','2','3','4')) and not(11<=int(year)<=14):
  if '1' in year:
        print(p_name+" зовут "+name+". "+p_year+" "+year+" год"+".")
  else:
        print(p_name + " зовут " + name + ". " + p_year + " " + str(year) + " года" + ".")
else:
      print(p_name + " зовут " + name + ". " + p_year + " " + str(year) + " лет" + ".")