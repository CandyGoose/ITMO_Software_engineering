'''
Создайте сценарий, который использует список имен файлов CSV в качестве источника для копирования файлов в плоский
файл. Текущая дата и время должны быть добавлены к имени файла в качестве префикса перед копированием.
'''

import datetime
import logging

# Список названий файлов
files = ['file_1.csv', 'file_2.csv']

# Текущая дата
d = datetime.datetime.now().strftime('%Y-%m-%d_%H-%M-%S')

logfile = 'logfile_' + d + '.log'

# Название нового файла
copy_file = d + '_' + 'copy_file.csv'

for f in files:
    with open(f, 'r') as fil:
        a = fil.read()
        with open(copy_file, 'a+') as newf:
            newf.write(a)