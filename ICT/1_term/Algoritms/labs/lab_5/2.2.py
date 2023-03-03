from collections import deque

def person_is_seller(name):
    return name[0] == 'В'

def search(name):
    search_queue = deque()
    search_queue += graph[name]
    searched = []
    while search_queue:

        person = search_queue.popleft()
        if not person in searched:
            if person_is_seller(person):
                print(person + " продает Волгу.")
                return True
            else:
                searched.append(person)
                search_queue += graph[person]
    else:
        print('Среди друзей никто не продает Волгу.')
        return False

graph = {}
graph ["Я"] = [ "Игорь", "Катя", "Наташа"]
graph["Игорь"] = ["Максим", "Илья"]
graph["Катя"] = ["Илья"]
graph["Наташа"] = ["Глеб", "Сергей"]
graph["Максим"] = []
graph["Илья"] = []
graph["Глеб"] = []
graph["Сергей"] = []
(search("Я"))