'''
Simple node object for creating an internal search graph.
Keeps track of state (Puzzle), parent (PuzzleNode), action (str), and depth (int).
'''
class PuzzleNode:

    def __init__(self, state, parent, action, depth = None):
        self.__state = state
        self.__parent = parent
        self.__action = action
        self.__depth = depth

    def get_action(self):
        return self.__action

    def get_depth(self):
        return self.__depth

    def get_parent(self):
        return self.__parent

    def get_state(self):
        return self.__state
