#!/bin/bash

export LANG=en_US.UTF-8

dir_c=-1
file_c=0

cool() {
	dir_c=$(($dir_c + 1))
	local directory=$1
	local prefix=$2

	local leaves=($directory/*)
	local local_c=${#leaves[@]}

	for i in "${!leaves[@]}"; do
		local leaf=${leaves[$i]}
		leaf=${leaf##*/}

		local local_prefix="│   "
		local pointer="├── "

		if [ $i -eq $((local_c - 1)) ]; then 
			pointer="└── "
			local_prefix="    "
		fi

		echo "${prefix}${pointer}$leaf"
		[ -d "$directory/$leaf" ] && cool "$directory/$leaf" "${prefix}$local_prefix" || file_c=$((file_c + 1))

	done
}

root="."
[ "$#" -ne 0 ] && root="$1"
echo $root

cool $root ""
echo
[ "${dir_c}" -eq "1" ] && printf "$(($dir_c)) directory, " || printf "$(($dir_c)) directories, "

[ "$file_c" -eq "1" ] && echo "$file_c file" || echo "$file_c files"
