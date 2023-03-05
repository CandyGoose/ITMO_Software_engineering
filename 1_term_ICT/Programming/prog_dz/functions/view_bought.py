# coding: utf8


cart = []
baza = []

def view_bought():
    bought = []
    baza_bought2 = []
    with open('bought.txt', 'r', encoding='UTF-8') as bought_baza:
        for line in bought_baza:
            bought.append(line.split('\n'))
    for k in range(len(bought)):
        baza_bought = bought[k][0].split(', ')
        baza_bought2.append(baza_bought)
    for i in range(len(bought)):
        line = ''
        for j in range(len(baza_bought2[i]) - 2):
            line += baza_bought2[i][j] + ', '
        line += baza_bought2[i][-2] + ' рублей, ' + baza_bought2[i][-1]
        print(line)
view_bought()