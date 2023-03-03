## Лабораторная работа 1

*С чего начинается Linux? С Терминала!!1!*

Итак, вы установили дистрибутив Linux Ubuntu и запустили его. Перед вами рабочий стол, панель задач, иконки и прочие вещи - все как и в других ~~менее удобных~~ операционных системах. Предвижу, что вы уже вдоволь наигрались с новым (или нет?) для вас графическим интерфейсом (GUI - graphical user interface) и готовы к самому интересному - знакомству с Терминалом (CLI - command line interface) и выполнению Лабораторной работы №1.

1. Запустите Терминал (воспользуйтесь сочетанием клавиш Ctrl+Alt+T). Вы увидите окно с единственной строкой (shell prompt), которая имеет вид

```bash
user@computer_name:~$
```

1. Склонируйте репозиторий с Лабораторной работой №1 к себе на компьютер, выполнив команду

```bash
git clone https://github.com/computer-science-itmo-ict/cs-lab-1-<your_github_username>.git
```

где `<your_github_username>` - ваш логин на гитхаб, авторизованный для этого курса.

3. Перейдите в склонированный репозиторий, используя команду `cd` (change directory).

4. Создайте новый файл с именем `script.bash`

```bash
touch script.bash
```

5. Откройте созданный файл `script.bash` для редактирования. Стандартный текстовый редактор в Linux Ubuntu это `gedit`. Выполните в терминале

```bash
gedit script.bash
```

6. Впишите следующий скрипт

```bash
#!/bin/bash

echo "Welcome to ITMO University"
```

7. Сохраните файл. Закройте текстовый редактор `gedit`. Запустите bash-скрипт, выполнив в терминале

```bash
bash script.bash
```

8. Если вы все сделали верно, то в терминале должна отобразиться строка `Welcome to ITMO University`.


А теперь, используя знания полученные из лекций, дополнительных источников и, добавив к этому немного смекалки, решите следующую задачу.

### Задача

Модифицируйте файл `script.bash` так, чтобы при его запуске в терминале в виде

```bash
bash script.bash Vasya Pupkin
```

Вывод был

`Welcome, Vasya Pupkin`

*Hint:* Скрипт должен работать для любых имен, даже если это Benedict Timothy Carlton Cumberbatch.

### Как сдать решение?

Очень просто!

1. Используя терминал, перейдите в директорию с репозиторием Лабораторной работы.

2. Последовательно выполните команды

```bash
git add script.bash
git commit -m "I did the first task!"
git push origin master
```

Эти команды загрузят ваше решение на `github`, где инициируется автоматическая проверка, результаты которой вы сможете увидеть через несколько минут через браузер на сайте `github.com`.

### Дополнительные источники

* О системе котроля версий `git`, рекомендуем прочесть разделы 1, 2.1 и 2.2 из [https://git-scm.com/book/ru/v2](https://git-scm.com/book/ru/v2).

* Специальные типы переменных [https://se.ifmo.ru/~ad/Documentation/ABS_Guide_ru.html#OTHERTYPESV](https://se.ifmo.ru/~ad/Documentation/ABS_Guide_ru.html#OTHERTYPESV)

* Ресурс, где можно найти ответы на (почти) любые вопросы - [stackoverflow.com](stackoverflow.com)

* Хорошая книга по Shell/bash в Linux - "Learn Linux Shell Scripting – Fundamentals of Bash 4.4"  Sebastiaan Tammer
