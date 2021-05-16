A program that learns about a topic of your choice (maybe bands, or cars or ice cream) by using a binary tree to play a guessing game. The program allows the state to be saved out to file and loaded at a later time.
Also, some summary stats about the tree structure are printed out.

## Play a game ...

The game works as follows: You will think of a specific animal (or item of your choice, music band, cars, star trek characters, etc) supposing the topic is animal and you think about a CAT. The game will ask you a series of binary questions to determine your animal.

- Think of an animal and I (the computer) will guess it by asking you a series of questions. 
- Does it have legs? `YES`
- Is it a cat? `YES`
- I win! (returns to main menu)

In the animal case the initial tree has the following knowledge: `Does it have legs` and the possible (initial) known outcomes: yes: `CAT` and no: `SNAKE` however supposing you choose BlueJay then the computer needs to ask you how to know the difference between a BlueJay and a Cat:

- Think of an animal and I will guess it
- Does it have legs? `YES`
- Is it a cat? `NOPE`
- I’m stumped, What is it? `BLUEJAY`
- Please type a yes/no question, that is yes for a cat but no for a BlueJay: `DOES IT HAVE WINGS?`
- Thanks (return to main menu)

Subsequent games will incorporate this new question at the correct level of the tree so that the computer may differentiate between a cat and a bluejay and ask you the correct question at the correct time.
