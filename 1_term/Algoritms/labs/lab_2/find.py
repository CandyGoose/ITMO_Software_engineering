import xlrd

workbook = xlrd.open_workbook('keys.xls')
worksheet = workbook.sheet_by_index(0)

all_students = {}
for row in range(1, worksheet.nrows):
    for col in range(worksheet.ncols-1):
        all_students[worksheet.cell_value(row,col)]=worksheet.cell_value(row,col+1)
questions = ['Это девушка? ', 'У вашего студента тёмные волосы? ', 'Этот студент носит очки? ', 'Этот студент родился зимой или весной? ', 'У этого студента есть вторая половинка? ', 'Этот студент играет в CS:GO или Dota 2? ']

nashli = False
a = ''
print('Загадайте студента из группы К3121')
print('Отвечайте на вопросы только Да/Нет')

for i in range(0, len(questions)):
    question = input(questions[i])
    if question == 'Да' or question == 'да':
        a += '1'
    elif question == 'нет' or question == 'Нет':
        a += '0'
    else:
        print('Неверный ввод, начните заново')
        break
    if a in all_students:
        print('Вы загадали студента по имени', all_students[a])
        nashli = True
        break
if nashli == False:
    print('Вашего студента нет в группе К3121')