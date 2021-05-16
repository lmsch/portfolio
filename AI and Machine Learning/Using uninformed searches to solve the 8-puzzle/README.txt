Name: Luke Schipper
Course: CS4110 - AI and Automated Reasoning
Date: Sept. 27, 2019

This is a simple application for using uninformed searches to solve the 8-puzzle.
Prints out the explored nodes as the search progresses and the solution
upon completion. Supports any nxn grid.

Instructions for use (run py 8-puzzle_searches.py --help for more details):

py 8-puzzle_searches.py bfs 0,1,3,2,4,5,6,8,7 OR py 8-puzzle_searches.py dfs 0,1,3,2,4,5,6,8,7
-> Run breadth-first search (BFS) or depth-first search (DFS) on board
0 1 3
2 4 5
6 8 7

py 8-puzzle_searches.py dls 0,1,3,2,4,5,6,8,7 -l 110
-> Run depth-limited search (DLS) with limit depth 110 (default limit: 100)

For any given search, the goal states can be provided specifically
-> py 8-puzzle_searches.py bfs 0,1,3,2 -g 0,3,2,1 -g 3,2,1,0
Goal states default to the integer sorting of the provided initial board state.
(i.e. given 013245687, goals become 012345678 and 123456780)

To search a 4x4 puzzle, enter the numbers as normal (0-15 and comma-delimited)
