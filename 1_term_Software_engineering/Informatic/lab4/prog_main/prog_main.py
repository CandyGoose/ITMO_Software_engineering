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
        if counter == maxcounter:
            xml += "</" + k + ">\n"
        else:
            xml += "\t" * (counter + 1) + "</" + k + ">\n"


maxcounter = 0
counter = 0
xml = '<?xml version="1.0" encoding="UTF-8" ?>\n<main>\n'
with open("sch.json", "r", encoding="utf-8") as f:
    text = f.read()
true = True
false = False
null = None
l = eval(text)
DictToXML(l)
xml += "</main>"
print(xml)
with open("output.xml", "w", encoding="utf-8") as f:
    f.write(xml)
