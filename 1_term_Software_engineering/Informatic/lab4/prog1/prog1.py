from json2xml import json2xml
from json2xml.utils import readfromjson

data = readfromjson("sch.json")
xml = json2xml.Json2xml(data).to_xml()
print(xml)
with open("output.xml", "w", encoding="utf-8") as f:
    f.write(xml)
