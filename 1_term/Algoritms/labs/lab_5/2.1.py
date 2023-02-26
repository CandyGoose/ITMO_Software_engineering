graph = {
    'Московская' : ['Горьковская','Чкаловская', "Канавинская"],
    'Канавинская' : ['Бурнаковская'],
    'Чкаловская' : ['Ленинская'],
    'Ленинская' : ['Заречная'],
    'Заречная' : ['Кировская'],
    'Парк Культуры' : ['a'],
    'a' : [],
    'Горьковская' : ['b'],
    'b' : [],
    'Кировская' : ['Парк Культуры'],
    'Бурнаковская' : ['Буревестник'],
    'Буревестник' : ['c'],
    'c' : []
}

visited = set()
doroga = []
def dfs(visited, graph, node):
    if node not in visited:
        visited.add(node)
        for neighbour in graph[node]:
            dfs(visited, graph, neighbour)
            if len(neighbour) != 0:
                doroga.append(node)
def vyvod():
    doroga.reverse()
    aa = '-'.join(doroga)
    aaa = aa.split("Московская")
    del aaa[0]
    for i in range(len(aaa)):
        if aaa[i] != aaa[-1]:
            print('Московская'+aaa[i][:-1])
        else:
            print('Московская'+ aaa[i])

dfs(visited, graph, 'Московская')
vyvod()