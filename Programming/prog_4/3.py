chisla = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9']
sm = 0

text = input('Напишите что-нибудь: ')
for i in text:
    if i in chisla:
        sm += int(i)
print('Сумма цифр в строке:', sm)