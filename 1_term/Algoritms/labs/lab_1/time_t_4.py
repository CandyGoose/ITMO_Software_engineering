import timeit

print(timeit.timeit(stmt='inverse([[2,0,0], [0,-3,0], [0,0,1]])', setup='from test_time_t_4 import inverse', number=10000))
print(timeit.timeit(stmt='inverse_numpy([[2,0,0], [0,-3,0], [0,0,1]])', setup='from test_time_t_4 import inverse_numpy', number=10000))