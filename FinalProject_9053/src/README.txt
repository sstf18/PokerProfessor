//Database: 
SQLite
(smae database we learned in the lecture); 

//Here are two tables which were created in the database 
CREATE TABLE users (
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	username TEXT NOT NULL,
	password TEXT NOT NULL,
	email TEXT NOT NULL
);

CREATE TABLE sessions (
    session_id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER NOT NULL,
    score INTEGER NOT NULL,
    session_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

// Test acount: (if someone want to test the code)
username: sstf1; password: sstf1



// Creating a Poker Simulator is my origal plan, but i couldn't finish it on time
in that simulator: 
	first, randomly genreate two cards, set as "your cards" : [rank1, suit1] and [rank2, suit2]; 
	second, randomly genreate another 2 cards 100000 times, compare these new 2 cards with "your cards" to see who will win.
	thrid, calcualte the win rate of "your Cards" in this 100000 times comparison.  
This simulator is good for using multithreading, and much better than I have now (PokerEquityCalculator). But I dont have time to finish it.. Maybe in the future..   

