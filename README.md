# TEaCHer
Telegram Exercise and Chat Helper

This is a bot to help remind you to exercise, among other things

## Functionality:
- /roll: "/roll 2d6" rolls (2) 6-sided dice. Also accepts "/roll d20" as input
- Photo ratings: rates any photo in which you include the word "rate" in the caption

## CHANGELOG

2020.01.14.00
- moved /roll funcitonality to Student
- re-renamed the TEaCH class back to TEaCHer

this is an exercise in learning how to resolve merge conflicts 

2020.01.13.02
- added checkGlobalFlags
- ratePhoto now works for every photo with a caption, need to fix
- added failure check in case of "/roll d0"
- moved changelog to readme
   
2020.01.13.01
- made /roll work properly by checking the first arg, catches error if there are no args (the rollDice method takes the MessageContext and not just a string)
- added target to gitignore
- added enums classes so I don't have to call them every time
- added number after date to changelog for revision control

2020.01.13.00
- Added changelog
- Removed /d20 as it's redundant to /roll
- Added /allInfo for me to get all info from chats
- Added ratephoto (BAD implementation, throws exceptions at every image, need to fix)
