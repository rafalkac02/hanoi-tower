# Tower of Hanoi

Rules, description: There are 3 rods and n disks. [TO-DO]

Design choices I have made:
* __User picks number of disks in range from 1 to 9 inclusive:__ Towers of hanoi games available online most commonly allow up to 8, 9 or 10 disks. With 9 disks set-up, there is minimum number of 511 moves to solve the puzzle (2<sup>n</sup>-1 for n disks). As this number grows rapidly, limit of 9 seems enough.
* __Indexes of rods are: 0, 1, 2:__ first rod is numbered 0, not 1 in accordance with programming naming convention
* ...
* [TO-DO]
