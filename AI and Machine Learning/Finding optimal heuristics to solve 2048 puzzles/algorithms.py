import heapq
import math

def a_star(start, h, goal, open):
    closed_set = set()
    open_set = set()
    g_value = {}
    f_value = []
    parent = {}

    # initialization
    f_start = h(start)
    g_value[start] = 0
    open_set.add(start)
    heapq.heappush(f_value, (f_start, start))

    while open_set:
        cost, node = heapq.heappop(f_value)

        if goal(node):
            return make_path(node, parent)

        closed_set.add(node)
        open_set.remove(node)

        for n in open(node):
            tentative_g_score = g_value[node] + 1

            # only works if your heuristic is consistent (monotonic)
            if n in closed_set:
                continue

            if n not in open_set or tentative_g_score < g_value[n]:
                parent[n] = node
                g_value[n] = tentative_g_score
                actual_f_value = tentative_g_score + h(n)

                if n in open_set:
                    # O(N) rebuild of the heap (Python heap doesn't have decrease-key operation)
                    for i, (p, x) in f_value:
                        if x == n:
                            f_value[i] = (actual_f_value, n)
                            break
                    heapq.heapify(f_value)
                else:
                    open_set.add(n)
                    heapq.heappush(f_value, (actual_f_value, n))


def make_path(node, parent):
    path = [node]

    while node in parent:
        node = parent[node]
        path.insert(0, node)

    return path


if __name__ == '__main__':
    import time

    start = time.time()

    from game import Game

    def goal(game):
        return game.is_game_finished()

    def open(game):
        if game.is_game_finished():
            return

        for move in "nsew":
            new_game = game.play(move)

            if (new_game != game):
                yield new_game

    # Helper functions START

    def generate_tile_weights(goal_value, fn = None):
        weights = dict()
        tile_value = goal_value
        i = 0
        if fn is None:
            fn = lambda t, i, g: g - t
        while tile_value >= 2:
            weights[tile_value] = fn(tile_value, i, goal_value)
            tile_value //= 2
            i += 1
        return weights

    def find_max_tile_indexes(game):
        max_tiles = list()
        temp = [tile for tile_list in game.board for tile in tile_list]
        max_tile = max(temp)
        for i in range(len(temp)):
            if temp[i] == max_tile:
                max_tiles.append({
                    "row": i // game.dimension,
                    "col": i % game.dimension
                })
        return max_tiles

    def determine_sorted_distance(sorted_list):
        distance = 0
        for i in range(len(sorted_list) - 1):
            if sorted_list[i + 1] <= 0:
                return distance
            distance += math.log(sorted_list[i]//sorted_list[i + 1], 2)
        return distance


    def determine_hamming_distance(list):
        temp = list.copy()
        list.sort(reverse = True)
        distance = 0
        for i in range(len(temp)):
            if temp[i] != list[i]:
                distance += 1
        return distance

    def is_valid(index, game):
        return index >= 0 and index < game.dimension

    # Helper functions END

    # Heuristics START

    def trivial_heuristic(game):
        return 0

    def numberOfTiles_heuristic(game):
        numberOfTiles = 0
        for i in range(len(game.board)):
            for j in range(len(game.board)):
                if game.board[i][j]  > 0:
                    numberOfTiles += 1
        return numberOfTiles

    def maxTileWeight_heuristic(game):
        return tile_weights[max(map(max, game.board))]

    def nextMax_heuristic(game):
        temp = [tile for tile_list in game.board for tile in tile_list]
        temp.sort()
        max_tile = temp.pop()
        next_max_tile = temp.pop()
        if next_max_tile == 0:
            return tile_weights[max_tile] + 1
        return tile_weights[max_tile] + math.log(max_tile//next_max_tile, 2)

    def adjacentPropagationOrder_heuristic(game):
        h = float("inf")
        max_tile_indexes = find_max_tile_indexes(game)
        for index in max_tile_indexes:
            desired_tile = game.board[index["row"]][index["col"]]
            temp_h = tile_weights[desired_tile]
            box_border = {
                "w": index["col"],
                "e": index["col"],
                "s": index["row"],
                "n": index["row"],
            }
            while is_valid(box_border["w"], game) or is_valid(box_border["e"], game) or is_valid(box_border["s"], game) or is_valid(box_border["n"], game):
                desired_tile //= 2
                box_border["w"] -= 1
                box_border["e"] += 1
                box_border["s"] += 1
                box_border["n"] -= 1
                for i in range(box_border["n"], box_border["s"] + 1):
                    if is_valid(i, game) and is_valid(box_border["w"], game):
                        tile = game.board[i][box_border["w"]]
                        if tile < desired_tile and tile > 0:
                            temp_h += math.log(desired_tile//tile, 2)
                for i in range(box_border["n"], box_border["s"] + 1):
                    if is_valid(i, game) and is_valid(box_border["e"], game):
                        tile = game.board[i][box_border["e"]]
                        if tile < desired_tile and tile > 0:
                            temp_h += math.log(desired_tile//tile, 2)
                for j in range(box_border["w"], box_border["e"] + 1):
                    if is_valid(j, game) and is_valid(box_border["s"], game):
                        tile = game.board[box_border["s"]][j]
                        if tile < desired_tile and tile > 0:
                            temp_h += math.log(desired_tile//tile, 2)
                for j in range(box_border["w"], box_border["e"] + 1):
                    if is_valid(j, game) and is_valid(box_border["n"], game):
                        tile = game.board[box_border["n"]][j]
                        if tile < desired_tile and tile > 0:
                            temp_h += math.log(desired_tile//tile, 2)
            if temp_h < h:
                h = temp_h
        return h

    def adjacentLineOrder_heuristic(game):
        h = float("inf")
        max_tile_indexes = find_max_tile_indexes(game)
        for index in max_tile_indexes:
            north_list = [game.board[i][index["col"]] for i in range(index["row"], -1, -1)]
            south_list = [game.board[i][index["col"]] for i in range(index["row"], game.dimension)]
            east_list = [game.board[index["row"]][j] for j in range(index["col"], game.dimension)]
            west_list = [game.board[index["row"]][j] for j in range(index["col"], -1, -1)]
            w_h = determine_hamming_distance(west_list) + determine_sorted_distance(west_list)
            e_h = determine_hamming_distance(east_list) + determine_sorted_distance(east_list)
            s_h = determine_hamming_distance(south_list) + determine_sorted_distance(south_list)
            n_h = determine_hamming_distance(north_list) + determine_sorted_distance(north_list)
            temp_h = w_h + e_h + s_h + n_h + tile_weights[game.board[index["row"]][index["col"]]]
            if temp_h < h:
                h = temp_h
        return h

    # Heuristics END
    
    import argparse
    
    heuristics_map = {
        "alo": adjacentLineOrder_heuristic,
        "apo": adjacentPropagationOrder_heuristic,
        "nextmax": nextMax_heuristic,
        "maxtileweight": maxTileWeight_heuristic,
        "numberoftiles":  numberOfTiles_heuristic,
        "trivial": trivial_heuristic
    }
    
    parser = argparse.ArgumentParser(description="Experiment with A* using various parameters and heuristics.")
    parser.add_argument("heuristic", type=str, help="heuristic used in A*.")
    parser.add_argument("-g", "--goal",type=int, default=2048, help="override final tile value.")
    parser.add_argument("-d", "--dimension", type=int, default=4, help="override the dimensions of the 2048 board.")
    args = parser.parse_args()

    tile_weights = generate_tile_weights(args.goal, lambda t, i, g: 2**((g - t)//2) - 1)

    game = Game(args.dimension, args.goal)
    path = a_star(game, heuristics_map[args.heuristic.lower()], goal, open)

   # print("Path ------------------------------")
   # print(path)
    print("Path Length -----------------------")
    print(len(path))
    print("Execution time --------------------")
    print(time.time() - start)
