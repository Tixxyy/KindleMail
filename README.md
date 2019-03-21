# Basic email sender with attachment support

Made with the intention to easily send files to your Kindle email account (e.g. .pdf .mobi files) by using the Gmail API,
but you can easily use it for anything

## How to use
1) Enable the google API on the account that you are going to use to send mail https://console.developers.google.com/start/api?id=gmail 
2) Put the credentials.json file in src/main/resources folder 
3) Simply run it -gradle run (or you can create a fatJar for convenience - gradle fatJar) 

## Known issues
If authorization is unsuccessful the program essentially will die 

