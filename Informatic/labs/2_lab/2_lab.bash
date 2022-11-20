#!/bin/bash

x=$1
y=$2

plus=$((x+y))
minus=$((x-y))
multy=$((x*y))

if [[ $y -eq 0 ]] 
then
    div="#"
else
    if [[ $x -eq 0 ]]
        then div=0.00
    else
        float=$(bc<<<"scale=2;$x/$y")
        if [[ $x/$y -ne 0 ]]
            then div=$float
        else 
            if [[ ${float:0:1} = "-" ]]
                then div="-0"${float:1}
            else div="0$float"
            fi
        fi
    fi
fi

echo $plus $minus $multy $div
