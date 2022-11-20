#!/bin/bash

export LANG=en_US.UTF-8

if [[ $1 != "" ]]
then
    echo $1
    this_dir=$1
else 
    echo "."
    this_dir=$(pwd)
fi
dir=0
file=0

func () 
{
    local root
    root=$1
    local branch
    branch=$2
    local subdirs
    subdirs=($root/*)
    local subdirs_cnt
    subdirs_cnt=${#subdirs[@]}

    for i in ${!subdirs[@]}
    do
        local parent
        parent=$'\u2502\u00A0\u00A0\u0020'
        local child
        child=$'\u251c\u2500\u2500\u0020'
        local name
        name=${subdirs[i]##*/}

        if [[ $i -eq $(( $subdirs_cnt - 1 )) ]]
        then
            parent=$'\u0020\u0020\u0020\u0020'
            child=$'\u2514\u2500\u2500\u0020'
        fi
        echo "$branch$child$name"

        if [[ -d $root/$name ]]
        then 
            dir=$(( $dir + 1 ))
            func $root/$name "$branch$parent"
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

