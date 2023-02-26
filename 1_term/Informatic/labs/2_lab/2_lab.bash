#!/bin/bash

x=$1
y=$2

plus=$((x+y))
minus=$((x-y))
multy=$((x*y))

if [[ $y -eq 0 ]]  # проверка деления на 0
then
    div="#"
else
    if [[ $x -eq 0 ]]  # ответ 0
        then div=0.00
    else
        float=$(bc<<<"scale=2;$x/$y")  # деление и установка 2 знаков после запятой 
        if [[ $x/$y -ne 0 ]]  # равна ли целая часть 0
            then div=$float
        else 
            if [[ ${float:0:1} = "-" ]]  # показать 1 символ в строке
                then div="-0"${float:1}  # удалить 1 символ в строке
            else div="0$float"
            fi
        fi
    fi
fi

echo $plus $minus $multy $div