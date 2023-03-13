import re

with open("test.txt", "r") as f:
    test_str = f.read()
test_str = re.sub(r"[^\w\s]", "",test_str)
test_str = test_str.split()
test_str = " ".join(test_str)
test_str=" "+test_str+" "
regex = r"(?<=\s)([^уеёаоэыяию\s]*([уеёаоэыяию])([^уеёаоэыяию\s]*\2*[^уеёаоэыяию\s]*)*)(?=\s)"
matches = re.findall(regex, test_str, re.MULTILINE | re.IGNORECASE)
arr=[]
for i in matches:
    a,b,c = i
    arr.append(a)
print("\n".join(sorted(arr,key=len)))

