# coding: utf8
import datetime

cart = []
baza = []

def view_cart():
    if cart != []:
        print('Текущий список: ' + ', '.join(cart))
    else:
        print('Текущий список пуст.')
view_cart()