def DictToMarkdown(d):
    global md
    global rekdeep
    for k, v in d.items():
        md += ("#" * rekdeep) + " " + k + "\n"
        if isinstance(v, list):
            for i in v:
                md += "\n------\n"
                rekdeep += 1
                DictToMarkdown(i)
                rekdeep -= 1
        elif isinstance(v, dict):
            rekdeep += 1
            DictToMarkdown(v)
            rekdeep -= 1
        else:
            md += v + "\n"


rekdeep = 1
md = ''
with open("sch.json", "r", encoding="utf-8") as f:
    text = f.read()
true = True
false = False
null = None
l = eval(text)
DictToMarkdown(l)
print(md)

with open("markdown.md", "w", encoding="utf-8") as f:
    f.write(md)