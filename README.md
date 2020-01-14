# TEaCH
Telegram Exercise and Chat Helper

This is a bot to help remind you to exercise, among other things

## CHANGELOG
###2020.01.13.03
   - added checkGlobalFlags
   - ratePhoto now works for every photo with a caption, need to fix
   - added failure check in case of "/roll d0"
   - moved changelog to readme
###2020.01.13.02
- made /roll work properly by checking the first arg, catches error if there are no args (the rollDice method takes the MessageContext and not just a string)
- added target to gitignore
- added enums classes so I don't have to call them every time
- added number after date to changelog for revision control
###2020.01.13.01
- Added changelog
- Removed /d20 as it's redundant to /roll
- Added /allInfo for me to get all info from chats
- Added ratephoto (BAD implementation, throws exceptions at every image, need to fix)