#!/bin/bash
# вызывает командный интерпретатор
export LANG=en_US.UTF-8

if [[ $1 != "" ]]  # проверка на пустой ввод
then
    echo $1
    this_dir=$1
else 
    echo "."
    this_dir=$(pwd)  # отобразить файлы текущей директории
fi
dir=0
file=0

func ()  # Функция должна быть объявлена до того как она будет вызвана.
{
    local root
    root=$1  # проверяемая директория
    local branch
    branch=$2  # отвечает за отступ, печатает родителей
    local subdirs
    subdirs=($root/*)  # Создание массива со всеми поддиректориями текущей директории
    local subdirs_cnt
    subdirs_cnt=${#subdirs[@]}  # Количество поддиректорий и количество файлов в директории

    for i in ${!subdirs[@]}  # Получение индексов массива, сколько строк
    do
        local parent
        parent=$'\u2502\u00A0\u00A0\u0020' # │
        local child
        child=$'\u251c\u2500\u2500\u0020' # ├──
        local name
        name=${subdirs[i]##*/}  # Поиск подстроки ведется сначала строки, для вывода последней части

        if [[ $i -eq $(( $subdirs_cnt - 1 )) ]]  # это последний элемент?
        then
            parent=$'\u0020\u0020\u0020\u0020' # пробел
            child=$'\u2514\u2500\u2500\u0020'  # └──
        fi
        echo "$branch$child$name"  # отступ, ├── или └── и имя

        if [[ -d $root/$name ]]  # текущее имя - директория?
        then 
            dir=$(( $dir + 1 ))
            func $root/$name "$branch$parent"  # функция для директории в этой директории
        else
            file=$(( $file + 1 ))
        fi
    done
}

func $this_dir

if [[ $dir -eq 1 ]]
then
    dr="directory,"
else
    dr="directories,"
fi

if [[ $file -eq 1 ]]
then
    fl="file"
else
    fl="files"
fi

echo
echo $dir $dr $file $fl
