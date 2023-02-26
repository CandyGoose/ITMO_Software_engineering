import timeit

print(timeit.timeit(stmt='quick_sort([22, 5, 1, 18, 99])', setup='from sort import quick_sort', number=10000))
print(timeit.timeit(stmt='comb([22, 5, 1, 18, 99])', setup='from sort import comb', number=10000))