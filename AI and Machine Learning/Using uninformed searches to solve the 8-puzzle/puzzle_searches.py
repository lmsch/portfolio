import argparse
import copy

from puzzle import Puzzle
from puzzle_node import PuzzleNode

'''Returns valid actions as lambdas given a state.'''
def actions(state):
    actions_list = []
    if state.can_down():
        actions_list.append(lambda state: state.down())
    if state.can_left():
        actions_list.append(lambda state: state.left())
    if state.can_right():
        actions_list.append(lambda state: state.right())
    if state.can_up():
        actions_list.append(lambda state: state.up())
    return actions_list

'''Executes a breadth-first search given an initial order and a list of goal orders.'''
def bfs(parsed_order, goal_orders):
    node = PuzzleNode(Puzzle(parsed_order), None, None)
    if goal_test(node.get_state().get_order(), goal_orders):
        print(node.get_state(), end="\n\n")
        print("Solution:", solution(node))
        return
    frontier = [node]
    explored = set()
    while len(frontier) > 0:
        node = frontier.pop(0)
        print(node.get_state(), end="\n\n")
        explored.add(node.get_state().get_order())
        for action in actions(node.get_state()):
            child_node = result(node.get_state(), action, node)
            child_order = child_node.get_state().get_order()
            if not child_order in explored:
                if goal_test(child_order, goal_orders):
                    print(child_node.get_state(), end="\n\n")
                    print(solution(child_node))
                    return
                frontier.append(child_node)
    print("No solution found.")

'''Executes a depth-first search given an initial order and a list of goal orders.'''
def dfs(parsed_order, goal_orders):
    node = PuzzleNode(Puzzle(parsed_order), None, None)
    if goal_test(node.get_state().get_order(), goal_orders):
        print(node.get_state(), end="\n\n")
        print("Solution:", solution(node))
        return
    frontier = [node]
    explored = set()
    while len(frontier) > 0:
        node = frontier.pop()
        print(node.get_state(), end="\n\n")
        explored.add(node.get_state().get_order())
        for action in actions(node.get_state()):
            child_node = result(node.get_state(), action, node)
            child_order = child_node.get_state().get_order()
            if not child_order in explored:
                if goal_test(child_order, goal_orders):
                    print(child_node.get_state(), end="\n\n")
                    print(solution(child_node))
                    return
                frontier.append(child_node)
    print("No solution found.")

'''Executes a depth-limited search given an initial order and a list of goal orders.'''
def dls(parsed_order, limit, goal_orders):
    node = PuzzleNode(Puzzle(parsed_order), None, None, 0)
    if goal_test(node.get_state().get_order(), goal_orders):
        print(node.get_state(), end="\n\n")
        print("Solution:", solution(node))
        return
    frontier = [node]
    explored = set()
    while len(frontier) > 0:
        node = frontier.pop()
        print(node.get_state(), end="\n\n")
        explored.add(node.get_state().get_order())
        if node.get_depth() < limit:
            for action in actions(node.get_state()):
                child_node = result(node.get_state(), action, node, node.get_depth() + 1)
                child_order = child_node.get_state().get_order()
                if not child_order in explored:
                    if goal_test(child_order, goal_orders):
                        print(child_node.get_state(), end="\n\n")
                        print(solution(child_node))
                        return
                    frontier.append(child_node)
    print("No solution found.")

'''Used to generate goal orders based an order argument.'''
def generate_goal_orders(parsed_order):
    sorted_order_list = parsed_order.copy()
    sorted_order_list.sort(key=int)
    front_zero_goal = ",".join(sorted_order_list)
    sorted_order_list.append(sorted_order_list.pop(0))
    end_zero_goal = ",".join(sorted_order_list)
    return [front_zero_goal, end_zero_goal]

'''Checks if a given state's order matches one of the goal orders.'''
def goal_test(order, goal_orders):
    for goal_order in goal_orders:
        if order == goal_order:
            return True
    return False

'''Defines command line arguments and executes requested search with given parameters.'''
def main():
    parser = argparse.ArgumentParser(description="Run uninformed searches to solve 8-Puzzle.")
    parser.add_argument("search", type=str, help="bfs (breadth), dfs (depth), or dls (depth-limited).")
    parser.add_argument("order", type=str, help="starting order of puzzle (i.e. 1,3,2,5,4,6,8,7,0).")
    parser.add_argument(
        "-g",
        "--goals",
        type=str,
        action="append",
        help="override default order of goal state.")
    parser.add_argument("-l", "--limit", type=int, default=100, help="override default limit (100) for dls.")
    args = parser.parse_args()
    args.order = args.order.split(",")
    if args.goals is None:
        args.goals = generate_goal_orders(args.order)
    if args.search == "dfs":
        dfs(args.order, args.goals)
    elif args.search == "dls":
        dls(args.order, args.limit, args.goals)
    else:
        bfs(args.order, args.goals)

'''Generates a new node with the provided action performed on the original state.'''
def result(state, action, parent, depth = None):
    state_copy = copy.deepcopy(state)
    action_title = action(state_copy)
    return PuzzleNode(state_copy, parent, action_title, depth)

'''
Once search is complete, traces back up the created graph to find the series
of actions that lead to the goal state.
'''
def solution(node):
    solution = []
    while not node.get_parent() is None:
        solution.append(node.get_action())
        node = node.get_parent()
    return solution[::-1]

if __name__ == "__main__":
    main()
