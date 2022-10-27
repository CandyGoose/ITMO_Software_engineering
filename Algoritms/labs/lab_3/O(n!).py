# 1  вариант
# def fact(n):
#     if n==1:
#         return 1
#     else:
#         return n*fact(n-1)
#
# print(fact(5))
# 2 вариант
from itertools import *
cnt=1
for i in permutations('ТАШУИВНРАШК', 11):
    if ''.join(i) == 'ТУШАВИНКРАШ':
        print(cnt)
        break
    cnt+=1