
import math

'''Models the state (8-Puzzle) of any node in the graph created by the searching algorithm.'''
class Puzzle:

    '''Constructor for initializing the Puzzle object.'''
    def __init__(self, parsed_order):
        self.__board = Puzzle.generate_board(parsed_order)
        self.__zero_pos = Puzzle.get_zero_pos(self.__board)

    '''Returns a multiline string representing the board of the Puzzle object.'''
    def __str__(self):
        return "\n".join("\t".join(self.__board[i]) for i in range(0, len(self.__board)))

    '''Given an a parsed order (list(string)), creates a 2D list representing the board.'''
    def generate_board(parsed_order):
        board_size = len(parsed_order)
        board_dim = int(math.sqrt(board_size))
        return [parsed_order[i:board_dim + i] for i in range(0, board_size, board_dim)]

    '''Searches through board to find position of zero.'''
    def get_zero_pos(board):
        for i in range(0, len(board)):
            for j in range(0, len(board)):
                if board[i][j] == "0":
                    return {"row": i, "col": j}

    '''Helps swap elements of a list. Used to perform actions.'''
    def swap(board, row1, col1, row2, col2):
        board[row1][col1], board[row2][col2] = board[row2][col2], board[row1][col1]

    '''Returns comma delimited sequence representing the order of the board.'''
    def get_order(self):
        return ",".join(",".join(self.__board[i]) for i in range(0, len(self.__board)))

    '''
    Checks if an action can be performed based on the state of the board.
    Valid actions are up, down, left, and right.
    '''
    def can_left(self):
        return not self.__zero_pos["col"] == len(self.__board) - 1

    def can_down(self):
        return not self.__zero_pos["row"] == 0

    def can_right(self):
        return not self.__zero_pos["col"] == 0

    def can_up(self):
        return not self.__zero_pos["row"] == len(self.__board) - 1

    '''
    Mutates the state of the board by performing the action on the board.
    Valid actions are up, down, left, and right.
    Returns the name of the action.
    '''
    def left(self):
        if self.can_left():
            row = self.__zero_pos["row"]
            col = self.__zero_pos["col"]
            Puzzle.swap(self.__board, row, col, row, col + 1)
            self.__zero_pos["col"] = col + 1
            return "left"

    def down(self):
        if self.can_down():
            row = self.__zero_pos["row"]
            col = self.__zero_pos["col"]
            Puzzle.swap(self.__board, row, col, row - 1, col)
            self.__zero_pos["row"] = row - 1
            return "down"

    def right(self):
        if self.can_right():
            row = self.__zero_pos["row"]
            col = self.__zero_pos["col"]
            Puzzle.swap(self.__board, row, col, row, col - 1)
            self.__zero_pos["col"] = col - 1
            return "right"

    def up(self):
        if self.can_up():
            row = self.__zero_pos["row"]
            col = self.__zero_pos["col"]
            Puzzle.swap(self.__board, row, col, row + 1, col)
            self.__zero_pos["row"] = row + 1
            return "up"
