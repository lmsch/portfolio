This is a simple application for using A* searches to solve 2048 puzzles.
Supports various dimensions, goal values, and heuristics.

Instructions for use (run py algorithms.py --help for more details):

py algorithms.py alo -g 1024 -d 5
-> Run A* using adjacent line order heuristic, given a 5 x 5 board, until goal tile 1024 is reached.

Available heuristics: alo, apo, nextmax, maxtileweight, numberoftiles, trivial (see README_2 for more info).

