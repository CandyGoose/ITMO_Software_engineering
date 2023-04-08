import re
x = input()
regex = re.compile("8<\(")
ans = re.findall(regex,x)
print(len(ans))
print("Мой смайлик: " + "8<(")