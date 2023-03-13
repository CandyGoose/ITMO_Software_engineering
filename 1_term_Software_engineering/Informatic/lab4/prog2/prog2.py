import re


def JsonToDict(d):
    for k, v in d.items():
        array = []
        nd = {}
        regex = r"(?<=\")([\w|-]+)\"(?::)(?:(\".+?\")|\{(\".+?\")\})(?=[, ]?)"
        for i in re.finditer(regex, v):
            if i.group(1) in nd.keys():
                array.append(JsonToDictPart2(nd))
                nd = {}
            if i.group(2) == None:
                nd[i.group(1)] = i.group(3)
            else:
                nd[i.group(1)] = i.group(2)
        array.append(JsonToDictPart2(nd))
        d[k] = array
    return d


def JsonToDictPart2(d):
    for k, v in d.items():
        a = re.search("\":", v)
        if (re.search("\":", v)):
            nd = {}
            regex = r"(?<=\")([\w|-]+)\"(?::)(?:(\".+?\")|\{(\".+?\")\})(?=[, ]?)"
            for i in re.finditer(regex, v):
                if i.group(2) == None:
                    ch = str(i.group(3)).replace('"', "")
                    nd[i.group(1)] = ch
                else:
                    ch = str(i.group(2)).replace('"', "")
                    nd[i.group(1)] = ch
            d[k] = nd
        else:
            v = v.replace('"', "")
            d[k] = v
    return d


def DictToXML(d):
    global xml
    global counter
    global maxcounter
    for k, v in d.items():
        xml += "\t" * (counter + 1) + "<" + k + ">"
        if isinstance(v, list):
            for i in v:
                if xml[-1] != "\n":
                    xml += "\n"
                counter += 1
                maxcounter = counter
                DictToXML(i)
                counter -= 1
        elif isinstance(v, dict):
            if xml[-1] != "\n":
                xml += "\n"
            counter += 1
            maxcounter = counter
            DictToXML(v)
            counter -= 1
        else:
            maxcounter = counter
            xml += str(v)
        if (counter == maxcounter):
            xml += "</" + k + ">\n"
        else:
            xml += "\t" * (counter + 1) + "</" + k + ">\n"


maxcounter = 0
counter = 0
xml = '<?xml version="1.0" encoding="UTF-8" ?>\n<main>\n'
with open("sch.json", "r", encoding="utf-8") as f:
    text = f.read()
text = text[1:-1]
d = {}
regex = r'(?<=")[А-Яа-яёЁ\w]+(?=":\[)'
km = re.findall(regex, text)
regex = r'(?<=:\[{)[^\]]+(?=})'
vm = re.findall(regex, text)
for i in range(len(km)):
    d[km[i]] = vm[i]
a = JsonToDict(d)
DictToXML(a)
xml += "</main>"
print(xml)
with open("output.xml", "w", encoding="utf-8") as f:
    f.write(xml)
