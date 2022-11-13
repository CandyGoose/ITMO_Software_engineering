# coding: utf8


cart = []
baza = []

def view_bought():
    with open('bought.txt', 'r', encoding='UTF-8') as bought:
        bought = bought.read()
    print(bought)
view_bought()