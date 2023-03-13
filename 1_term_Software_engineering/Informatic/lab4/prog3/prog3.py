from time import time
from json2xml import json2xml
from json2xml.utils import readfromjson
import re
st_time1 = time()

#---Обязательное---#
def DictToXML(d):
    global xml
    global counter
    global maxcounter
    for k, v in d.items():
        xml += "    " * counter + "<" + k + ">"
        if isinstance(v, list):
            for i in v:
                if xml[-1] != "\n":
                    xml += "\n"
                counter += 1
                maxcounter = counter
                DictToXML(i)
        elif isinstance(v, dict):
            if xml[-1] != "\n":
                xml += "\n"
            counter += 1
            maxcounter = counter
            DictToXML(v)
        else:
            maxcounter = counter
            xml += str(v)
        if counter == maxcounter:
            xml += "</" + k + ">\n"
        else:
            xml += "    " * counter + "</" + k + ">\n"
    counter -= 1
    if counter == 0:
        maxcounter = 0


maxcounter = 0
counter = 0
xml = '<?xml version="1.0" encoding="UTF-8" ?>\n'
with open("sch.json", "r") as f:
    text = f.read()
true = True
false = False
null = None
l = eval(text)
DictToXML(l)
#print(xml)
t1 = (time() - st_time1)*(10)
print("--- Обязательное %s секунд * 10 ---" % (t1))
st_time2 = time()

#-----------№1------------#
data = readfromjson("sch.json")
xml = json2xml.Json2xml(data).to_xml()
# print(xml)
t2 = (time() - st_time2)*(10)
print("--- №1 %s секунд * 10 ---" % (t2))
st_time3 = time()

#-----------№2------------#
def JsonToDict(d):
    for k, v in d.items():
        array = []
        nd = {}
        regex = r"(?<=\")([\w|-]+)\"(?::)(?:(\".+?\")|\{(\".+?\")\})(?=[, ]?)"
        for i in re.finditer(regex, v):
            if i.group(1) in nd.keys():
                array.append(JsonToDictPart2(nd))
                nd = {}
            if i.group(2) is None:
                nd[i.group(1)] = i.group(3)
            else:
                nd[i.group(1)] = i.group(2)
        array.append(JsonToDictPart2(nd))
        d[k]=array
    return d

def JsonToDictPart2(d):
    for k, v in d.items():
        if re.search("\":", v):
            nd = {}
            regex = r"(?<=\")([\w|-]+)\"(?::)(?:(\".+?\")|\{(\".+?\")\})(?=[, ]?)"
            for i in re.finditer(regex, v):
                if i.group(2) is None:
                    ch = str(i.group(3)).replace('"', "")
                    nd[i.group(1)] = ch
                else:
                    ch = str(i.group(2)).replace('"', "")
                    nd[i.group(1)] = ch
            d[k] = nd
        else:
            v = v.replace('"', "")
            d[k] =v
    return d

def DictToXML(d):
    global xml
    global counter
    global maxcounter
    for k, v in d.items():
        xml += "    " * counter + "<" + k + ">"
        if isinstance(v, list):
            for i in v:
                if xml[-1] != "\n":
                    xml += "\n"
                counter += 1
                maxcounter = counter
                DictToXML(i)
        elif isinstance(v, dict):
            if xml[-1] != "\n":
                xml += "\n"
            counter += 1
            maxcounter = counter
            DictToXML(v)
        else:
            maxcounter = counter
            xml += str(v)
        if counter == maxcounter:
            xml += "</" + k + ">\n"
        else:
            xml += "    " * counter + "</" + k + ">\n"
    counter -= 1
    if counter == 0:
        maxcounter = 0

maxcounter = 0
counter = 0
xml = '<?xml version="1.0" encoding="UTF-8" ?>\n'
with open("sch.json", "r") as f:
    text = f.read()
text = text[1:-1]
d = {}
regex = r'(?<=")[А-Яа-яёЁ\w]+(?=":\[)'
km = re.findall(regex, text)
regex = r'(?<=:\[{)[^\]]+(?=})'
vm = re.findall(regex, text)
for i in range(len(km)):
    d[km[i]]=vm[i]
a = JsonToDict(d)
DictToXML(a)
#print(xml)
t3 = (time() - st_time3) * 10
print("--- №2 %s секунд * 10 ---" % t3)


